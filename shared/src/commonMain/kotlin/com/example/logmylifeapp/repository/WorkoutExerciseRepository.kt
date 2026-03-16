package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.stringSetAdapter
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.WorkoutExercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WorkoutExerciseRepository(private val db: LogMyLifeDatabase) {

    suspend fun addWorkoutExercise(exercise: WorkoutExercise) = withContext(Dispatchers.Default) {
        db.workoutExerciseQueries.addWorkoutExercise(
            name = exercise.name,
            description = exercise.description,
            equipmentNeeded = stringSetAdapter.encode(exercise.equipmentNeeded),
            predictedTimeInMinutes = exercise.predictedTimeInMinutes,
            illustrationResId = exercise.illustrationResId,
            illustrationUri = exercise.illustrationUri,
            numberOfSets = exercise.numberOfSets,
            restTimeBetweenSets = exercise.restTimeBetweenSets
        )
    }

    suspend fun addWorkoutExercises(exercises: List<WorkoutExercise>): List<Long> = withContext(Dispatchers.Default) {
        exercises.map { exercise ->
            db.workoutExerciseQueries.insertWorkoutExercise(
                name = exercise.name,
                description = exercise.description,
                equipmentNeeded = stringSetAdapter.encode(exercise.equipmentNeeded),
                predictedTimeInMinutes = exercise.predictedTimeInMinutes,
                illustrationResId = exercise.illustrationResId,
                illustrationUri = exercise.illustrationUri,
                numberOfSets = exercise.numberOfSets,
                restTimeBetweenSets = exercise.restTimeBetweenSets
            )
            db.workoutExerciseQueries.lastInsertRowId().executeAsOne()
        }
    }

    suspend fun updateWorkoutExercise(exercise: WorkoutExercise) = withContext(Dispatchers.Default) {
        db.workoutExerciseQueries.updateWorkoutExercise(
            id = exercise.id,
            name = exercise.name,
            description = exercise.description,
            equipmentNeeded = stringSetAdapter.encode(exercise.equipmentNeeded),
            predictedTimeInMinutes = exercise.predictedTimeInMinutes,
            illustrationResId = exercise.illustrationResId,
            illustrationUri = exercise.illustrationUri,
            numberOfSets = exercise.numberOfSets,
            restTimeBetweenSets = exercise.restTimeBetweenSets
        )
    }

    suspend fun deleteWorkoutExercise(exercise: WorkoutExercise) = withContext(Dispatchers.Default) {
        db.workoutExerciseQueries.deleteWorkoutExercise(exercise.id)
    }

    fun getAllWorkoutExercises(): Flow<List<WorkoutExercise>> =
        db.workoutExerciseQueries.getAllWorkoutExercises()
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }

    fun getWorkoutExerciseById(id: Int): Flow<WorkoutExercise?> =
        db.workoutExerciseQueries.getWorkoutExerciseById(id)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it?.toModel() }

    fun getWorkoutExerciseBySessionIdAndOrderInWorkout(sessionId: Int, orderInWorkout: Int): Flow<WorkoutExercise?> =
        db.workoutExerciseQueries.getExerciseBySessionIdAndOrder(sessionId, orderInWorkout)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it?.toModel() }
}
