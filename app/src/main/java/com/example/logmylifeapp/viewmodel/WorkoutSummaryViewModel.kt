package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.dao.WorkoutSummaryDao
import com.example.logmylifeapp.dto.CurrentWorkoutExerciseDTO
import com.example.logmylifeapp.dto.WorkoutSummaryDTO
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
import com.example.logmylifeapp.repository.WorkoutSummaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutSummaryViewModel(
    private val sessionId: Long,
    private val workoutSummaryRepository: WorkoutSummaryRepository = Graph.workoutSummaryRepository
) : ViewModel() {
    private val _workoutSummary = MutableStateFlow<WorkoutSummaryDTO?>(null)
    val workoutSummary: StateFlow<WorkoutSummaryDTO?> = _workoutSummary

    init{
        viewModelScope.launch {
            _workoutSummary.value =
                workoutSummaryRepository.getWorkoutSummaryBySessionId(sessionId)
        }
    }

}