package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.WorkoutPlanWarmupCrossRefDao
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlanWarmupCrossRef
import kotlinx.coroutines.flow.Flow

class WorkoutPlanWarmupCrossRefRepository(
    private val dao: WorkoutPlanWarmupCrossRefDao
) {
    suspend fun addWorkoutPlanWarmupCrossRef(crossRef: WorkoutPlanWarmupCrossRef) =
        dao.addWorkoutPlanWarmupCrossRef(crossRef)

    suspend fun addWorkoutPlanWarmupCrossRefs(crossRefs: List<WorkoutPlanWarmupCrossRef>) =
        dao.addWorkoutPlanWarmupCrossRefs(crossRefs)

    fun getWarmupBySessionIdAndOrder(sessionId: Int, order: Int): Flow<WorkoutExercise?> =
        dao.getWarmupBySessionIdAndOrder(sessionId, order)

    suspend fun getWarmupCountForSession(sessionId: Int): Int =
        dao.getWarmupCountForSession(sessionId)
}
