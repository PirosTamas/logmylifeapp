package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.WorkoutSession
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutSessionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutSession(question: WorkoutSession): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutSessions(answers: List<WorkoutSession>): List<Long>

    @Update
    suspend fun updateWorkoutSession(question: WorkoutSession)

    @Delete
    suspend fun deleteWorkoutSession(question: WorkoutSession)

    @Query("SELECT * FROM workout_session")
    fun getAllWorkoutSessions(): Flow<List<WorkoutSession>>

    @Query("SELECT * FROM workout_session WHERE id = :id")
    fun getWorkoutSessionById(id: Int): Flow<WorkoutSession?>

    @Query("UPDATE workout_session SET completed = 1 WHERE id = :sessionId")
    suspend fun setSessionCompleted(sessionId: Long)

}