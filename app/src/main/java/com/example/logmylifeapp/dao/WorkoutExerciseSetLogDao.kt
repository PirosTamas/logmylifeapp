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
    suspend fun addWorkoutExerciseSetLog(question: WorkoutExerciseSetLog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutExerciseSetLogs(answers: List<WorkoutExerciseSetLog>): List<Long>

    @Update
    suspend fun updateWorkoutExerciseSetLog(question: WorkoutExerciseSetLog)

    @Delete
    suspend fun deleteWorkoutExerciseSetLog(question: WorkoutExerciseSetLog)




}