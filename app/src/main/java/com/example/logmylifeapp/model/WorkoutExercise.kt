package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.time.DayOfWeek
import java.time.LocalDate

@Entity(tableName = "workout_exercise")
data class WorkoutExercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String,
    val equipmentNeeded: Set<String>,
    val predictedTimeInMinutes: Int,
    val illustrationResId: Int? = null,
    val illustrationUri: String? = null,
    val numberOfSets: Int,
    val restTimeBetweenSets: Int
)