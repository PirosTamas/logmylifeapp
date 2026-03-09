package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlanStretchCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutPlanStretchCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutPlanStretchCrossRef(crossRef: WorkoutPlanStretchCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutPlanStretchCrossRefs(crossRefs: List<WorkoutPlanStretchCrossRef>): List<Long>

    @Delete
    suspend fun deleteWorkoutPlanStretchCrossRef(crossRef: WorkoutPlanStretchCrossRef)

    @Query("""
        SELECT we.* FROM workout_session ws
        JOIN workout_plan_stretch_cross_ref wpc ON ws.planId = wpc.planId
        JOIN workout_exercise we ON wpc.exerciseId = we.id
        WHERE ws.id = :sessionId AND wpc.orderInStretch = :order
    """)
    fun getStretchBySessionIdAndOrder(sessionId: Int, order: Int): Flow<WorkoutExercise?>

    @Query("""
        SELECT COUNT(*) FROM workout_plan_stretch_cross_ref
        WHERE planId = (SELECT planId FROM workout_session WHERE id = :sessionId)
    """)
    suspend fun getStretchCountForSession(sessionId: Int): Int
}
