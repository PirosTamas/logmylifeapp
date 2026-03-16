package com.example.logmylifeapp.model

data class WorkoutPlanWithExercises(
    val plan: WorkoutPlan,
    val mainExercises: List<WorkoutExercise>,
    val warmups: List<WorkoutExercise>,
    val stretches: List<WorkoutExercise>
)
