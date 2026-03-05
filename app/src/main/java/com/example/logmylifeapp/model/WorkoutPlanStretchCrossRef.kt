package com.example.logmylifeapp.model

import androidx.room.Entity

@Entity(
    tableName = "workout_plan_stretch_cross_ref",
    primaryKeys = ["planId", "exerciseId"]
)
data class WorkoutPlanStretchCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInStretch: Int
)
