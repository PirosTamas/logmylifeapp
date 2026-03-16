package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.localDateAdapter
import com.example.logmylifeapp.dto.CurrentWorkoutExerciseDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrentWorkoutExerciseRepository(private val db: LogMyLifeDatabase) {

    fun getWorkoutExerciseBySessionIdAndExerciseIdAndSetIndex(
        sessionId: Int,
        exerciseId: Int,
        setIndex: Int
    ): Flow<CurrentWorkoutExerciseDTO?> =
        db.workoutExerciseSetLogQueries
            .getWorkoutExerciseBySessionIdAndExerciseIdAndSetIndex(sessionId, exerciseId, setIndex)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { row ->
                row?.let {
                    CurrentWorkoutExerciseDTO(
                        exerciseName = it.exerciseName,
                        targetReps = it.targetReps,
                        completedReps = it.completedReps,
                        weight = it.weight ?: 0f,
                        order = it.order,
                        numberOfSets = it.numberOfSets,
                        restTimeBetweenSets = it.restTimeBetweenSets,
                        date = localDateAdapter.decode(it.date)
                    )
                }
            }
}
