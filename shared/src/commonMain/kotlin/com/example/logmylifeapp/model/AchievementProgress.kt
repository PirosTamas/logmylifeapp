package com.example.logmylifeapp.model

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class AchievementProgress(
    val id: Int = 0,
    var name: String,
    val category: AchievementCategory,
    var scheduledDays: Set<DayOfWeek>,
    var currentSession: Int,
    val numberOfSessions: Int,
    var dayChecked: Boolean = false,
    val startDate: LocalDate
)
