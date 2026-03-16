package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.localDateAdapter
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DailyLifeDataAnswerRepository(private val db: LogMyLifeDatabase) {

    suspend fun addAnswer(answer: DailyLifeDataAnswer) = withContext(Dispatchers.Default) {
        db.dailyLifeDataAnswerQueries.addAnswer(
            questionId = answer.questionId,
            answer = answer.answer,
            createdAt = localDateAdapter.encode(answer.createdAt)
        )
    }

    suspend fun addAnswers(answers: List<DailyLifeDataAnswer>) = withContext(Dispatchers.Default) {
        answers.forEach { answer ->
            db.dailyLifeDataAnswerQueries.addAnswers(
                questionId = answer.questionId,
                answer = answer.answer,
                createdAt = localDateAdapter.encode(answer.createdAt)
            )
        }
    }

    suspend fun updateAnswer(answer: DailyLifeDataAnswer) = withContext(Dispatchers.Default) {
        db.dailyLifeDataAnswerQueries.updateAnswer(
            id = answer.id,
            questionId = answer.questionId,
            answer = answer.answer,
            createdAt = localDateAdapter.encode(answer.createdAt)
        )
    }

    suspend fun deleteAnswer(answer: DailyLifeDataAnswer) = withContext(Dispatchers.Default) {
        db.dailyLifeDataAnswerQueries.deleteAnswer(answer.id)
    }

    fun getAllAnswers(): Flow<List<DailyLifeDataAnswer>> =
        db.dailyLifeDataAnswerQueries.getAllAnswers()
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }

    fun getAnswersForQuestion(questionId: Int): Flow<List<DailyLifeDataAnswer>> =
        db.dailyLifeDataAnswerQueries.getAnswersForQuestion(questionId)
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }
}
