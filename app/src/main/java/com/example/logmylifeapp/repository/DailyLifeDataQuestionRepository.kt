package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.DailyLifeDataQuestionDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

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

    fun getUnansweredQuestionsForToday(): Flow<List<DailyLifeDataQuestion>> {
        val today = java.time.LocalDate.now().dayOfWeek.name
        return dao.getQuestionsForDay(today, LocalDate.now())
    }
}