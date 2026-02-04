package com.example.logmylifeapp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.logmylifeapp.screen.AddDailyLifeDataQuestionScreen
import com.example.logmylifeapp.screen.AddProgressScreen
import com.example.logmylifeapp.screen.DailyLifeDataScreen
import com.example.logmylifeapp.screen.HomeScreen
import com.example.logmylifeapp.screen.WorkoutHomeScreen
import com.example.logmylifeapp.screen.WorkoutPreviewScreen
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
            navArgument("planId"){ type = NavType.IntType }
        )) {
                backStackEntry ->
            val planId = backStackEntry.arguments?.getInt("planId") ?: return@composable
            WorkoutPreviewScreen(planId = planId)
        }
    }

}