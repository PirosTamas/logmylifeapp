package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WorkoutExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutExercise(question: WorkoutExercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutExercises(answers: List<WorkoutExercise>): List<Long>

    @Update
    suspend fun updateWorkoutExercise(question: WorkoutExercise)

    @Delete
    suspend fun deleteWorkoutExercise(question: WorkoutExercise)

    @Query("SELECT * FROM workout_exercise")
    fun getAllWorkoutExercises(): Flow<List<WorkoutExercise>>

    @Query("SELECT * FROM workout_exercise WHERE id = :id")
    fun getWorkoutExerciseById(id: Int): Flow<WorkoutExercise?>



}