package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDate

@Entity(
    tableName = "workout_session",
    foreignKeys = [ForeignKey(
        entity = WorkoutPlan::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("planId"),
        onDelete = ForeignKey.CASCADE
    )],
)
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val planId: Int,
    val date: LocalDate,
    val completed: Boolean
)
