package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseSetLogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutExerciseSetLog(workoutExerciseSetLog: WorkoutExerciseSetLog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutExerciseSetLogs(workoutExerciseSetLog: List<WorkoutExerciseSetLog>): List<Long>

    @Update
    suspend fun updateWorkoutExerciseSetLog(workoutExerciseSetLog: WorkoutExerciseSetLog)

    @Delete
    suspend fun deleteWorkoutExerciseSetLog(workoutExerciseSetLog: WorkoutExerciseSetLog)

    @Query("select count(*) from workout_exercise_set_log wesl  where wesl.exerciseLogId = (select id from workout_exercise_log where sessionId = :sessionId and id = (select exerciseId from workout_plan_exercise_cross_ref where orderInWorkout = :exerciseIndex and planId = (select planId from workout_session where id = :sessionId)))")
    fun getCurrentSetIndexBySessionIdAndExerciseIndex(sessionId: Int, exerciseIndex: Int): Flow<Int>




}