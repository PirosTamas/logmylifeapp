package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.WorkoutPlanStretchCrossRefDao
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlanStretchCrossRef
import kotlinx.coroutines.flow.Flow

class WorkoutPlanStretchCrossRefRepository(
    private val dao: WorkoutPlanStretchCrossRefDao
) {
    suspend fun addWorkoutPlanStretchCrossRef(crossRef: WorkoutPlanStretchCrossRef) =
        dao.addWorkoutPlanStretchCrossRef(crossRef)

    suspend fun addWorkoutPlanStretchCrossRefs(crossRefs: List<WorkoutPlanStretchCrossRef>) =
        dao.addWorkoutPlanStretchCrossRefs(crossRefs)

    fun getStretchBySessionIdAndOrder(sessionId: Int, order: Int): Flow<WorkoutExercise?> =
        dao.getStretchBySessionIdAndOrder(sessionId, order)

    suspend fun getStretchCountForSession(sessionId: Int): Int =
        dao.getStretchCountForSession(sessionId)
}
