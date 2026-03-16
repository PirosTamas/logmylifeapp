package com.example.logmylifeapp.model

data class WorkoutExercise(
    val id: Int = 0,
    val name: String,
    val description: String,
    val equipmentNeeded: Set<String>,
    val predictedTimeInMinutes: Int,
    val illustrationResId: Int? = null,
    val illustrationUri: String? = null,
    val numberOfSets: Int,
    val restTimeBetweenSets: Int
)
