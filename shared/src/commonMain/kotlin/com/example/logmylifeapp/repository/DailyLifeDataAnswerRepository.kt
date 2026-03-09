package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.DailyLifeDataAnswerDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import kotlinx.coroutines.flow.Flow

class DailyLifeDataAnswerRepository (private val dao: DailyLifeDataAnswerDao){
    suspend fun addAnswer(answer: DailyLifeDataAnswer) {
        dao.addAnswer(answer)
    }

    suspend fun addAnswers(answers: List<DailyLifeDataAnswer>) {
        dao.addAnswers(answers)
    }

    suspend fun updateAnswer(answer: DailyLifeDataAnswer) {
        dao.updateAnswer(answer)
    }

    suspend fun deleteAnswer(answer: DailyLifeDataAnswer) {
        dao.deleteAnswer(answer)
    }

    fun getAllAnswers(): Flow<List<DailyLifeDataAnswer>> =
        dao.getAllAnswers()

    fun getAnswersForQuestion(questionId: Int): Flow<List<DailyLifeDataAnswer>> =
        dao.getAnswersForQuestion(questionId)

}