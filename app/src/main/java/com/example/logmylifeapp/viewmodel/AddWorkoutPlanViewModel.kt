package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.enums.WorkoutExerciseType
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import com.example.logmylifeapp.model.WorkoutPlanStretchCrossRef
import com.example.logmylifeapp.model.WorkoutPlanWarmupCrossRef
import com.example.logmylifeapp.repository.WorkoutExerciseRepository
import com.example.logmylifeapp.repository.WorkoutPlanExerciseCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import com.example.logmylifeapp.repository.WorkoutPlanStretchCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanWarmupCrossRefRepository
import com.example.logmylifeapp.screen.components.InputField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddWorkoutPlanViewModel(
    private val workoutPlanRepository: WorkoutPlanRepository = Graph.workoutPlanRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository = Graph.workoutExerciseRepository,
    private val workoutPlanExerciseCrossRefRepository: WorkoutPlanExerciseCrossRefRepository = Graph.workoutPlanExerciseCrossRefRepository,
    private val workoutPlanWarmupCrossRefRepository: WorkoutPlanWarmupCrossRefRepository = Graph.workoutPlanWarmupCrossRefRepository,
    private val workoutPlanStretchCrossRefRepository: WorkoutPlanStretchCrossRefRepository = Graph.workoutPlanStretchCrossRefRepository
) : ViewModel() {
    val planName = InputField.TextField(label = "Name", placeholder = "e.g. Hypertrophy Split")
    val planSessions = InputField.NumberField(label = "Sessions", placeholder = "3")
    val planStartDate = InputField.DateField(label = "Start Date")
    val planScheduledDays = InputField.MultiSelectDays(label = "Scheduled Days")

    private val _warmups = MutableStateFlow<List<WorkoutExercise>>(emptyList())
    val warmups: StateFlow<List<WorkoutExercise>> = _warmups

    private val _mainExercises = MutableStateFlow<List<WorkoutExercise>>(emptyList())
    val mainExercises: StateFlow<List<WorkoutExercise>> = _mainExercises

    private val _stretches = MutableStateFlow<List<WorkoutExercise>>(emptyList())
    val stretches: StateFlow<List<WorkoutExercise>> = _stretches

    private val _selectedExerciseIds = MutableStateFlow<Set<Int>>(emptySet())
    val selectedExerciseIds: StateFlow<Set<Int>> = _selectedExerciseIds

    val workoutExercises: StateFlow<List<WorkoutExercise>> =
        workoutExerciseRepository
            .getAllWorkoutExercises()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveExercises(workoutExerciseType: WorkoutExerciseType) {
        val selectedExercises = workoutExercises.value
            .filter { it.id in _selectedExerciseIds.value }

        when (workoutExerciseType) {
            WorkoutExerciseType.WARMUP -> _warmups.value += selectedExercises
            WorkoutExerciseType.MAIN -> _mainExercises.value += selectedExercises
            WorkoutExerciseType.STRETCH -> _stretches.value += selectedExercises
        }
        clearExerciseSelection()
    }

    fun addWorkoutPlanWithExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            val plan = WorkoutPlan(
                name = planName.value,
                scheduledDays = planScheduledDays.selectedDays,
                currentSession = 0,
                numberOfSessions = planSessions.value ?: 0,
                startDate = planStartDate.date ?: java.time.LocalDate.now()
            )

            val planId = workoutPlanRepository.insertWorkoutPlanGetId(plan).toInt()

            _mainExercises.value.forEachIndexed { index, exercise ->
                workoutPlanExerciseCrossRefRepository.addWorkoutPlanExerciseCrossRef(
                    WorkoutPlanExerciseCrossRef(planId, exercise.id, index)
                )
            }

            _warmups.value.forEachIndexed { index, exercise ->
                workoutPlanWarmupCrossRefRepository.addWorkoutPlanWarmupCrossRef(
                    WorkoutPlanWarmupCrossRef(planId, exercise.id, index)
                )
            }

            _stretches.value.forEachIndexed { index, exercise ->
                workoutPlanStretchCrossRefRepository.addWorkoutPlanStretchCrossRef(
                    WorkoutPlanStretchCrossRef(planId, exercise.id, index)
                )
            }
        }
    }

    fun toggleExerciseSelection(id: Int) {
        val current = _selectedExerciseIds.value.toMutableSet()
        if (current.contains(id)) current.remove(id) else current.add(id)
        _selectedExerciseIds.value = current
    }

    private fun clearExerciseSelection() {
        _selectedExerciseIds.value = emptySet()
    }
}
