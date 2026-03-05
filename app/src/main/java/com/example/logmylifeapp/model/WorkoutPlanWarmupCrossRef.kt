package com.example.logmylifeapp.model

import androidx.room.Entity

@Entity(
    tableName = "workout_plan_warmup_cross_ref",
    primaryKeys = ["planId", "exerciseId"]
)
data class WorkoutPlanWarmupCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInWarmup: Int
)
