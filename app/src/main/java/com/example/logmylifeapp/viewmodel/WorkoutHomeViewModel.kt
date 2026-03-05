package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.Graph.workoutSessionRepository
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import com.example.logmylifeapp.repository.WorkoutSessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WorkoutHomeViewModel(
    private val workoutPlanRepository: WorkoutPlanRepository = Graph.workoutPlanRepository,
    private val workoutSessionRepository: WorkoutSessionRepository = Graph.workoutSessionRepository
) : ViewModel() {

    val workoutPlansForToday: Flow<List<WorkoutPlan>> = workoutPlanRepository.getWorkoutPlanForToday()

    suspend fun addWorkoutSession(planId: Int): Long {
        return workoutSessionRepository.addWorkoutSession(planId)
    }
}