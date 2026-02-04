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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.R
import com.example.logmylifeapp.Screen
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.screen.components.WorkoutPlanElement
import com.example.logmylifeapp.viewmodel.DailyLifeDataViewModel
import com.example.logmylifeapp.viewmodel.WorkoutHomeViewModel
import com.example.logmylifeapp.viewmodel.WorkoutPreviewViewModel
import com.example.logmylifeapp.viewmodel.WorkoutPreviewViewModelFactory

@Composable
fun WorkoutPreviewScreen(modifier: Modifier = Modifier, planId: Int) {
//    val viewModel: WorkoutPreviewViewModel = viewModel(factory = WorkoutPreviewViewModelFactory(planId))
//    val planWithExercises by
//    viewModel.planWithExercises.collectAsState(initial = null)

    //val firstExercise = planWithExercises?.exercises?.minByOrNull { it.id }
    val firstExercise = WorkoutExercise(
        name = "Bench press",
        description = "The bench press is a foundational compound strength exercise performed by lying supine on a bench and pressing a barbell or dumbbells upward from the chest. It primarily targets the pectoralis major, anterior deltoids, and triceps, while utilizing the back, core, and legs for stabilization. ",
        equipmentNeeded = setOf("Bench", "Barbell", "Plates"),
        predictedTimeInMinutes = 5,
        illustrationResId = R.drawable.benchpress,
        illustrationUri = "",
        numberOfSets = 3
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 44.dp, start = 12.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            firstExercise?.let { exercise ->
                Text(exercise.name, fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
                Text(exercise.description)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    exercise.illustrationResId?.let { resId ->
                        Image(
                            painter = painterResource(resId),
                            contentDescription = exercise.name,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Number of sets: " + exercise.numberOfSets)
                    Text(": " + exercise.numberOfSets)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_exercise_24),
                        contentDescription = ""
                    )
                    Text(": " + exercise.equipmentNeeded.joinToString(", "))
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_access_time_24),
                        contentDescription = ""
                    )
                    Text(": " + exercise.predictedTimeInMinutes + " min")
                }

            }

        }
        Button({} ) {
            Text("Start")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutPreviewScreenPreview() {
    WorkoutPreviewScreen(modifier = Modifier, 1)
}