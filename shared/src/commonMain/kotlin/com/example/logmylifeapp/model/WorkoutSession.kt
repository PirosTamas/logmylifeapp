package com.example.logmylifeapp.model

import kotlinx.datetime.LocalDate

data class WorkoutSession(
    val id: Int = 0,
    val planId: Int,
    val date: LocalDate,
    val completed: Boolean
)
