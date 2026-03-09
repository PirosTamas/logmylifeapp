package com.example.logmylifeapp

import com.example.logmylifeapp.enums.WorkoutExerciseType
import com.example.logmylifeapp.model.WorkoutExerciseLog

sealed class Screen(val route: String) {
    object ProgressHomeScreen: Screen("home_screen")
    object AddProgressScreen: Screen("add_progress_screen")
    object AddDailyLifeDataQuestionScreen: Screen("add_daily_life_data_question_screen")
    object DailyLifeDataScreen: Screen("daily_life_data_screen")
    object YearInPixelsScreen: Screen("year_in_pixels_screen")
    object WorkoutHomeScreen: Screen("workout_home_screen")
    object WorkoutPreviewScreen: Screen("workout_preview_screen")

    object WorkoutSetScreen: Screen("workout_set_screen")


    object WorkoutSummaryScreen :
        Screen("workout_summary_screen/{sessionId}") {
        fun createRoute(sessionId: Long) =
            "workout_summary_screen/$sessionId"
    }
    object SettingsHomeScreen: Screen("settings_home_screen")
    object AddWorkoutPlanScreen: Screen("add_workout_plan")
    object SelectExerciseScreen : Screen(
        "select_exercise/{exerciseType}"
    ) {
        fun createRoute(type: WorkoutExerciseType): String {
            return "select_exercise/${type.name}"
        }
    }
    object PredefinedAnswersScreen : Screen("predefined_answers_screen")
}