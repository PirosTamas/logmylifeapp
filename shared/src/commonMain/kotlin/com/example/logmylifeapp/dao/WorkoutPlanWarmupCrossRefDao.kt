package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlanWarmupCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanWarmupCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutPlanWarmupCrossRef(crossRef: WorkoutPlanWarmupCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutPlanWarmupCrossRefs(crossRefs: List<WorkoutPlanWarmupCrossRef>): List<Long>

    @Delete
    suspend fun deleteWorkoutPlanWarmupCrossRef(crossRef: WorkoutPlanWarmupCrossRef)


    @Query("""
        SELECT we.* FROM workout_session ws
        JOIN workout_plan_warmup_cross_ref wpc ON ws.planId = wpc.planId
        JOIN workout_exercise we ON wpc.exerciseId = we.id
        WHERE ws.id = :sessionId AND wpc.orderInWarmup = :order
    """)
    fun getWarmupBySessionIdAndOrder(sessionId: Int, order: Int): Flow<WorkoutExercise?>

    @Query("""
        SELECT COUNT(*) FROM workout_plan_warmup_cross_ref
        WHERE planId = (SELECT planId FROM workout_session WHERE id = :sessionId)
    """)
    suspend fun getWarmupCountForSession(sessionId: Int): Int
}
