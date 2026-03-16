package com.example.logmylifeapp.data

import app.cash.sqldelight.ColumnAdapter
import com.example.logmylifeapp.model.AchievementCategory
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

val localDateAdapter = object : ColumnAdapter<LocalDate, String> {
    override fun decode(databaseValue: String) = LocalDate.parse(databaseValue)
    override fun encode(value: LocalDate) = value.toString()
}

val dayOfWeekSetAdapter = object : ColumnAdapter<Set<DayOfWeek>, String> {
    override fun decode(databaseValue: String): Set<DayOfWeek> =
        if (databaseValue.isEmpty()) emptySet()
        else databaseValue.split(",").map { DayOfWeek.valueOf(it) }.toSet()
    override fun encode(value: Set<DayOfWeek>) = value.joinToString(",") { it.name }
}

val stringSetAdapter = object : ColumnAdapter<Set<String>, String> {
    override fun decode(databaseValue: String): Set<String> =
        if (databaseValue.isEmpty()) emptySet()
        else databaseValue.split(",").toSet()
    override fun encode(value: Set<String>) = value.joinToString(",")
}

val achievementCategoryAdapter = object : ColumnAdapter<AchievementCategory, String> {
    override fun decode(databaseValue: String) = AchievementCategory.valueOf(databaseValue)
    override fun encode(value: AchievementCategory) = value.name
}
