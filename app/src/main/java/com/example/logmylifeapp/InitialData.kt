package com.example.logmylifeapp

import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import java.time.DayOfWeek
import java.time.LocalDate

object InitialData {

    fun questions(): List<DailyLifeDataQuestion> = listOf(
        DailyLifeDataQuestion(
            question = "How was your day?",
            predefinedAnswers = setOf("1", "2", "3", "4", "5"),
            scheduledDays = setOf(DayOfWeek.TUESDAY,DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
            startDate = LocalDate.now(),
            customAnswerAllowed = false
        )
    )
    fun answers(questionId: Int) : List<DailyLifeDataAnswer> {
        val startDate = LocalDate.of(2026,1,1)
        val endDate = LocalDate.of(2026,1,30)

        return generateSequence(startDate){ it.plusDays(1)}
            .takeWhile { !it.isAfter(endDate) }
            .map{
                DailyLifeDataAnswer(questionId = questionId, answer = (1..5).random().toString(), createdAt = it)
            }
            .toList()
    }

}