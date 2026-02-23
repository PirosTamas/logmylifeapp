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
import com.example.logmylifeapp.Screen
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.viewmodel.WorkoutSetViewModel
import com.example.logmylifeapp.viewmodel.WorkoutSetViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun WorkoutSetScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    sessionId: Long,
    exerciseIndex: Int,
    exerciseLogId: Int,
    setIndex: Int
) {
    val viewModel: WorkoutSetViewModel =
        viewModel(
            factory = WorkoutSetViewModelFactory(
                sessionId = sessionId,
                exerciseLogId = exerciseLogId,
                setIndex = setIndex
            )
        )

    val currentWorkout by
    viewModel.currentWorkout.collectAsState(initial = null)

    val exerciseCount by viewModel.exerciseCount.collectAsState()



    val coroutineScope = rememberCoroutineScope()

    if (currentWorkout == null) {
        Text(
            text = "Loading workout...",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        val workout = currentWorkout!!

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 44.dp, start = 12.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                Text(
                    workout.exerciseName,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
//                exercise.illustrationResId?.let { resId ->
//                    Image(
//                        painter = painterResource(resId),
//                        contentDescription = exercise.name,
//                        modifier = Modifier.fillMaxWidth(0.8f)
//                    )
//                }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text("Round: " + (setIndex + 1))
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Recommended weight:")
                    Text(workout.weight.toString() + " x " + workout.targetReps)
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Last week's weight:")
                    Text(workout.weight.toString() + " x " + workout.completedReps)
                }

            }


            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Button(onClick = {
                    navController.navigate(
                        Screen.WorkoutFailureScreen.createRoute(
                            sessionId,
                            exerciseIndex,
                            exerciseLogId.toLong(),
                            setIndex
                        )
                    )
                }) {
                    Text("Failure")
                }
                Button({
                    coroutineScope.launch {
                        viewModel.addWorkoutExerciseSetLog(
                            WorkoutExerciseSetLog(
                                exerciseLogId = exerciseLogId,
                                order = setIndex,
                                targetReps = workout.targetReps,
                                completedReps = workout.targetReps,
                                weight = workout.weight,
                                success = true,
                                description = "SUCCESS"
                            )
                        )
                        if(setIndex < workout.numberOfSets - 1){
                        navController.navigate(
                            Screen.WorkoutSetScreen.createRoute(
                                sessionId,
                                exerciseIndex,
                                exerciseLogId.toLong(),
                                setIndex + 1
                            )
                        )}
                        else if(exerciseIndex >= exerciseCount - 1){
                            navController.navigate(Screen.WorkoutSummaryScreen.createRoute(sessionId))
                        }
                        else{
                            navController.navigate(
                                Screen.WorkoutPreviewScreen.createRoute(
                                    sessionId,
                                    exerciseIndex + 1
                                )
                            )
                        }
                    }
                }) {
                    Text("Success")
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun WorkoutSetScreenPreview() {
//    WorkoutSetScreen(modifier = Modifier, , 1)
}