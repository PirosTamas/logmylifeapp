package com.example.logmylifeapp

import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.model.WorkoutSession
import java.time.DayOfWeek
import java.time.LocalDate

object DummyData {

    fun questions(): List<DailyLifeDataQuestion> = listOf(
        DailyLifeDataQuestion(
            question = "How was your day?",
            predefinedAnswers = setOf("Great", "Okay", "Bad"),
            scheduledDays = setOf(
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
            ),
            startDate = LocalDate.now(),
            customAnswerAllowed = true
        ),
        DailyLifeDataQuestion(
            question = "How was your sleep?",
            predefinedAnswers = setOf("Good", "Average", "Poor"),
            scheduledDays = setOf(
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
            ),
            startDate = LocalDate.now(),
            customAnswerAllowed = true
        ),
        DailyLifeDataQuestion(
            question = "How stressed are you?",
            predefinedAnswers = setOf("Low", "Medium", "High"),
            scheduledDays = setOf(
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
            ),
            startDate = LocalDate.now(),
            customAnswerAllowed = true
        )
    )

    fun workoutPlans(): List<WorkoutPlan> = listOf(
        WorkoutPlan(
            id = 1,
            name = "Road to 70kg",
            scheduledDays = setOf(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
            ),
            currentSession = 6,
            numberOfSessions = 10,
            startDate = LocalDate.now().minusWeeks(2),
        )
    )

    fun workoutExercises() = listOf(
        WorkoutExercise(
            name = "Squats",
            description = "Leg exercise",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 10,
            illustrationResId = R.drawable.squat,
            illustrationUri = null,
            numberOfSets = 3,
            restTimeBetweenSets = 30
        ),
        WorkoutExercise(
            name = "Push-ups",
            description = "Upper body exercise",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 5,
            illustrationResId = R.drawable.benchpress,
            illustrationUri = null,
            numberOfSets = 3,
            restTimeBetweenSets = 30
        )
    )


    fun workoutSessions(planId: Int) = listOf(
        WorkoutSession(
            planId = planId,
            date = LocalDate.now().minusDays(5),
            completed = true
        ),
        WorkoutSession(
            planId = planId,
            date = LocalDate.now().minusDays(10),
            completed = true
        )
    )




}