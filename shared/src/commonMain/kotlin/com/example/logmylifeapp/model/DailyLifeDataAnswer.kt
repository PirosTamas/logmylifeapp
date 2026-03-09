package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "daily_life_data_answer",
    foreignKeys = [ForeignKey(
        entity = DailyLifeDataQuestion::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("questionId"),
        onDelete = ForeignKey.CASCADE
    )],
)

data class DailyLifeDataAnswer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val questionId: Int,
    val answer: String,
    val createdAt: LocalDate,

)