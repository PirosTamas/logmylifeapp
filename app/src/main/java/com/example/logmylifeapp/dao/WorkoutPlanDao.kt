package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.model.WorkoutPlanWithExercises
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WorkoutPlanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutPlan(question: WorkoutPlan)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutPlans(answers: List<WorkoutPlan>): List<Long>

    @Update
    suspend fun updateWorkoutPlan(question: WorkoutPlan)

    @Delete
    suspend fun deleteWorkoutPlan(question: WorkoutPlan)

    @Query("SELECT * FROM workout_plan")
    fun getAllWorkoutPlans(): Flow<List<WorkoutPlan>>

    @Query("SELECT * FROM workout_plan WHERE id = :id")
    fun getWorkoutPlanById(id: Int): Flow<WorkoutPlan?>

    @Query("""
        SELECT * FROM workout_plan WHERE scheduledDays LIKE '%' || :dayOfWeek || '%'
        """)
    fun getWorkoutPlanForDay(dayOfWeek: String): Flow<List<WorkoutPlan>>

    @Query("SELECT * FROM workout_plan WHERE id = :planId")
    fun getWorkoutPlanWithExercises(planId: Int): Flow<WorkoutPlanWithExercises>

    @Query("select * from workout_plan where id = (select planId from workout_session where id = :sessionId)")
    suspend fun getWorkoutPlanBySessionId(sessionId: Int): WorkoutPlan?

}