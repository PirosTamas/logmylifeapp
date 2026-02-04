package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WorkoutPlanExerciseCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWorkoutPlanExerciseCrossRef(question: WorkoutPlanExerciseCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutPlanExerciseCrossRefs(answers: List<WorkoutPlanExerciseCrossRef>): List<Long>

    @Update
    suspend fun updateWorkoutPlanExerciseCrossRef(question: WorkoutPlanExerciseCrossRef)

    @Delete
    suspend fun deleteWorkoutPlanExerciseCrossRef(question: WorkoutPlanExerciseCrossRef)

    @Query("SELECT * FROM WorkoutPlanExerciseCrossRef")
    fun getAllWorkoutPlanExerciseCrossRefs(): Flow<List<WorkoutPlanExerciseCrossRef>>


}

