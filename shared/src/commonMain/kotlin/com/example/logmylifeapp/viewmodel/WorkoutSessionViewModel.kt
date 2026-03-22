package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.model.ExercisePhase
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.repository.CurrentWorkoutExerciseRepository
import com.example.logmylifeapp.repository.WorkoutExerciseLogRepository
import com.example.logmylifeapp.repository.WorkoutExerciseRepository
import com.example.logmylifeapp.repository.WorkoutExerciseSetLogRepository
import com.example.logmylifeapp.repository.WorkoutPlanExerciseCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import com.example.logmylifeapp.repository.WorkoutPlanStretchCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanWarmupCrossRefRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkoutSessionViewModel(
    val sessionId: Int,
    private val workoutPlanRepository: WorkoutPlanRepository = Graph.workoutPlanRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository = Graph.workoutExerciseRepository,
    private val workoutExerciseLogRepository: WorkoutExerciseLogRepository = Graph.workoutExerciseLogRepository,
    private val workoutExerciseSetLogRepository: WorkoutExerciseSetLogRepository = Graph.workoutExerciseSetLogRepository,
    private val currentWorkoutExerciseRepository: CurrentWorkoutExerciseRepository = Graph.currentWorkoutExerciseRepository,
    private val workoutPlanExerciseCrossRefRepository: WorkoutPlanExerciseCrossRefRepository = Graph.workoutPlanExerciseCrossRefRepository,
    private val warmupCrossRefRepository: WorkoutPlanWarmupCrossRefRepository = Graph.workoutPlanWarmupCrossRefRepository,
    private val stretchCrossRefRepository: WorkoutPlanStretchCrossRefRepository = Graph.workoutPlanStretchCrossRefRepository
) : ViewModel() {

    private var workoutPlan: WorkoutPlan? = null

    // Counts for each phase — loaded once in init
    private var warmupCount = 0
    private var mainExerciseCount = 0
    private var stretchCount = 0

    // BUG FIX: counts were plain var loaded in a coroutine, causing a race condition where
    // onSuccessClicked could run before the counts were loaded (they'd be 0, triggering summary
    // immediately). CompletableDeferred makes callers wait until init finishes.
    private val countsLoaded = CompletableDeferred<Unit>()

    private val _phase = MutableStateFlow(ExercisePhase.WARMUP)
    val phase: StateFlow<ExercisePhase> = _phase


    private val _indexInPhase = MutableStateFlow(0)

    val exerciseIndex: StateFlow<Int> = _indexInPhase

    private val _setIndex = MutableStateFlow(0)
    val setIndex: StateFlow<Int> = _setIndex

    private val _planName = MutableStateFlow("")
    val planName: StateFlow<String> = _planName

    var currentExerciseLogId: Long? = null

    init {
        viewModelScope.launch {
            workoutPlan = workoutPlanRepository.getWorkoutPlanBySessionId(sessionId)
            _planName.value = workoutPlan?.name ?: ""

            warmupCount = warmupCrossRefRepository.getWarmupCountForSession(sessionId)
            mainExerciseCount = workoutPlanExerciseCrossRefRepository.getExerciseCountForWorkoutSession(sessionId)
            stretchCount = stretchCrossRefRepository.getStretchCountForSession(sessionId)

            _phase.value = when {
                warmupCount > 0 -> ExercisePhase.WARMUP
                mainExerciseCount > 0 -> ExercisePhase.MAIN
                else -> ExercisePhase.STRETCH
            }

            countsLoaded.complete(Unit)
        }
    }

    val workoutExercise: StateFlow<WorkoutExercise?> =
        combine(_phase, _indexInPhase) { phase, idx -> phase to idx }
            .flatMapLatest { (phase, idx) ->
                when (phase) {
                    ExercisePhase.WARMUP ->
                        warmupCrossRefRepository.getWarmupBySessionIdAndOrder(sessionId, idx)
                    ExercisePhase.MAIN ->
                        workoutExerciseRepository.getWorkoutExerciseBySessionIdAndOrderInWorkout(sessionId, idx)
                    ExercisePhase.STRETCH ->
                        stretchCrossRefRepository.getStretchBySessionIdAndOrder(sessionId, idx)
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    val currentWorkoutSet =
        combine(_phase, workoutExercise, _setIndex) { phase, exercise, setIdx ->
            Triple(phase, exercise, setIdx)
        }
            .flatMapLatest { (phase, exercise, setIdx) ->
                if (phase != ExercisePhase.MAIN || exercise == null) {
                    flowOf(null)
                } else {
                    currentWorkoutExerciseRepository
                        .getWorkoutExerciseBySessionIdAndExerciseIdAndSetIndex(
                            sessionId = sessionId,
                            exerciseId = exercise.id,
                            setIndex = setIdx
                        )
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    fun onWarmupOrStretchDone(navigateToSummary: () -> Unit) {
        viewModelScope.launch {
            countsLoaded.await()
            advancePhase(navigateToSummary)
        }
    }

    private fun advancePhase(navigateToSummary: () -> Unit) {
        when (_phase.value) {
            ExercisePhase.WARMUP -> {
                if (_indexInPhase.value < warmupCount - 1) {
                    _indexInPhase.value++
                } else {
                    _indexInPhase.value = 0
                    when {
                        mainExerciseCount > 0 -> _phase.value = ExercisePhase.MAIN
                        stretchCount > 0 -> _phase.value = ExercisePhase.STRETCH
                        else -> navigateToSummary()
                    }
                }
            }

            ExercisePhase.MAIN -> {
                _indexInPhase.value = 0
                when {
                    stretchCount > 0 -> _phase.value = ExercisePhase.STRETCH
                    else -> navigateToSummary()
                }
            }

            ExercisePhase.STRETCH -> {
                if (_indexInPhase.value < stretchCount - 1) {
                    _indexInPhase.value++
                } else {
                    navigateToSummary()
                }
            }
        }
    }

    fun onSuccessClicked(
        workout: WorkoutExerciseSetLog,
        navigateToPreview: () -> Unit,
        navigateToSummary: () -> Unit
    ) {
        viewModelScope.launch {
            countsLoaded.await()

            val exercise = workoutExercise.value ?: return@launch
            var exerciseLogId = currentExerciseLogId

            if (exerciseLogId == null || _setIndex.value == 0) {
                exerciseLogId = addWorkoutExerciseLog()
            }

            if (exerciseLogId == -1L) return@launch

            addWorkoutExerciseSetLog(
                WorkoutExerciseSetLog(
                    exerciseLogId = exerciseLogId.toInt(),
                    order = setIndex.value,
                    targetReps = workout.targetReps,
                    completedReps = workout.completedReps,
                    weight = workout.weight,
                    success = true,
                    description = "SUCCESS"
                )
            )

            if (_setIndex.value < exercise.numberOfSets - 1) {
                _setIndex.value++
                return@launch
            }

            _setIndex.value = 0
            _indexInPhase.value++

            if (_indexInPhase.value < mainExerciseCount) {
                navigateToPreview()
            } else {
                _indexInPhase.value = 0
                advancePhase(navigateToSummary)
                if (_phase.value == ExercisePhase.STRETCH) {
                    navigateToPreview()
                }
            }
        }
    }

    private suspend fun addWorkoutExerciseLog(): Long {
        val exercise = workoutExercise.value ?: return -1L
        val log = WorkoutExerciseLog(sessionId = sessionId, exerciseId = exercise.id)
        val id = workoutExerciseLogRepository.addWorkoutExerciseLog(log)
        currentExerciseLogId = id
        return id
    }

    fun addWorkoutExerciseSetLog(workoutExerciseSetLog: WorkoutExerciseSetLog) {
        viewModelScope.launch {
            workoutExerciseSetLogRepository.addWorkoutExerciseSetLog(workoutExerciseSetLog)
        }
    }
}
