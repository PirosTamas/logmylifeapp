package com.example.logmylifeapp

import com.example.logmylifeapp.model.WorkoutExerciseLog

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AddProgressScreen: Screen("add_progress_screen")
    object AddDailyLifeDataQuestionScreen: Screen("add_daily_life_data_question_screen")
    object DailyLifeDataScreen: Screen("daily_life_data_screen")
    object YearInPixelsScreen: Screen("year_in_pixels_screen")
    object WorkoutHomeScreen: Screen("workout_home_screen")
    object WorkoutPreviewScreen :
        Screen("workout_preview_screen/{sessionId}/{orderIndex}") {
        fun createRoute(sessionId: Long, orderIndex: Int) =
            "workout_preview_screen/$sessionId/$orderIndex"
    }

    object WorkoutSetScreen :
        Screen("workout_set_screen/{sessionId}/{exerciseIndex}/{exerciseLogId}/{setIndex}") {
        fun createRoute(sessionId: Long, exerciseIndex: Int, exerciseLogId: Long, setIndex: Int) =
            "workout_set_screen/$sessionId/$exerciseIndex/$exerciseLogId/$setIndex"
    }

    object WorkoutFailureScreen :
        Screen("workout_failure_screen/{sessionId}/{exerciseIndex}/{exerciseLogId}/{setIndex}") {
        fun createRoute(sessionId: Long, exerciseIndex: Int, exerciseLogId: Long, setIndex: Int) =
            "workout_failure_screen/$sessionId/$exerciseIndex/$exerciseLogId/$setIndex"
    }
    object WorkoutSummaryScreen :
        Screen("workout_summary_screen/{sessionId}") {
        fun createRoute(sessionId: Long) =
            "workout_summary_screen/$sessionId"
    }
}