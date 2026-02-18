package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.WorkoutExerciseSetLogDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class WorkoutExerciseSetLogRepository(
    private val dao: WorkoutExerciseSetLogDao
) {

    suspend fun addWorkoutExerciseSetLog(workoutPlan: WorkoutExerciseSetLog) {
        dao.addWorkoutExerciseSetLog(workoutPlan)
    }

    suspend fun addWorkoutExerciseSetLogs(workoutPlans: List<WorkoutExerciseSetLog>) {
        dao.addWorkoutExerciseSetLogs(workoutPlans)
    }

    suspend fun updateWorkoutExerciseSetLog(workoutPlan: WorkoutExerciseSetLog) {
        dao.updateWorkoutExerciseSetLog(workoutPlan)
    }

    suspend fun deleteWorkoutExerciseSetLog(workoutPlan: WorkoutExerciseSetLog) {
        dao.deleteWorkoutExerciseSetLog(workoutPlan)
    }

    
}