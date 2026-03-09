package com.example.logmylifeapp.data

import androidx.room.TypeConverter
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

class Converters {
    @TypeConverter
    fun fromDayOfWeekSet(days: Set<DayOfWeek>): String =
        days.joinToString(",") { it.name }

    @TypeConverter
    fun toDayOfWeekSet(value: String): Set<DayOfWeek> =
        if (value.isEmpty()) emptySet()
        else value.split(",").map { DayOfWeek.valueOf(it) }.toSet()

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String =
        date.toString()

    @TypeConverter
    fun toLocalDate(value: String): LocalDate =
        LocalDate.parse(value)

    @TypeConverter
    fun fromStringSet(strings: Set<String>): String =
        strings.joinToString(",")

    @TypeConverter
    fun toStringSet(value: String): Set<String> =
        if (value.isEmpty()) emptySet()
        else value.split(",").toSet()

    @TypeConverter
    fun fromStringList(strings: List<String>): String =
        strings.joinToString(",")

    @TypeConverter
    fun toStringList(value: String): List<String> =
        if (value.isEmpty()) emptyList()
        else value.split(",")
}