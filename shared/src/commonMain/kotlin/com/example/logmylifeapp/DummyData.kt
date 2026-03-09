package com.example.logmylifeapp

import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.model.WorkoutSession
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn

object DummyData {

    fun questions(): List<DailyLifeDataQuestion> = listOf(
        DailyLifeDataQuestion(
            question = "How was your day?",
            predefinedAnswers = setOf("Great", "Okay", "Bad"),
            scheduledDays = setOf(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
            startDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
            customAnswerAllowed = true
        ),
        DailyLifeDataQuestion(
            question = "How was your sleep?",
            predefinedAnswers = setOf("Good", "Average", "Poor"),
            scheduledDays = setOf(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
            startDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
            customAnswerAllowed = true
        ),
        DailyLifeDataQuestion(
            question = "How stressed are you?",
            predefinedAnswers = setOf("Low", "Medium", "High"),
            scheduledDays = setOf(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
            startDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
            customAnswerAllowed = true
        )
    )

    fun workoutPlans(): List<WorkoutPlan> = listOf(
        WorkoutPlan(
            id = 1,
            name = "Road to 70kg",
            scheduledDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            currentSession = 6,
            numberOfSessions = 10,
            startDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(DatePeriod(days = 14))
        )
    )

    fun workoutExercises() = listOf(
        WorkoutExercise(
            name = "Squats",
            description = "Leg exercise",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 10,
            illustrationResId = null,
            illustrationUri = null,
            numberOfSets = 3,
            restTimeBetweenSets = 30
        ),
        WorkoutExercise(
            name = "Push-ups",
            description = "Upper body exercise",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 5,
            illustrationResId = null,
            illustrationUri = null,
            numberOfSets = 3,
            restTimeBetweenSets = 30
        )
    )

    fun warmupExercises() = listOf(
        WorkoutExercise(
            name = "Jumping Jacks",
            description = "Full body warmup to raise heart rate before training",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 3,
            illustrationResId = null,
            illustrationUri = null,
            numberOfSets = 1,
            restTimeBetweenSets = 0
        ),
        WorkoutExercise(
            name = "Arm Circles",
            description = "Shoulder mobility warmup",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 2,
            illustrationResId = null,
            illustrationUri = null,
            numberOfSets = 1,
            restTimeBetweenSets = 0
        )
    )

    fun stretchExercises() = listOf(
        WorkoutExercise(
            name = "Hamstring Stretch",
            description = "Hold each side for 30 seconds to improve flexibility",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 2,
            illustrationResId = null,
            illustrationUri = null,
            numberOfSets = 1,
            restTimeBetweenSets = 0
        ),
        WorkoutExercise(
            name = "Chest Opener",
            description = "Interlace fingers behind back and open chest",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 2,
            illustrationResId = null,
            illustrationUri = null,
            numberOfSets = 1,
            restTimeBetweenSets = 0
        )
    )

    fun workoutSessions(planId: Int) = listOf(
        WorkoutSession(
            planId = planId,
            date = Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(5, DateTimeUnit.DAY),
            completed = true
        ),
        WorkoutSession(
            planId = planId,
            date = Clock.System.todayIn(TimeZone.currentSystemDefault()).minus(10, DateTimeUnit.DAY),
            completed = true
        )
    )
}
