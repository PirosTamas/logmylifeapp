package com.example.logmylifeapp.model

data class WorkoutExerciseSetLog(
    val id: Int = 0,
    val exerciseLogId: Int,
    val order: Int,
    val targetReps: Int,
    val completedReps: Int,
    val weight: Float?,
    val success: Boolean,
    val description: String
)
