package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import kotlinx.coroutines.flow.Flow
@Dao
interface DailyLifeDataAnswerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAnswer(answer: DailyLifeDataAnswer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAnswers(answers: List<DailyLifeDataAnswer>)

    @Update
    suspend fun updateAnswer(answer: DailyLifeDataAnswer)

    @Delete
    suspend fun deleteAnswer(answer: DailyLifeDataAnswer)

    @Query("SELECT * FROM daily_life_data_answer")
    fun getAllAnswers(): Flow<List<DailyLifeDataAnswer>>

    @Query("SELECT * FROM daily_life_data_answer WHERE questionId = :questionId")
    fun getAnswersForQuestion(questionId: Int): Flow<List<DailyLifeDataAnswer>>



}