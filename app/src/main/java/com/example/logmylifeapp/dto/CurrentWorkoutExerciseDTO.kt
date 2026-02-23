package com.example.logmylifeapp.dto

import java.time.LocalDate
import java.util.Date

data class CurrentWorkoutExerciseDTO (
    val exerciseName: String,
    val targetReps: Int,
    val completedReps: Int,
    val weight: Float,
    val order: Int,
    val numberOfSets: Int,
    val restTimeBetweenSets: Int,
    val date: LocalDate
)