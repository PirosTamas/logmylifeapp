package com.example.logmylifeapp.repository

import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.dto.WorkoutSummaryDTO
import com.example.logmylifeapp.dto.WorkoutSummaryExerciseDTO
import com.example.logmylifeapp.dto.WorkoutSummarySetDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkoutSummaryRepository(private val db: LogMyLifeDatabase) {

    suspend fun getWorkoutSummaryBySessionId(sessionId: Long): WorkoutSummaryDTO = withContext(Dispatchers.Default) {
        val rows = db.workoutExerciseSetLogQueries
            .getWorkoutSessionSummary(sessionId.toInt())
            .executeAsList()

        val planName = rows.firstOrNull()?.planName ?: ""

        val exercises = rows.groupBy { it.name }
            .map { (exerciseName, rowGroup) ->
                WorkoutSummaryExerciseDTO(
                    name = exerciseName,
                    illustrationResId = rowGroup.first().illustrationResId,
                    sets = rowGroup.map {
                        WorkoutSummarySetDTO(
                            order = it.order,
                            targetReps = it.targetReps,
                            completedReps = it.completedReps,
                            weight = it.weight
                        )
                    }
                )
            }

        WorkoutSummaryDTO(planName = planName, exercises = exercises)
    }
}
