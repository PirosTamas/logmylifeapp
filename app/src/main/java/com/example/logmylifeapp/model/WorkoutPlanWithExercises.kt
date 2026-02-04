package com.example.logmylifeapp.model;

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class WorkoutPlanWithExercises(
    @Embedded val plan: WorkoutPlan,

    @Relation(
        parentColumn = "id",        // WorkoutPlan.id
        entityColumn = "id",        // WorkoutExercise.id
        associateBy = Junction(
            value = WorkoutPlanExerciseCrossRef::class,
            parentColumn = "planId",
            entityColumn = "exerciseId"
        )
    )
    val exercises: List<WorkoutExercise>
)