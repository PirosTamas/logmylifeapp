package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDate

@Entity(tableName = "workout_plan")
data class WorkoutPlan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    var scheduledDays: Set<DayOfWeek>,
    var currentSession: Int,
    val numberOfSessions: Int,
    val startDate: LocalDate
    // TODO @low: Add warmup, stretches
)