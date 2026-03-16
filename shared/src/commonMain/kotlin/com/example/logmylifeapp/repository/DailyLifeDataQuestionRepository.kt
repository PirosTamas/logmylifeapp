package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.dayOfWeekSetAdapter
import com.example.logmylifeapp.data.localDateAdapter
import com.example.logmylifeapp.data.stringSetAdapter
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class DailyLifeDataQuestionRepository(private val db: LogMyLifeDatabase) {

    suspend fun addQuestion(question: DailyLifeDataQuestion) = withContext(Dispatchers.Default) {
        db.dailyLifeDataQuestionQueries.addQuestion(
            question = question.question,
            scheduledDays = dayOfWeekSetAdapter.encode(question.scheduledDays),
            startDate = localDateAdapter.encode(question.startDate),
            predefinedAnswers = stringSetAdapter.encode(question.predefinedAnswers),
            customAnswerAllowed = question.customAnswerAllowed
        )
    }

    suspend fun addQuestions(questions: List<DailyLifeDataQuestion>): List<Long> = withContext(Dispatchers.Default) {
        questions.map { question ->
            db.dailyLifeDataQuestionQueries.insertQuestion(
                question = question.question,
                scheduledDays = dayOfWeekSetAdapter.encode(question.scheduledDays),
                startDate = localDateAdapter.encode(question.startDate),
                predefinedAnswers = stringSetAdapter.encode(question.predefinedAnswers),
                customAnswerAllowed = question.customAnswerAllowed
            )
            db.dailyLifeDataQuestionQueries.lastInsertRowId().executeAsOne()
        }
    }

    suspend fun updateQuestion(question: DailyLifeDataQuestion) = withContext(Dispatchers.Default) {
        db.dailyLifeDataQuestionQueries.updateQuestion(
            id = question.id,
            question = question.question,
            scheduledDays = dayOfWeekSetAdapter.encode(question.scheduledDays),
            startDate = localDateAdapter.encode(question.startDate),
            predefinedAnswers = stringSetAdapter.encode(question.predefinedAnswers),
            customAnswerAllowed = question.customAnswerAllowed
        )
    }

    suspend fun deleteQuestion(question: DailyLifeDataQuestion) = withContext(Dispatchers.Default) {
        db.dailyLifeDataQuestionQueries.deleteQuestion(question.id)
    }

    fun getAllQuestions(): Flow<List<DailyLifeDataQuestion>> =
        db.dailyLifeDataQuestionQueries.getAllQuestions()
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }

    fun getQuestionById(id: Int): Flow<DailyLifeDataQuestion?> =
        db.dailyLifeDataQuestionQueries.getQuestionById(id)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it?.toModel() }

    suspend fun getUnansweredQuestionsForToday(): List<DailyLifeDataQuestion> = withContext(Dispatchers.Default) {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        db.dailyLifeDataQuestionQueries.getQuestionsForDay(
            dayOfWeek = today.dayOfWeek.name,
            day = localDateAdapter.encode(today)
        ).executeAsList().map { it.toModel() }
    }
}
