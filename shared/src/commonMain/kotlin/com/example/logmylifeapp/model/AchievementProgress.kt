package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
@Entity(tableName="achievement_progress")
data class AchievementProgress(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    val category: AchievementCategory,
    var scheduledDays: Set<DayOfWeek>,
    var currentSession: Int,
    val numberOfSessions: Int,
    var dayChecked: Boolean = false,
    val startDate: LocalDate
)

