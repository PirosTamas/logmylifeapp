package com.example.logmylifeapp

import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import com.example.logmylifeapp.data.Achievement_progress
import com.example.logmylifeapp.data.Daily_life_data_answer
import com.example.logmylifeapp.data.Daily_life_data_question
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.Workout_exercise
import com.example.logmylifeapp.data.Workout_exercise_log
import com.example.logmylifeapp.data.Workout_exercise_set_log
import com.example.logmylifeapp.data.Workout_plan
import com.example.logmylifeapp.data.Workout_plan_exercise_cross_ref
import com.example.logmylifeapp.data.Workout_plan_stretch_cross_ref
import com.example.logmylifeapp.data.Workout_plan_warmup_cross_ref
import com.example.logmylifeapp.data.Workout_session
import com.example.logmylifeapp.repository.AchievementProgressRepository
import com.example.logmylifeapp.repository.CurrentWorkoutExerciseRepository
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import com.example.logmylifeapp.repository.SettingsRepository
import com.example.logmylifeapp.repository.WorkoutExerciseLogRepository
import com.example.logmylifeapp.repository.WorkoutExerciseRepository
import com.example.logmylifeapp.repository.WorkoutExerciseSetLogRepository
import com.example.logmylifeapp.repository.WorkoutPlanExerciseCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import com.example.logmylifeapp.repository.WorkoutPlanStretchCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanWarmupCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutSessionRepository
import com.example.logmylifeapp.repository.WorkoutSummaryRepository

object Graph {
    lateinit var driverFactory: DatabaseDriverFactory
    lateinit var settingsRepository: SettingsRepository

    val database: LogMyLifeDatabase by lazy {
        val driver = driverFactory.createDriver()
        driver.execute(null, "PRAGMA foreign_keys = ON", 0)
        LogMyLifeDatabase(
            driver = driver,
            workout_planAdapter = Workout_plan.Adapter(
                idAdapter = IntColumnAdapter,
                currentSessionAdapter = IntColumnAdapter,
                numberOfSessionsAdapter = IntColumnAdapter
            ),
            workout_exerciseAdapter = Workout_exercise.Adapter(
                idAdapter = IntColumnAdapter,
                predictedTimeInMinutesAdapter = IntColumnAdapter,
                illustrationResIdAdapter = IntColumnAdapter,
                numberOfSetsAdapter = IntColumnAdapter,
                restTimeBetweenSetsAdapter = IntColumnAdapter
            ),
            workout_sessionAdapter = Workout_session.Adapter(
                idAdapter = IntColumnAdapter,
                planIdAdapter = IntColumnAdapter
            ),
            workout_exercise_logAdapter = Workout_exercise_log.Adapter(
                idAdapter = IntColumnAdapter,
                sessionIdAdapter = IntColumnAdapter,
                exerciseIdAdapter = IntColumnAdapter
            ),
            workout_exercise_set_logAdapter = Workout_exercise_set_log.Adapter(
                weightAdapter = FloatColumnAdapter,
                idAdapter = IntColumnAdapter,
                exerciseLogIdAdapter = IntColumnAdapter,
                orderAdapter = IntColumnAdapter,
                targetRepsAdapter = IntColumnAdapter,
                completedRepsAdapter = IntColumnAdapter
            ),
            workout_plan_exercise_cross_refAdapter = Workout_plan_exercise_cross_ref.Adapter(
                planIdAdapter = IntColumnAdapter,
                exerciseIdAdapter = IntColumnAdapter,
                orderInWorkoutAdapter = IntColumnAdapter
            ),
            workout_plan_warmup_cross_refAdapter = Workout_plan_warmup_cross_ref.Adapter(
                planIdAdapter = IntColumnAdapter,
                exerciseIdAdapter = IntColumnAdapter,
                orderInWarmupAdapter = IntColumnAdapter
            ),
            workout_plan_stretch_cross_refAdapter = Workout_plan_stretch_cross_ref.Adapter(
                planIdAdapter = IntColumnAdapter,
                exerciseIdAdapter = IntColumnAdapter,
                orderInStretchAdapter = IntColumnAdapter
            ),
            achievement_progressAdapter = Achievement_progress.Adapter(
                idAdapter = IntColumnAdapter,
                currentSessionAdapter = IntColumnAdapter,
                numberOfSessionsAdapter = IntColumnAdapter
            ),
            daily_life_data_questionAdapter = Daily_life_data_question.Adapter(
                idAdapter = IntColumnAdapter
            ),
            daily_life_data_answerAdapter = Daily_life_data_answer.Adapter(
                idAdapter = IntColumnAdapter,
                questionIdAdapter = IntColumnAdapter
            )
        )
    }

    val achievementProgressRepository by lazy {
        AchievementProgressRepository(database)
    }
    val dailyLifeDataAnswerRepository by lazy {
        DailyLifeDataAnswerRepository(database)
    }
    val dailyLifeDataQuestionRepository by lazy {
        DailyLifeDataQuestionRepository(database)
    }
    val workoutPlanRepository by lazy {
        WorkoutPlanRepository(database)
    }
    val workoutSessionRepository by lazy {
        WorkoutSessionRepository(database)
    }
    val workoutExerciseLogRepository by lazy {
        WorkoutExerciseLogRepository(database)
    }
    val workoutExerciseSetLogRepository by lazy {
        WorkoutExerciseSetLogRepository(database)
    }
    val workoutExerciseRepository by lazy {
        WorkoutExerciseRepository(database)
    }
    val currentWorkoutExerciseRepository by lazy {
        CurrentWorkoutExerciseRepository(database)
    }
    val workoutPlanExerciseCrossRefRepository by lazy {
        WorkoutPlanExerciseCrossRefRepository(database)
    }
    val workoutPlanWarmupCrossRefRepository by lazy {
        WorkoutPlanWarmupCrossRefRepository(database)
    }
    val workoutPlanStretchCrossRefRepository by lazy {
        WorkoutPlanStretchCrossRefRepository(database)
    }
    val workoutSummaryRepository by lazy {
        WorkoutSummaryRepository(database)
    }
}
