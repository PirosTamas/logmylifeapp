package com.example.logmylifeapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.logmylifeapp.R
import com.example.logmylifeapp.Screen
import com.example.logmylifeapp.dto.CurrentWorkoutExerciseDTO
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.screen.components.InputField
import com.example.logmylifeapp.screen.components.NumberPicker
import com.example.logmylifeapp.viewmodel.WorkoutSessionViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun WorkoutSetScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutSessionViewModel,
    navigateToPreview: () -> Unit,
    navigateToSummary: () -> Unit
) {

    val currentWorkoutNullable by
    viewModel.currentWorkoutSet.collectAsState(initial = null)

    val setIndexNullable by viewModel.setIndex.collectAsState(initial = null)
    val exerciseIndexNullable by viewModel.exerciseIndex.collectAsState(initial = null)
    val exerciseNullable by viewModel.workoutExercise.collectAsState(initial = null)


    val coroutineScope = rememberCoroutineScope()

    val workout = currentWorkoutNullable
    val setIndex = setIndexNullable
    val exerciseIndex = exerciseIndexNullable
    val exercise = exerciseNullable

    if (setIndex == null || exerciseIndex == null || workout == null || exercise == null) {
        Text("valami szar", fontSize = 32.sp)
        return
    } else {
////        Text(text = workout.numberOfSets.toString())
////        Column(modifier = Modifier.padding(top = 55.dp)) {
////            Text(fontSize = 33.sp, text = "SessionId: ${viewModel.sessionId}")
////            Text(fontSize = 33.sp, text = "SetIndex: $setIndex")
////            Text(fontSize = 33.sp, text = "ExerciseIndex: $exerciseIndex")
////            Text(fontSize = 33.sp, text = "Workout: $exerciseIndex")
////        }

        var completedWeight by remember(workout) {
            mutableIntStateOf(workout.weight.toInt())
        }

        var completedReps by remember(workout) {
            mutableIntStateOf(workout.targetReps)
        }
//        val setIndex = 0
//        val workout =
//            CurrentWorkoutExerciseDTO("Barbell squats", 10, 10, 50f, 0, 3, 30, LocalDate.now())
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = colorResource(R.color.green_900),
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, end = 24.dp, bottom = 16.dp, start = 24.dp)
                )
                {
                    IconButton(
                        onClick = { navigateToPreview() },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(40.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorResource(R.color.green_200),
                            containerColor = colorResource(R.color.green_200).copy(alpha = 0.1f)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Select " + workout.exerciseName,
                            fontSize = 18.sp,
                            lineHeight = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.off_white)
                        )
                        Text(
                            text = "Legs day".uppercase(),
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.1.sp,
                            color = colorResource(R.color.grey_300)
                        )
                    }

                }
            },
            content = { innerPadding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp), verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Current Progress",
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(R.color.grey_300)
                            )
                            Row() {
                                Text(
                                    text = "Set ",
                                    fontSize = 48.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colorResource(R.color.off_white)
                                )
                                Text(
                                    text = "${setIndex + 1}",
                                    fontSize = 48.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colorResource(R.color.green_200)
                                )
                                Text(
                                    text = " of ${workout.numberOfSets} ",
                                    fontSize = 48.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colorResource(R.color.off_white)
                                )

                            }
                            Row(
                                modifier = Modifier.padding(top = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(workout.numberOfSets) { index ->
                                    Box(
                                        modifier = Modifier
                                            .width(32.dp)
                                            .height(6.dp)
                                            .clip(RoundedCornerShape(50))
                                            .background(
                                                if (index <= setIndex)
                                                    colorResource(R.color.green_200)
                                                else
                                                    colorResource(R.color.blue_800)
                                            )
                                    )
                                }
                            }


                        }
                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .width(164.dp)
                                    .height(94.dp)
                                    .background(
                                        color = colorResource(R.color.green_200)
                                            .copy(alpha = 0.05f),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .clip(RoundedCornerShape(24.dp))
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(R.color.green_200).copy(0.2f),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .shadow(elevation = 1.dp)
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Recommended".uppercase(),
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(R.color.green_200).copy(alpha = 0.7f)
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${workout.weight}",
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(R.color.off_white)
                                    )
                                    Text(
                                        text = " kg",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(R.color.grey_300)
                                    )
                                    Text(
                                        text = " x ",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(R.color.off_white)
                                    )
                                    Text(
                                        text = "${workout.targetReps} ",
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(R.color.off_white)
                                    )

                                }
                            }
                            Column(
                                modifier = Modifier
                                    .width(164.dp)
                                    .height(94.dp)
                                    .background(
                                        color = colorResource(R.color.blue_900).copy(0.5f),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .clip(RoundedCornerShape(24.dp))
                                    .border(
                                        width = 1.dp,
                                        color = colorResource(R.color.blue_800),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .shadow(elevation = 1.dp)
                                    .padding(20.dp),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Last Week".uppercase(),
                                    fontSize = 12.sp,
                                    lineHeight = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = colorResource(R.color.grey_300)
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${workout.weight}",
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(R.color.off_white)
                                    )
                                    Text(
                                        text = " kg",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(R.color.grey_300)
                                    )
                                    Text(
                                        text = " x ",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colorResource(R.color.off_white)
                                    )
                                    Text(
                                        text = "${workout.targetReps} ",
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colorResource(R.color.off_white)
                                    )

                                }
                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Actual Weight (kg) ",
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.off_white)
                                )
                                NumberPicker(
                                    value = completedWeight,
                                    minValue = 0,
                                    onValueChange = { completedWeight = it }
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .weight(1f),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Total reps",
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(R.color.off_white)
                                )
                                NumberPicker(
                                    value = completedReps,
                                    minValue = 0,
                                    onValueChange = { completedReps = it },

                                )
                            }

                        }

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.green_200),
                                contentColor = colorResource(R.color.green_900)
                            ),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                coroutineScope.launch {

                                    viewModel.onSuccessClicked(
                                        workout =
                                            WorkoutExerciseSetLog(
                                                exerciseLogId = -1,
                                                order = setIndex,
                                                targetReps = workout.targetReps,
                                                completedReps = completedReps,
                                                weight = completedWeight.toFloat(),
                                                success = true,
                                                description = "SUCCESS"
                                            ),
                                        navigateToPreview = navigateToPreview,
                                        navigateToSummary = navigateToSummary
                                    )
                                }
                            }) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Complete set".uppercase(),
                                    fontSize = 18.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                            }

                        }
                    }
                }
            }
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun WorkoutSetScreenPreview() {
//    WorkoutSetScreen(modifier = Modifier, {}, {})
//}