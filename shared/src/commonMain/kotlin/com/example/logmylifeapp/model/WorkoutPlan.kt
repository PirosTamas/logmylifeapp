package com.example.logmylifeapp.model

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class WorkoutPlan(
    val id: Int = 0,
    val name: String,
    var scheduledDays: Set<DayOfWeek>,
    var currentSession: Int,
    val numberOfSessions: Int,
    val startDate: LocalDate
)
