package com.example.logmylifeapp

import com.example.logmylifeapp.data.LogMyLifeDatabase
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
    lateinit var database: LogMyLifeDatabase
    lateinit var settingsRepository: SettingsRepository

    val achievementProgressRepository by lazy {
        AchievementProgressRepository(achievementProgressDao = database.achievementProgressDao())
    }
    val dailyLifeDataAnswerRepository by lazy {
        DailyLifeDataAnswerRepository(dao = database.dailyLifeDataAnswerDao())
    }
    val dailyLifeDataQuestionRepository by lazy {
        DailyLifeDataQuestionRepository(dao = database.dailyLifeDataQuestionDao())
    }
    val workoutPlanRepository by lazy {
        WorkoutPlanRepository(dao = database.workoutPlanDao())
    }
    val workoutSessionRepository by lazy {
        WorkoutSessionRepository(dao = database.workoutSessionDao())
    }
    val workoutExerciseLogRepository by lazy {
        WorkoutExerciseLogRepository(dao = database.workoutExerciseLogDao())
    }
    val workoutExerciseSetLogRepository by lazy {
        WorkoutExerciseSetLogRepository(dao = database.workoutExerciseSetLogDao())
    }
    val workoutExerciseRepository by lazy {
        WorkoutExerciseRepository(dao = database.workoutExerciseDao())
    }
    val currentWorkoutExerciseRepository by lazy {
        CurrentWorkoutExerciseRepository(dao = database.currentWorkoutExerciseDao())
    }
    val workoutPlanExerciseCrossRefRepository by lazy {
        WorkoutPlanExerciseCrossRefRepository(dao = database.workoutPlanExerciseCrossRefDao())
    }
    val workoutPlanWarmupCrossRefRepository by lazy {
        WorkoutPlanWarmupCrossRefRepository(dao = database.workoutPlanWarmupCrossRefDao())
    }
    val workoutPlanStretchCrossRefRepository by lazy {
        WorkoutPlanStretchCrossRefRepository(dao = database.workoutPlanStretchCrossRefDao())
    }
    val workoutSummaryRepository by lazy {
        WorkoutSummaryRepository(dao = database.workoutSummaryDao())
    }
}
