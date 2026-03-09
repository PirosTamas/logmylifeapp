package com.example.logmylifeapp

import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn

object InitialData {

    fun questions(): List<DailyLifeDataQuestion> = listOf(
        DailyLifeDataQuestion(
            question = "How was your day?",
            predefinedAnswers = setOf("1", "2", "3", "4", "5"),
            scheduledDays = setOf(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
            startDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
            customAnswerAllowed = false
        )
    )

    fun answers(questionId: Int): List<DailyLifeDataAnswer> {
        val startDate = LocalDate(2026, 1, 1)
        val endDate = LocalDate(2026, 1, 30)

        return generateSequence(startDate) { it.plus(1, DateTimeUnit.DAY) }
            .takeWhile { it <= endDate }
            .map {
                DailyLifeDataAnswer(questionId = questionId, answer = (1..5).random().toString(), createdAt = it)
            }
            .toList()
    }
}
