package com.example.logmylifeapp.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.logmylifeapp.Screen
import com.example.logmylifeapp.screen.components.WorkoutPlanElement
import com.example.logmylifeapp.viewmodel.DailyLifeDataViewModel
import com.example.logmylifeapp.viewmodel.WorkoutHomeViewModel
import kotlinx.coroutines.launch

@Composable
fun WorkoutHomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val viewModel: WorkoutHomeViewModel = viewModel()
    val workoutPlans = viewModel.workoutPlansForToday.collectAsState(initial = listOf())
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 22.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        workoutPlans.value.forEach { workoutPlan ->
            WorkoutPlanElement(workoutPlan = workoutPlan, onStartClick = {
                planId ->
                coroutineScope.launch {
                    val sessionId = viewModel.addWorkoutSession(planId)
                    navController.navigate(
                        Screen.WorkoutPreviewScreen.createRoute(sessionId, 0)
                    )
                }

            })
        }
    }
}