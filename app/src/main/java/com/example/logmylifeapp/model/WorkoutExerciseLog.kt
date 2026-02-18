package com.example.logmylifeapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Entity(
    tableName = "workout_exercise_log",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSession::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sessionId"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutExercise::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("exerciseId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
)
@Parcelize
data class WorkoutExerciseLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sessionId: Int,
    val exerciseId: Int
): Parcelable
