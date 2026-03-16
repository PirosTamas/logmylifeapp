package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlanStretchCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WorkoutPlanStretchCrossRefRepository(private val db: LogMyLifeDatabase) {

    suspend fun addWorkoutPlanStretchCrossRef(ref: WorkoutPlanStretchCrossRef) = withContext(Dispatchers.Default) {
        db.workoutPlanStretchCrossRefQueries.addWorkoutPlanStretchCrossRef(
            planId = ref.planId,
            exerciseId = ref.exerciseId,
            orderInStretch = ref.orderInStretch
        )
    }

    suspend fun addWorkoutPlanStretchCrossRefs(refs: List<WorkoutPlanStretchCrossRef>) = withContext(Dispatchers.Default) {
        refs.forEach { ref ->
            db.workoutPlanStretchCrossRefQueries.insertWorkoutPlanStretchCrossRef(
                planId = ref.planId,
                exerciseId = ref.exerciseId,
                orderInStretch = ref.orderInStretch
            )
        }
    }

    fun getStretchBySessionIdAndOrder(sessionId: Int, order: Int): Flow<WorkoutExercise?> =
        db.workoutExerciseQueries.getStretchBySessionIdAndOrder(sessionId, order)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it?.toModel() }

    suspend fun getStretchCountForSession(sessionId: Int): Int = withContext(Dispatchers.Default) {
        db.workoutPlanStretchCrossRefQueries.getStretchCountForSession(sessionId)
            .executeAsOne().toInt()
    }
}
