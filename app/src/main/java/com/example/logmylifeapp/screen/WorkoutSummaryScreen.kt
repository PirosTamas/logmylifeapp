package com.example.logmylifeapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import com.example.logmylifeapp.R
import com.example.logmylifeapp.Screen
import com.example.logmylifeapp.dto.WorkoutSummaryDTO
import com.example.logmylifeapp.dto.WorkoutSummaryExerciseDTO
import com.example.logmylifeapp.dto.WorkoutSummarySetDTO
import com.example.logmylifeapp.screen.components.WorkoutSummaryElement
import com.example.logmylifeapp.viewmodel.WorkoutSummaryViewModel
import com.example.logmylifeapp.viewmodel.WorkoutSummaryViewModelFactory

@Composable
fun WorkoutSummaryScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    sessionId: Long,
) {
    val viewModel: WorkoutSummaryViewModel =
        viewModel(
            factory = WorkoutSummaryViewModelFactory(
                sessionId = sessionId,
            )
        )

    val workoutSummary by
    viewModel.workoutSummary.collectAsState(initial = null)
//    val summary = WorkoutSummaryDTO(
//        planName = "Mix fit",
//        listOf(
//            WorkoutSummaryExerciseDTO(
//                "Squats", R.drawable.squat,
//                sets = listOf(
//                    WorkoutSummarySetDTO(0, 10, 10, 40f),
//                    WorkoutSummarySetDTO(1, 10, 10, 50f),
//                    WorkoutSummarySetDTO(2, 10, 8, 60f),
//                )
//            ),
//            WorkoutSummaryExerciseDTO(
//                "Bench press", R.drawable.benchpress,
//                sets = listOf(
//                    WorkoutSummarySetDTO(0, 10, 10, 40f),
//                    WorkoutSummarySetDTO(1, 10, 10, 50f),
//                    WorkoutSummarySetDTO(2, 10, 8, 60f),
//                )
//            ),
//            WorkoutSummaryExerciseDTO(
//                "Bench press", R.drawable.benchpress,
//                sets = listOf(
//                    WorkoutSummarySetDTO(0, 10, 10, 40f),
//                    WorkoutSummarySetDTO(1, 10, 10, 50f),
//                    WorkoutSummarySetDTO(2, 10, 8, 60f),
//                )
//            ),
//            WorkoutSummaryExerciseDTO(
//                "Bench press", R.drawable.benchpress,
//                sets = listOf(
//                    WorkoutSummarySetDTO(0, 10, 10, 40f),
//                    WorkoutSummarySetDTO(1, 10, 10, 50f),
//                    WorkoutSummarySetDTO(2, 10, 8, 60f),
//                )
//            ),
//            WorkoutSummaryExerciseDTO(
//                "Bench press", R.drawable.benchpress,
//                sets = listOf(
//                    WorkoutSummarySetDTO(0, 10, 10, 40f),
//                    WorkoutSummarySetDTO(1, 10, 10, 50f),
//                    WorkoutSummarySetDTO(2, 10, 8, 60f),
//                )
//            )
//
//        )
//    )


    val coroutineScope = rememberCoroutineScope()

    if (workoutSummary == null) {
        Text(
            text = "Loading summary...",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        val summary = workoutSummary!!
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 12.dp, top = 32.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Congratulations",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = summary.planName + " finished!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )

                summary.exercises.forEach {
                    WorkoutSummaryElement(it)
                }
            }
            Button(onClick = {
                navController.navigate(Screen.HomeScreen.route)
            }
            ) {
                Text("Back to Home")
            }


        }
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun WorkoutSummaryScreenPreview() {
//    WorkoutSummaryScreen(modifier = Modifier, /*{} as NavHostController,*/ 1)
//}