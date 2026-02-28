package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.WorkoutExerciseLogDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExerciseLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class WorkoutExerciseLogRepository(
    private val dao: WorkoutExerciseLogDao
) {

    suspend fun addWorkoutExerciseLog(workoutPlan: WorkoutExerciseLog): Long {
        return dao.addWorkoutExerciseLog(workoutPlan)
    }

    suspend fun addWorkoutExerciseLogs(workoutPlans: List<WorkoutExerciseLog>) {
        dao.addWorkoutExerciseLogs(workoutPlans)
    }

    suspend fun updateWorkoutExerciseLog(workoutPlan: WorkoutExerciseLog) {
        dao.updateWorkoutExerciseLog(workoutPlan)
    }

    suspend fun deleteWorkoutExerciseLog(workoutPlan: WorkoutExerciseLog) {
        dao.deleteWorkoutExerciseLog(workoutPlan)
    }

    fun getCurrentExerciseIndexBySessionId(sessionId: Int): Flow<Int>{
        return dao.getCurrentExerciseIndexBySessionId(sessionId)
    }

    
}