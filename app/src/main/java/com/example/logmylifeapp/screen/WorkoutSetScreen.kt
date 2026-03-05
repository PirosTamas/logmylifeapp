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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.logmylifeapp.R
import com.example.logmylifeapp.Screen
import com.example.logmylifeapp.ui.theme.LocalAppColors
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


    val planName by viewModel.planName.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val workout = currentWorkoutNullable
    val setIndex = setIndexNullable
    val exerciseIndex = exerciseIndexNullable
    val exercise = exerciseNullable

    val colors = LocalAppColors.current

    if (setIndex == null || exerciseIndex == null || workout == null || exercise == null) {
        // Loading state — data not yet available from the database
        return
    } else {

        var completedWeight by remember(workout) {
            mutableIntStateOf(workout.weight.toInt())
        }

        var completedReps by remember(workout) {
            mutableIntStateOf(workout.targetReps)
        }
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = colors.background,
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
                            contentColor = colors.primary,
                            containerColor = colors.primary.copy(alpha = 0.1f)
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
                            text = workout.exerciseName,
                            fontSize = 18.sp,
                            lineHeight = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.onBackground
                        )
                        Text(
                            text = planName.uppercase(),
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.1.sp,
                            color = colors.onSurfaceVariant
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
                                color = colors.onSurfaceVariant
                            )
                            Row() {
                                Text(
                                    text = "Set ",
                                    fontSize = 48.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colors.onBackground
                                )
                                Text(
                                    text = "${setIndex + 1}",
                                    fontSize = 48.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colors.primary
                                )
                                Text(
                                    text = " of ${workout.numberOfSets} ",
                                    fontSize = 48.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = colors.onBackground
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
                                                    colors.primary
                                                else
                                                    colors.surfaceVariant
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
                                        color = colors.primary
                                            .copy(alpha = 0.05f),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .clip(RoundedCornerShape(24.dp))
                                    .border(
                                        width = 1.dp,
                                        color = colors.primary.copy(0.2f),
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
                                    color = colors.primary.copy(alpha = 0.7f)
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${workout.weight}",
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onBackground
                                    )
                                    Text(
                                        text = " kg",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colors.onSurfaceVariant
                                    )
                                    Text(
                                        text = " x ",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colors.onBackground
                                    )
                                    Text(
                                        text = "${workout.targetReps} ",
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onBackground
                                    )

                                }
                            }
                            Column(
                                modifier = Modifier
                                    .width(164.dp)
                                    .height(94.dp)
                                    .background(
                                        color = colors.surface.copy(0.5f),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .clip(RoundedCornerShape(24.dp))
                                    .border(
                                        width = 1.dp,
                                        color = colors.surfaceVariant,
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
                                    color = colors.onSurfaceVariant
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${workout.weight}",
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onBackground
                                    )
                                    Text(
                                        text = " kg",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colors.onSurfaceVariant
                                    )
                                    Text(
                                        text = " x ",
                                        fontSize = 14.sp,
                                        lineHeight = 20.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = colors.onBackground
                                    )
                                    Text(
                                        text = "${workout.targetReps} ",
                                        fontSize = 20.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onBackground
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
                                    color = colors.onBackground
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
                                    color = colors.onBackground
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
                                containerColor = colors.primary,
                                contentColor = colors.onPrimary
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