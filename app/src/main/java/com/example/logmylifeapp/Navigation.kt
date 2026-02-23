package com.example.logmylifeapp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.screen.AddDailyLifeDataQuestionScreen
import com.example.logmylifeapp.screen.AddProgressScreen
import com.example.logmylifeapp.screen.DailyLifeDataScreen
import com.example.logmylifeapp.screen.HomeScreen
import com.example.logmylifeapp.screen.WorkoutFailureScreen
import com.example.logmylifeapp.screen.WorkoutHomeScreen
import com.example.logmylifeapp.screen.WorkoutPreviewScreen
import com.example.logmylifeapp.screen.WorkoutSetScreen
import com.example.logmylifeapp.screen.WorkoutSummaryScreen
import com.example.logmylifeapp.screen.YearInPixelsScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
                navigateToAddProgress = {
                navController.navigate(Screen.AddProgressScreen.route)
            }, navigateToAddQuestion = {
                navController.navigate(Screen.AddDailyLifeDataQuestionScreen.route)
            }, navigateToDailyLifeData = {
                navController.navigate(Screen.DailyLifeDataScreen.route)
            },
                navigateToYearInPixels = {
                    navController.navigate(Screen.YearInPixelsScreen.route)
                })
        }
        composable(route = Screen.AddProgressScreen.route) {
            AddProgressScreen(navigateToHome = {
                navController.navigate(Screen.HomeScreen.route)
            }
            )
        }
        composable(route = Screen.AddDailyLifeDataQuestionScreen.route) {
            AddDailyLifeDataQuestionScreen(navigateToHome = {
                navController.navigate(Screen.HomeScreen.route)
            })
        }
        composable(route = Screen.DailyLifeDataScreen.route) {
            DailyLifeDataScreen(navigateToHome = {
                navController.navigate(Screen.HomeScreen.route)
            })
        }
        composable(route = Screen.YearInPixelsScreen.route) {
            YearInPixelsScreen(navigateToHome = {
                navController.navigate(Screen.HomeScreen.route)
            })
        }
        composable(route = Screen.WorkoutHomeScreen.route) {
            WorkoutHomeScreen(navController = navController)
        }
        composable(route = Screen.WorkoutPreviewScreen.route, arguments = listOf(
            navArgument("sessionId"){ type = NavType.LongType },
            navArgument("orderIndex"){ type = NavType.IntType }
        )) {
                backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            val orderIndex = backStackEntry.arguments?.getInt("orderIndex") ?: return@composable
            WorkoutPreviewScreen(sessionId = sessionId.toInt(), workoutOrder = orderIndex, navController = navController)
        }
        composable(route = Screen.WorkoutSetScreen.route, arguments = listOf(
            navArgument("sessionId"){ type = NavType.LongType },
            navArgument("exerciseIndex"){ type = NavType.IntType },
            navArgument("exerciseLogId"){ type = NavType.LongType },
            navArgument("setIndex"){ type = NavType.IntType }
        )) {
                backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            val exerciseIndex = backStackEntry.arguments?.getInt("exerciseIndex") ?: return@composable
            val exerciseLogId = backStackEntry.arguments?.getLong("exerciseLogId") ?: return@composable
            val setIndex = backStackEntry.arguments?.getInt("setIndex") ?: return@composable
            WorkoutSetScreen(sessionId = sessionId, exerciseIndex = exerciseIndex, exerciseLogId = exerciseLogId.toInt(), setIndex = setIndex, navController = navController)
        }
        composable(route = Screen.WorkoutFailureScreen.route, arguments = listOf(
            navArgument("sessionId"){ type = NavType.LongType },
            navArgument("exerciseIndex"){ type = NavType.IntType },
            navArgument("exerciseLogId"){ type = NavType.LongType },
            navArgument("setIndex"){ type = NavType.IntType }
        )) {
                backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            val exerciseIndex = backStackEntry.arguments?.getInt("exerciseIndex") ?: return@composable
            val exerciseLogId = backStackEntry.arguments?.getLong("exerciseLogId") ?: return@composable
            val setIndex = backStackEntry.arguments?.getInt("setIndex") ?: return@composable
            WorkoutFailureScreen(sessionId = sessionId, exerciseIndex = exerciseIndex,exerciseLogId = exerciseLogId.toInt(), setIndex = setIndex, navController = navController)
        }
        composable(route = Screen.WorkoutSummaryScreen.route, arguments = listOf(
            navArgument("sessionId"){ type = NavType.LongType },
        )) {
                backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            WorkoutSummaryScreen(sessionId = sessionId,  navController = navController)
        }
    }

}