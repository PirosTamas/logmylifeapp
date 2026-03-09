package com.example.logmylifeapp.repository

import androidx.room.Query
import com.example.logmylifeapp.dao.WorkoutPlanExerciseCrossRefDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import kotlinx.coroutines.flow.Flow

class WorkoutPlanExerciseCrossRefRepository(
    private val dao: WorkoutPlanExerciseCrossRefDao
) {

    suspend fun addWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef) {
        dao.addWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef)
    }

    suspend fun addWorkoutPlanExerciseCrossRefs(workoutPlanExerciseCrossRefs: List<WorkoutPlanExerciseCrossRef>) {
        dao.addWorkoutPlanExerciseCrossRefs(workoutPlanExerciseCrossRefs)
    }

    suspend fun updateWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef) {
        dao.updateWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef)
    }

    suspend fun deleteWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef: WorkoutPlanExerciseCrossRef) {
        dao.deleteWorkoutPlanExerciseCrossRef(workoutPlanExerciseCrossRef)
    }

    fun getAllWorkoutPlanExerciseCrossRefs(): Flow<List<WorkoutPlanExerciseCrossRef>> =
        dao.getAllWorkoutPlanExerciseCrossRefs()

   suspend fun getExerciseCountForWorkoutSession(sessionId: Int): Int =
       dao.getExerciseCountForWorkoutSession(sessionId)



    
}