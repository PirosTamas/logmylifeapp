package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.logmylifeapp.model.WorkoutExerciseSetLog

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




}