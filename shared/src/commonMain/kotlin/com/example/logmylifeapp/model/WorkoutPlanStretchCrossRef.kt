package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "workout_plan_stretch_cross_ref",
    primaryKeys = ["planId", "exerciseId"],
    indices = [
        Index(value = ["planId"]),
        Index(value = ["exerciseId"])
    ]
)
data class WorkoutPlanStretchCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInStretch: Int
)
