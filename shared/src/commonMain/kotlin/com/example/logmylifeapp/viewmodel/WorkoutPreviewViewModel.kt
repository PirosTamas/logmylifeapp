package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.Graph.workoutSessionRepository
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.model.WorkoutPlanWithExercises
import com.example.logmylifeapp.model.WorkoutSession
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import com.example.logmylifeapp.repository.WorkoutExerciseLogRepository
import com.example.logmylifeapp.repository.WorkoutExerciseRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import com.example.logmylifeapp.repository.WorkoutSessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WorkoutPreviewViewModel(
    private val sessionId: Int,
    private val orderIndex: Int,
    private val workoutExerciseRepository: WorkoutExerciseRepository = Graph.workoutExerciseRepository,
    private val workoutExerciseLogRepository: WorkoutExerciseLogRepository = Graph.workoutExerciseLogRepository
) : ViewModel() {

    lateinit var currentExercise: Flow<WorkoutExercise?>



    init {
        viewModelScope.launch {
            currentExercise = workoutExerciseRepository.getWorkoutExerciseBySessionIdAndOrderInWorkout(sessionId, orderIndex);
        }
    }

    suspend fun addWorkoutExerciseLog(workoutExerciseLog: WorkoutExerciseLog): Long {
        return workoutExerciseLogRepository.addWorkoutExerciseLog(workoutExerciseLog)
    }


}