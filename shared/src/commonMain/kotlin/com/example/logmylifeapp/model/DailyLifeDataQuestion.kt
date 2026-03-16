package com.example.logmylifeapp.model

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class DailyLifeDataQuestion(
    val id: Int = 0,
    val question: String,
    var scheduledDays: Set<DayOfWeek>,
    val startDate: LocalDate,
    val predefinedAnswers: Set<String>,
    val customAnswerAllowed: Boolean
)
