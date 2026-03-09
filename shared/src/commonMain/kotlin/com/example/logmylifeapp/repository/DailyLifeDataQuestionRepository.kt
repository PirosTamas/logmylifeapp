package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.DailyLifeDataQuestionDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class DailyLifeDataQuestionRepository(
    private val dao: DailyLifeDataQuestionDao
) {

    suspend fun addQuestion(question: DailyLifeDataQuestion) {
        dao.addQuestion(question)
    }

    suspend fun addQuestions(questions: List<DailyLifeDataQuestion>) {
        dao.addQuestions(questions)
    }

    suspend fun updateQuestion(question: DailyLifeDataQuestion) {
        dao.updateQuestion(question)
    }

    suspend fun deleteQuestion(question: DailyLifeDataQuestion) {
        dao.deleteQuestion(question)
    }

    fun getAllQuestions(): Flow<List<DailyLifeDataQuestion>> =
        dao.getAllQuestions()

    fun getQuestionById(id: Int): Flow<DailyLifeDataQuestion?> =
        dao.getQuestionById(id)

    suspend fun getUnansweredQuestionsForToday(): List<DailyLifeDataQuestion> {
        val todayDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
        return dao.getQuestionsForDay(todayDate.dayOfWeek.name, todayDate)
    }
}