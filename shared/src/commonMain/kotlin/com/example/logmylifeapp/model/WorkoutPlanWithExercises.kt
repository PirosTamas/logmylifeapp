package com.example.logmylifeapp.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkoutPlanWithExercises(
    @Embedded val plan: WorkoutPlan,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WorkoutPlanExerciseCrossRef::class,
            parentColumn = "planId",
            entityColumn = "exerciseId"
        )
    )
    val mainExercises: List<WorkoutExercise>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WorkoutPlanWarmupCrossRef::class,
            parentColumn = "planId",
            entityColumn = "exerciseId"
        )
    )
    val warmups: List<WorkoutExercise>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = WorkoutPlanStretchCrossRef::class,
            parentColumn = "planId",
            entityColumn = "exerciseId"
        )
    )
    val stretches: List<WorkoutExercise>
)