package com.example.logmylifeapp.repository

import androidx.room.Query
import com.example.logmylifeapp.dao.WorkoutPlanDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutPlan
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class WorkoutPlanRepository(
    private val dao: WorkoutPlanDao
) {

    suspend fun addWorkoutPlan(workoutPlan: WorkoutPlan) {
        dao.addWorkoutPlan(workoutPlan)
    }

    suspend fun insertWorkoutPlanGetId(workoutPlan: WorkoutPlan): Long =
        dao.insertWorkoutPlanGetId(workoutPlan)

    suspend fun addWorkoutPlans(workoutPlans: List<WorkoutPlan>) {
        dao.addWorkoutPlans(workoutPlans)
    }

    suspend fun updateWorkoutPlan(workoutPlan: WorkoutPlan) {
        dao.updateWorkoutPlan(workoutPlan)
    }

    suspend fun deleteWorkoutPlan(workoutPlan: WorkoutPlan) {
        dao.deleteWorkoutPlan(workoutPlan)
    }

    fun getAllWorkoutPlans(): Flow<List<WorkoutPlan>> =
        dao.getAllWorkoutPlans()

    fun getWorkoutPlanById(id: Int): Flow<WorkoutPlan?> =
        dao.getWorkoutPlanById(id)

    fun getWorkoutPlanForToday(): Flow<List<WorkoutPlan>> {
        val today = LocalDate.now().dayOfWeek.name
        return dao.getWorkoutPlanForDay(today)
    }

    fun getWorkoutPlanWithExercises(planId: Int) =
        dao.getWorkoutPlanWithExercises(planId)


    suspend fun getWorkoutPlanBySessionId(sessionId: Int): WorkoutPlan?{
        return dao.getWorkoutPlanBySessionId(sessionId)
    }
    
}