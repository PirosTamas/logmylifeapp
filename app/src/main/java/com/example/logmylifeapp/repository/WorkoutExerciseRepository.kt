package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.WorkoutExerciseDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExercise
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class WorkoutExerciseRepository(
    private val dao: WorkoutExerciseDao
) {

    suspend fun addWorkoutExercise(workoutExercise: WorkoutExercise) {
        dao.addWorkoutExercise(workoutExercise)
    }

    suspend fun addWorkoutExercises(workoutExercises: List<WorkoutExercise>) {
        dao.addWorkoutExercises(workoutExercises)
    }

    suspend fun updateWorkoutExercise(workoutExercise: WorkoutExercise) {
        dao.updateWorkoutExercise(workoutExercise)
    }

    suspend fun deleteWorkoutExercise(workoutExercise: WorkoutExercise) {
        dao.deleteWorkoutExercise(workoutExercise)
    }

    fun getAllWorkoutExercises(): Flow<List<WorkoutExercise>> =
        dao.getAllWorkoutExercises()

    fun getWorkoutExerciseById(id: Int): Flow<WorkoutExercise?> =
        dao.getWorkoutExerciseById(id)

    fun getWorkoutExerciseBySessionIdAndOrderInWorkout(sessionId: Int, orderInWorkout: Int): Flow<WorkoutExercise?>{
        return dao.getWorkoutExerciseBySessionIdAndOrderInWorkout(sessionId, orderInWorkout)
    }

    
}