package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.logmylifeapp.dto.CurrentWorkoutExerciseDTO
import com.example.logmylifeapp.dto.WorkoutSessionFlatRow
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSummaryDao {
    @Query(
        """
    select wp.name as planName, we.name, we.illustrationResId, wesl.`order`, wesl.targetReps, wesl.completedReps, wesl.weight from workout_exercise_set_log wesl join workout_plan wp on ws.planId = wp.id join workout_exercise_log wel on wesl.exerciseLogId = wel.id join  workout_session ws on wel.sessionId = ws.id  join workout_exercise we on wel.exerciseId = we.id where ws.id = :sessionId;
    """
    )
    suspend fun getWorkoutSessionFlatRowBySessionId(sessionId: Long): List<WorkoutSessionFlatRow>
}