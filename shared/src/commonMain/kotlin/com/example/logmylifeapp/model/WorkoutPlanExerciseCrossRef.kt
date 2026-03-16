package com.example.logmylifeapp.model

data class WorkoutPlanExerciseCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInWorkout: Int
)
