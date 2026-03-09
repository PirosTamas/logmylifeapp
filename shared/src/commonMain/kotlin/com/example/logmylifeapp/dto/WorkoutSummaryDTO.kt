package com.example.logmylifeapp.dto

data class WorkoutSessionFlatRow(
    val planName: String,
    val name: String,
    val illustrationResId: Int,
    val order: Int,
    val targetReps: Int,
    val completedReps: Int,
    val weight: Float?
)

data class WorkoutSummaryDTO(
    val planName: String,
    val exercises: List<WorkoutSummaryExerciseDTO>
)

data class WorkoutSummaryExerciseDTO(
    val name: String,
    val illustrationResId: Int,
    val sets: List<WorkoutSummarySetDTO>
)

data class WorkoutSummarySetDTO(
    val order: Int,
    val targetReps: Int,
    val completedReps: Int,
    val weight: Float?
)