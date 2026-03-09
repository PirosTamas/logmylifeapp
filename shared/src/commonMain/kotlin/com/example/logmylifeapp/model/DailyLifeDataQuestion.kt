package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Entity(tableName="daily_life_data_question")
data class DailyLifeDataQuestion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val question: String,
    var scheduledDays: Set<DayOfWeek>,
    val startDate: LocalDate,
    val predefinedAnswers: Set<String>,
    val customAnswerAllowed: Boolean
)