package com.example.logmylifeapp.model

import androidx.room.Entity

@Entity(
    tableName = "workout_plan_exercise_cross_ref",
    primaryKeys = ["planId", "exerciseId"]
)
data class WorkoutPlanExerciseCrossRef(
    val planId: Int,
    val exerciseId: Int,
    val orderInWorkout: Int
)