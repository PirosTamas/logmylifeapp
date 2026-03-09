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

@Dao
interface WorkoutExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutExercise(question: WorkoutExercise)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutExercises(answers: List<WorkoutExercise>): List<Long>

    @Update
    suspend fun updateWorkoutExercise(answers: WorkoutExercise)

    @Delete
    suspend fun deleteWorkoutExercise(answers: WorkoutExercise)

    @Query("SELECT * FROM workout_exercise")
    fun getAllWorkoutExercises(): Flow<List<WorkoutExercise>>

    @Query("SELECT * FROM workout_exercise WHERE id = :id")
    fun getWorkoutExerciseById(id: Int): Flow<WorkoutExercise?>

    @Query("""select we.*  from workout_session ws 
        join workout_plan_exercise_cross_ref wpe on ws.planId = wpe.planId  
        join workout_exercise we on wpe.exerciseId = we.id  
        where ws.id = :sessionId and wpe.orderInWorkout = :orderInWorkout""")
    fun getWorkoutExerciseBySessionIdAndOrderInWorkout(sessionId: Int, orderInWorkout: Int): Flow<WorkoutExercise?>


}