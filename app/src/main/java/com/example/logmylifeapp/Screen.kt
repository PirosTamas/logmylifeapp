package com.example.logmylifeapp

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object AddProgressScreen: Screen("add_progress_screen")
    object AddDailyLifeDataQuestionScreen: Screen("add_daily_life_data_question_screen")
    object DailyLifeDataScreen: Screen("daily_life_data_screen")
    object YearInPixelsScreen: Screen("year_in_pixels_screen")
    object WorkoutHomeScreen: Screen("workout_home_screen")
    object WorkoutPreviewScreen :
        Screen("workout_preview_screen/{planId}") {
        fun createRoute(planId: Int) =
            "workout_preview_screen/$planId"
    }
}