package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

@Dao
interface DailyLifeDataQuestionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuestion(question: DailyLifeDataQuestion)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestions(answers: List<DailyLifeDataQuestion>): List<Long>

    @Update
    suspend fun updateQuestion(question: DailyLifeDataQuestion)

    @Delete
    suspend fun deleteQuestion(question: DailyLifeDataQuestion)

    @Query("SELECT * FROM daily_life_data_question")
    fun getAllQuestions(): Flow<List<DailyLifeDataQuestion>>

    @Query("SELECT * FROM daily_life_data_question WHERE id = :id")
    fun getQuestionById(id: Int): Flow<DailyLifeDataQuestion?>

    @Query("""
        SELECT * FROM daily_life_data_question WHERE scheduledDays LIKE '%' || :dayOfWeek || '%'
        AND id NOT IN ( 
        SELECT questionId FROM daily_life_data_answer
        WHERE createdAt = :day
        )
        """)
    suspend fun getQuestionsForDay(dayOfWeek: String, day: LocalDate): List<DailyLifeDataQuestion>
}