package com.example.logmylifeapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "workout_exercise_set_log",
    foreignKeys = [ForeignKey(
        entity = WorkoutExerciseLog::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("exerciseLogId"),
        onDelete = ForeignKey.CASCADE
    )],
)
data class WorkoutExerciseSetLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exerciseLogId: Int,
    val order: Int,
    val targetReps: Int,
    val completedReps: Int,
    val weight: Float?,
    val success: Boolean,
    val description: String
)
