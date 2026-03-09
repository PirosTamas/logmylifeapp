package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.WorkoutSummaryDao
import com.example.logmylifeapp.dto.WorkoutSummaryDTO
import com.example.logmylifeapp.dto.WorkoutSummaryExerciseDTO
import com.example.logmylifeapp.dto.WorkoutSummarySetDTO

class WorkoutSummaryRepository(
    private val dao: WorkoutSummaryDao,
) {

    suspend fun getWorkoutSummaryBySessionId(sessionId: Long): WorkoutSummaryDTO {
        val flatRows = dao.getWorkoutSessionFlatRowBySessionId(sessionId = sessionId)
        val planName = flatRows.firstOrNull()?.planName ?: ""

        val exercises = flatRows.groupBy { it.name }
            .map { (exerciseName, rows) ->
                WorkoutSummaryExerciseDTO(
                    name = exerciseName,
                    illustrationResId = rows.first().illustrationResId,
                    sets = rows.map {
                        WorkoutSummarySetDTO(
                            order = it.order,
                            targetReps = it.targetReps,
                            completedReps = it.completedReps,
                            weight = it.weight
                        )
                    }
                )
            }
        return WorkoutSummaryDTO(
            planName = planName,
            exercises = exercises
        )
    }


}