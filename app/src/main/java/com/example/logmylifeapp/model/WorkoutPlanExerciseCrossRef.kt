package com.example.logmylifeapp.model

import androidx.room.Entity

@Entity(
    primaryKeys = ["planId", "exerciseId"]
)
data class WorkoutPlanExerciseCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInWorkout: Int
)