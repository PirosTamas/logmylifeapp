package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.dto.WorkoutSummaryDTO
import com.example.logmylifeapp.enums.WorkoutExerciseType
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import com.example.logmylifeapp.repository.WorkoutExerciseRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AddWorkoutPlanViewModel(
    private val workoutPlanRepository: WorkoutPlanRepository = Graph.workoutPlanRepository,
    private val workoutExerciseRepository: WorkoutExerciseRepository = Graph.workoutExerciseRepository
) : ViewModel() {


    private val _warmups = MutableStateFlow<List<WorkoutExercise>>(emptyList())
    val warmups: StateFlow<List<WorkoutExercise>> = _warmups

    private val _workouts = MutableStateFlow<List<WorkoutExercise>>(emptyList())
    val workouts: StateFlow<List<WorkoutExercise>> = _workouts

    private val _stretches = MutableStateFlow<List<WorkoutExercise>>(emptyList())
    val stretches: StateFlow<List<WorkoutExercise>> = _stretches

    private val _selectedExerciseIds = MutableStateFlow<Set<Int>>(emptySet())
    val selectedExerciseIds: StateFlow<Set<Int>> = _selectedExerciseIds

    val workoutExercises: StateFlow<List<WorkoutExercise>> =
        workoutExerciseRepository
            .getAllWorkoutExercises()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    fun addWarmup(exercise: WorkoutExercise) {
        _warmups.value += exercise
    }

    fun removeWarmup(exercise: WorkoutExercise) {
        _warmups.value -= exercise
    }

    fun addWorkoutPlan(workoutPlan: WorkoutPlan) {
        viewModelScope.launch(Dispatchers.IO) {
            workoutPlanRepository.addWorkoutPlan(workoutPlan)
        }
    }

    fun saveExercises(workoutExerciseType: WorkoutExerciseType){
        val selectedExercises = workoutExercises.value
            .filter { it.id in _selectedExerciseIds.value }

        when(workoutExerciseType){
            WorkoutExerciseType.WARMUP -> {
                _warmups.value += selectedExercises
            }
            WorkoutExerciseType.WORKOUT -> {
                _workouts.value += selectedExercises
            }
            WorkoutExerciseType.STRETCH -> {
                _stretches.value += selectedExercises
            }
        }
        clearExerciseSelection()
    }

    fun toggleExerciseSelection(id: Int) {
        val current = _selectedExerciseIds.value.toMutableSet()

        if (current.contains(id)) {
            current.remove(id)
        } else {
            current.add(id)
        }

        _selectedExerciseIds.value = current
    }

    private fun clearExerciseSelection() {
        _selectedExerciseIds.value = emptySet()
    }

}