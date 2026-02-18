package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.dto.CurrentWorkoutExerciseDTO
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.model.WorkoutPlanWithExercises
import com.example.logmylifeapp.repository.CurrentWorkoutExerciseRepository
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import com.example.logmylifeapp.repository.WorkoutExerciseSetLogRepository
import com.example.logmylifeapp.repository.WorkoutPlanExerciseCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutSetViewModel(
    private val sessionId: Long,
    private val exerciseLogId: Int,
    private val setIndex: Int,
    private val currentWorkoutExerciseRepository: CurrentWorkoutExerciseRepository = Graph.currentWorkoutExerciseRepository,
    private val workoutExerciseSetLogRepository: WorkoutExerciseSetLogRepository = Graph.workoutExerciseSetLogRepository,
    private val workoutPlanExerciseCrossRefRepository: WorkoutPlanExerciseCrossRefRepository = Graph.workoutPlanExerciseCrossRefRepository
) : ViewModel() {
    val currentWorkout: Flow<CurrentWorkoutExerciseDTO?> = currentWorkoutExerciseRepository.getWorkoutExerciseByWorkoutExerciseLogIdAndSetIndex(exerciseLogId, setIndex)

    private val _exerciseCount = MutableStateFlow(0)
    val exerciseCount: StateFlow<Int> = _exerciseCount

    init {
        viewModelScope.launch {
            _exerciseCount.value = workoutPlanExerciseCrossRefRepository.getExerciseCountForWorkoutSession(sessionId)
        }
    }

    fun addWorkoutExerciseSetLog(workoutExerciseSetLog: WorkoutExerciseSetLog) {
        viewModelScope.launch(Dispatchers.IO) {
            workoutExerciseSetLogRepository.addWorkoutExerciseSetLog(workoutExerciseSetLog)
        }
    }


}