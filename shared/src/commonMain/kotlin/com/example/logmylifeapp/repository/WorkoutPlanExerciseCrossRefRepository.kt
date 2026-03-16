package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WorkoutPlanExerciseCrossRefRepository(private val db: LogMyLifeDatabase) {

    suspend fun addWorkoutPlanExerciseCrossRef(ref: WorkoutPlanExerciseCrossRef) = withContext(Dispatchers.Default) {
        db.workoutPlanExerciseCrossRefQueries.addWorkoutPlanExerciseCrossRef(
            planId = ref.planId,
            exerciseId = ref.exerciseId,
            orderInWorkout = ref.orderInWorkout
        )
    }

    suspend fun addWorkoutPlanExerciseCrossRefs(refs: List<WorkoutPlanExerciseCrossRef>): List<Long> = withContext(Dispatchers.Default) {
        refs.map { ref ->
            db.workoutPlanExerciseCrossRefQueries.insertWorkoutPlanExerciseCrossRef(
                planId = ref.planId,
                exerciseId = ref.exerciseId,
                orderInWorkout = ref.orderInWorkout
            )
            db.workoutPlanQueries.lastInsertRowId().executeAsOne()
        }
    }

    suspend fun updateWorkoutPlanExerciseCrossRef(ref: WorkoutPlanExerciseCrossRef) = withContext(Dispatchers.Default) {
        db.workoutPlanExerciseCrossRefQueries.updateWorkoutPlanExerciseCrossRef(
            planId = ref.planId,
            exerciseId = ref.exerciseId,
            orderInWorkout = ref.orderInWorkout
        )
    }

    suspend fun deleteWorkoutPlanExerciseCrossRef(ref: WorkoutPlanExerciseCrossRef) = withContext(Dispatchers.Default) {
        db.workoutPlanExerciseCrossRefQueries.deleteWorkoutPlanExerciseCrossRef(
            planId = ref.planId,
            exerciseId = ref.exerciseId
        )
    }

    fun getAllWorkoutPlanExerciseCrossRefs(): Flow<List<WorkoutPlanExerciseCrossRef>> =
        db.workoutPlanExerciseCrossRefQueries.getAllWorkoutPlanExerciseCrossRefs()
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }

    suspend fun getExerciseCountForWorkoutSession(sessionId: Int): Int = withContext(Dispatchers.Default) {
        db.workoutPlanExerciseCrossRefQueries.getExerciseCountForWorkoutSession(sessionId)
            .executeAsOne().toInt()
    }
}
