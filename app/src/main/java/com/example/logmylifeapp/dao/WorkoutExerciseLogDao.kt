package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.WorkoutExerciseLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WorkoutExerciseLogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutExerciseLog(question: WorkoutExerciseLog): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutExerciseLogs(answers: List<WorkoutExerciseLog>): List<Long>

    @Update
    suspend fun updateWorkoutExerciseLog(question: WorkoutExerciseLog)

    @Delete
    suspend fun deleteWorkoutExerciseLog(question: WorkoutExerciseLog)

    @Query("SELECT * FROM workout_exercise_log")
    fun getAllWorkoutExerciseLogs(): Flow<List<WorkoutExerciseLog>>

    @Query("SELECT * FROM workout_exercise_log WHERE id = :id")
    fun getWorkoutExerciseLogById(id: Int): Flow<WorkoutExerciseLog?>


}