package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOne
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WorkoutExerciseSetLogRepository(private val db: LogMyLifeDatabase) {

    suspend fun addWorkoutExerciseSetLog(log: WorkoutExerciseSetLog) = withContext(Dispatchers.Default) {
        db.workoutExerciseSetLogQueries.addWorkoutExerciseSetLog(
            exerciseLogId = log.exerciseLogId,
            order = log.order,
            targetReps = log.targetReps,
            completedReps = log.completedReps,
            weight = log.weight,
            success = log.success,
            description = log.description
        )
    }

    suspend fun addWorkoutExerciseSetLogs(logs: List<WorkoutExerciseSetLog>) = withContext(Dispatchers.Default) {
        logs.forEach { log ->
            db.workoutExerciseSetLogQueries.insertWorkoutExerciseSetLog(
                exerciseLogId = log.exerciseLogId,
                order = log.order,
                targetReps = log.targetReps,
                completedReps = log.completedReps,
                weight = log.weight,
                success = log.success,
                description = log.description
            )
        }
    }

    suspend fun updateWorkoutExerciseSetLog(log: WorkoutExerciseSetLog) = withContext(Dispatchers.Default) {
        db.workoutExerciseSetLogQueries.updateWorkoutExerciseSetLog(
            id = log.id,
            exerciseLogId = log.exerciseLogId,
            order = log.order,
            targetReps = log.targetReps,
            completedReps = log.completedReps,
            weight = log.weight,
            success = log.success,
            description = log.description
        )
    }

    suspend fun deleteWorkoutExerciseSetLog(log: WorkoutExerciseSetLog) = withContext(Dispatchers.Default) {
        db.workoutExerciseSetLogQueries.deleteWorkoutExerciseSetLog(log.id)
    }

    fun getCurrentSetIndexBySessionIdAndExerciseIndex(sessionId: Int, exerciseIndex: Int): Flow<Int> =
        db.workoutExerciseSetLogQueries.getCurrentSetIndexBySessionIdAndExerciseIndex(sessionId, exerciseIndex)
            .asFlow().mapToOne(Dispatchers.Default)
            .map { it.toInt() }
}
