package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlanWarmupCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WorkoutPlanWarmupCrossRefRepository(private val db: LogMyLifeDatabase) {

    suspend fun addWorkoutPlanWarmupCrossRef(ref: WorkoutPlanWarmupCrossRef) = withContext(Dispatchers.Default) {
        db.workoutPlanWarmupCrossRefQueries.addWorkoutPlanWarmupCrossRef(
            planId = ref.planId,
            exerciseId = ref.exerciseId,
            orderInWarmup = ref.orderInWarmup
        )
    }

    suspend fun addWorkoutPlanWarmupCrossRefs(refs: List<WorkoutPlanWarmupCrossRef>) = withContext(Dispatchers.Default) {
        refs.forEach { ref ->
            db.workoutPlanWarmupCrossRefQueries.insertWorkoutPlanWarmupCrossRef(
                planId = ref.planId,
                exerciseId = ref.exerciseId,
                orderInWarmup = ref.orderInWarmup
            )
        }
    }

    fun getWarmupBySessionIdAndOrder(sessionId: Int, order: Int): Flow<WorkoutExercise?> =
        db.workoutExerciseQueries.getWarmupBySessionIdAndOrder(sessionId, order)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it?.toModel() }

    suspend fun getWarmupCountForSession(sessionId: Int): Int = withContext(Dispatchers.Default) {
        db.workoutPlanWarmupCrossRefQueries.getWarmupCountForSession(sessionId)
            .executeAsOne().toInt()
    }
}
