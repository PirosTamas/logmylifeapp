package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "workout_plan_exercise_cross_ref",
    primaryKeys = ["planId", "exerciseId"],
        indices = [
        Index(value = ["planId"]),
        Index(value = ["exerciseId"])
    ]
)
data class WorkoutPlanExerciseCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInWorkout: Int
)
