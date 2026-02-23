package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.WorkoutSessionDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutSession
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class WorkoutSessionRepository(
    private val dao: WorkoutSessionDao
) {

    suspend fun addWorkoutSession(planId: Int): Long {
        val workoutSession: WorkoutSession = WorkoutSession(
            planId = planId,
            date = LocalDate.now(),
            completed = false
        )
        return  dao.addWorkoutSession(workoutSession)
    }

    suspend fun updateWorkoutSession(workoutPlan: WorkoutSession) {
        dao.updateWorkoutSession(workoutPlan)
    }

    suspend fun deleteWorkoutSession(workoutPlan: WorkoutSession) {
        dao.deleteWorkoutSession(workoutPlan)
    }

    suspend fun setSessionCompleted(sessionId: Long){
        dao.setSessionCompleted(sessionId)
    }

    fun getAllWorkoutSessions(): Flow<List<WorkoutSession>> =
        dao.getAllWorkoutSessions()

    fun getWorkoutSessionById(id: Int): Flow<WorkoutSession?> =
        dao.getWorkoutSessionById(id)


    
}