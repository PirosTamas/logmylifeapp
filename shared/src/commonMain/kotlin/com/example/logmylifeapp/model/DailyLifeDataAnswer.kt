package com.example.logmylifeapp.model

import kotlinx.datetime.LocalDate

data class DailyLifeDataAnswer(
    val id: Int = 0,
    val questionId: Int,
    val answer: String,
    val createdAt: LocalDate
)
