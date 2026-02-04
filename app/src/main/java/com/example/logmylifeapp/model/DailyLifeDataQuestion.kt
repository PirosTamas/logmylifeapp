package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDate

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