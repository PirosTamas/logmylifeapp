package com.example.logmylifeapp

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.logmylifeapp.enums.WorkoutExerciseType
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.screen.AddDailyLifeDataQuestionScreen
import com.example.logmylifeapp.screen.AddProgressScreen
import com.example.logmylifeapp.screen.AddWorkoutPlanScreen
import com.example.logmylifeapp.screen.DailyLifeDataScreen
import com.example.logmylifeapp.screen.HomeScreen
import com.example.logmylifeapp.screen.SelectExerciseScreen
import com.example.logmylifeapp.screen.WorkoutFailureScreen
import com.example.logmylifeapp.screen.WorkoutHomeScreen
import com.example.logmylifeapp.screen.WorkoutPreviewScreen
import com.example.logmylifeapp.screen.WorkoutSetScreen
import com.example.logmylifeapp.screen.WorkoutSummaryScreen
import com.example.logmylifeapp.screen.YearInPixelsScreen
import com.example.logmylifeapp.viewmodel.AddWorkoutPlanViewModel
import com.example.logmylifeapp.viewmodel.WorkoutSessionViewModel
import com.example.logmylifeapp.viewmodel.WorkoutSessionViewModelFactory

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
        navigation(
            route = "workout",
            startDestination = Screen.WorkoutHomeScreen.route,

            ) {
            composable(route = Screen.WorkoutHomeScreen.route) {
                WorkoutHomeScreen(navigateToCurrentWorkout = { sessionId ->
                    navController.navigate("current_workout/$sessionId")
                }, navigateToAdd = {
                    navController.navigate(Screen.AddWorkoutPlanScreen.route)
                })
            }

            navigation(
                route = "current_workout/{sessionId}",
                startDestination = Screen.WorkoutPreviewScreen.route,
                arguments = listOf(
                    navArgument("sessionId") { type = NavType.LongType }
                )) {
                composable(
                    route = Screen.WorkoutPreviewScreen.route,
                ) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("current_workout/{sessionId}")
                    }

                    val sessionId = parentEntry.arguments?.getLong("sessionId")
                        ?: return@composable
                    val viewModel: WorkoutSessionViewModel = viewModel(
                        parentEntry,
                        factory = WorkoutSessionViewModelFactory(sessionId)
                    )


                    WorkoutPreviewScreen(
                        viewModel = viewModel,
                        navigateToHome = {
                            navController.navigate(Screen.WorkoutHomeScreen.route)
                        },
                        navigateToSet = {
                            navController.navigate(Screen.WorkoutSetScreen.route) {
                                popUpTo(Screen.WorkoutPreviewScreen.route) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        },
                    )
                }

                composable(
                    route = Screen.WorkoutSetScreen.route
                ) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("current_workout/{sessionId}")
                    }

                    val sessionId = parentEntry.arguments?.getLong("sessionId")
                        ?: return@composable
                    val viewModel: WorkoutSessionViewModel = viewModel(
                        parentEntry,
                        factory = WorkoutSessionViewModelFactory(sessionId)
                    )
                    WorkoutSetScreen(
                        viewModel = viewModel,
                        navigateToPreview = {
                            navController.navigate(Screen.WorkoutPreviewScreen.route)
                        },
//                        navigateToFailure = {
//                            navController.navigate(Screen.WorkoutFailureScreen.route)
//                        },
                        navigateToSummary = {
                            navController.navigate(Screen.WorkoutSummaryScreen.route)
                        }
                    )
                }
                composable(
                    route = Screen.WorkoutFailureScreen.route,
                ) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("current_workout/{sessionId}")
                    }

                    val sessionId = parentEntry.arguments?.getLong("sessionId")
                        ?: return@composable
                    val viewModel: WorkoutSessionViewModel = viewModel(
                        parentEntry,
                        factory = WorkoutSessionViewModelFactory(sessionId)
                    )

                    WorkoutFailureScreen(
                        viewModel = viewModel,
                        navigateToPreview = {
                            navController.navigate(Screen.WorkoutPreviewScreen.route)
                        },
                        navigateToSet = {
                            navController.navigate(Screen.WorkoutFailureScreen.route)
                        },
                        navigateToSummary = {
                            navController.navigate(Screen.WorkoutSummaryScreen.route)
                        }
                    )
                }

            }

            navigation(
                route = "add_workout_graph",
                startDestination = Screen.AddWorkoutPlanScreen.route
            ) {

                composable(route = Screen.AddWorkoutPlanScreen.route) { backStackEntry ->

                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("add_workout_graph")
                    }

                    val viewModel: AddWorkoutPlanViewModel = viewModel(parentEntry)

                    AddWorkoutPlanScreen(
                        viewModel = viewModel,
                        navigateToHome = {
                            navController.popBackStack()
                        },
                        navigateToSelectExercise = { type ->
                            navController.navigate(
                                Screen.SelectExerciseScreen.createRoute(type)
                            )
                        }
                    )
                }

                composable(
                    route = Screen.SelectExerciseScreen.route,
                    arguments = listOf(
                        navArgument("exerciseType") { type = NavType.StringType }
                    )
                ) { backStackEntry ->

                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("add_workout_graph")
                    }

                    val viewModel: AddWorkoutPlanViewModel = viewModel(parentEntry)

                    val typeString = backStackEntry.arguments?.getString("exerciseType")
                    val workoutExerciseType = WorkoutExerciseType.valueOf(typeString!!)

                    SelectExerciseScreen(
                        viewModel = viewModel,
                        workoutExerciseType = workoutExerciseType,
                        navigateToAddWorkoutPlan = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }



        composable(
            route = Screen.WorkoutSummaryScreen.route, arguments = listOf(
                navArgument("sessionId") { type = NavType.LongType },
            )
        ) { backStackEntry ->
            val sessionId = backStackEntry.arguments?.getLong("sessionId") ?: return@composable
            WorkoutSummaryScreen(sessionId = sessionId, navController = navController)
        }

    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}