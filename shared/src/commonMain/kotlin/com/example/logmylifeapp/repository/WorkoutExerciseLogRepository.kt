package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.WorkoutExerciseLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WorkoutExerciseLogRepository(private val db: LogMyLifeDatabase) {

    suspend fun addWorkoutExerciseLog(log: WorkoutExerciseLog): Long = withContext(Dispatchers.Default) {
        db.workoutExerciseLogQueries.insertWorkoutExerciseLog(
            sessionId = log.sessionId,
            exerciseId = log.exerciseId
        )
        db.workoutExerciseLogQueries.lastInsertRowId().executeAsOne()
    }

    suspend fun addWorkoutExerciseLogs(logs: List<WorkoutExerciseLog>) = withContext(Dispatchers.Default) {
        logs.forEach { log ->
            db.workoutExerciseLogQueries.addWorkoutExerciseLog(
                sessionId = log.sessionId,
                exerciseId = log.exerciseId
            )
        }
    }

    suspend fun updateWorkoutExerciseLog(log: WorkoutExerciseLog) = withContext(Dispatchers.Default) {
        db.workoutExerciseLogQueries.updateWorkoutExerciseLog(
            id = log.id,
            sessionId = log.sessionId,
            exerciseId = log.exerciseId
        )
    }

    suspend fun deleteWorkoutExerciseLog(log: WorkoutExerciseLog) = withContext(Dispatchers.Default) {
        db.workoutExerciseLogQueries.deleteWorkoutExerciseLog(log.id)
    }

    fun getCurrentExerciseIndexBySessionId(sessionId: Int): Flow<Int> =
        db.workoutExerciseLogQueries.getCurrentExerciseIndexBySessionId(sessionId)
            .asFlow().mapToOne(Dispatchers.Default)
            .map { it.toInt() }
}
