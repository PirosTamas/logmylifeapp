package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.dao.WorkoutPlanExerciseCrossRefDao
import com.example.logmylifeapp.dto.CurrentWorkoutExerciseDTO
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flowOf

class WorkoutSessionViewModel(
    val sessionId: Int,
    private val workoutPlanRepository: WorkoutPlanRepository = Graph.workoutPlanRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository = Graph.workoutExerciseRepository,
    private val workoutExerciseLogRepository: WorkoutExerciseLogRepository = Graph.workoutExerciseLogRepository,
    private val workoutExerciseSetLogRepository: WorkoutExerciseSetLogRepository = Graph.workoutExerciseSetLogRepository,
    private val currentWorkoutExerciseRepository: CurrentWorkoutExerciseRepository = Graph.currentWorkoutExerciseRepository,
    private val workoutPlanExerciseCrossRefDao: WorkoutPlanExerciseCrossRefRepository = Graph.workoutPlanExerciseCrossRefRepository
) : ViewModel() {
    private var workoutPlan: WorkoutPlan? = null
    private var numberOfExercises: Int = 0
    var currentExerciseLogId: Long? = null

    init {
        viewModelScope.launch {
            workoutPlan = workoutPlanRepository
                .getWorkoutPlanBySessionId(sessionId)

            numberOfExercises = workoutPlanExerciseCrossRefDao.getExerciseCountForWorkoutSession(sessionId)
        }
    }

    private val _exerciseIndex = MutableStateFlow(0)
    val exerciseIndex: StateFlow<Int> = _exerciseIndex

    private val _setIndex = MutableStateFlow(0)
    val setIndex: StateFlow<Int> = _setIndex


    val workoutExercise: StateFlow<WorkoutExercise?> =
        exerciseIndex
            .flatMapLatest { exerciseIdx ->
                workoutExerciseRepository
                    .getWorkoutExerciseBySessionIdAndOrderInWorkout(sessionId, exerciseIdx)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )

    val currentWorkoutSet: StateFlow<CurrentWorkoutExerciseDTO?> =
        combine(workoutExercise, setIndex) { exercise, setIdx ->
            exercise to setIdx
        }
            .flatMapLatest { (exercise, setIdx) ->

                if (exercise == null) {
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
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )


    suspend fun addWorkoutExerciseLog(): Long {
        val exercise = workoutExercise.value ?: return -1L

        val log = WorkoutExerciseLog(
            sessionId = sessionId,
            exerciseId = exercise.id
        )

        val id = workoutExerciseLogRepository.addWorkoutExerciseLog(log)
        currentExerciseLogId = id
        return id
    }

    fun onSuccessClicked(
        workout: WorkoutExerciseSetLog,
        navigateToPreview: () -> Unit,
        navigateToSummary: () -> Unit
    ) {
        viewModelScope.launch {
            val exercise = workoutExercise.value ?: return@launch
            var exerciseLogId = currentExerciseLogId


            if (exerciseLogId == null) {
                exerciseLogId = addWorkoutExerciseLog()
            }

            if (exerciseLogId == -1L) return@launch

            addWorkoutExerciseSetLog(
                WorkoutExerciseSetLog(
                    exerciseLogId = exerciseLogId.toInt(),
                    order = setIndex.value,
                    targetReps = workout.targetReps,
                    completedReps = workout.targetReps,
                    weight = workout.weight,
                    success = true,
                    description = "SUCCESS"
                )
            )
            if(_setIndex.value < exercise.numberOfSets -1){
                _setIndex.value++
                return@launch
            }

            _setIndex.value = 0
            _exerciseIndex.value++

            if (exerciseIndex.value > numberOfExercises - 1) {
                navigateToSummary()
            } else{
                navigateToPreview()
            }
        }
    }

    fun addWorkoutExerciseSetLog(workoutExerciseSetLog: WorkoutExerciseSetLog) {

        viewModelScope.launch(Dispatchers.IO) {
            workoutExerciseSetLogRepository.addWorkoutExerciseSetLog(workoutExerciseSetLog)
        }
    }


}