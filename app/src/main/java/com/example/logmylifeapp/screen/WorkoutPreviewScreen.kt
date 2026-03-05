package com.example.logmylifeapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.screen.components.WorkoutPlanElement
import com.example.logmylifeapp.viewmodel.DailyLifeDataViewModel
import com.example.logmylifeapp.viewmodel.WorkoutHomeViewModel
import com.example.logmylifeapp.viewmodel.WorkoutPreviewViewModel
import com.example.logmylifeapp.viewmodel.WorkoutPreviewViewModelFactory
import com.example.logmylifeapp.viewmodel.WorkoutSessionViewModel
import kotlinx.coroutines.launch

@Composable
fun WorkoutPreviewScreen(
    viewModel: WorkoutSessionViewModel,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigateToSet: () -> Unit,
) {
    val currentExerciseNullable by
    viewModel.workoutExercise.collectAsState(initial = null)
    val colors = LocalAppColors.current
    val coroutineScope = rememberCoroutineScope()

    val workoutExercise = currentExerciseNullable

    if (workoutExercise == null) {
        Text("valami szar", fontSize = 32.sp)
        return
    } else {

        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = colors.surface,
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp, end = 24.dp, bottom = 16.dp, start = 24.dp)
                )
                {
                    IconButton(
                        onClick = { navigateToHome() },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .size(40.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colors.onBackground,
                            containerColor = colors.surfaceVariant
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Exercise detail".uppercase(),
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.1.sp,
                        color = colors.onSurfaceVariant
                    )


                }
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 24.dp)
                        .fillMaxSize()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
                            Image(
                                painter = painterResource(
                                    id = workoutExercise.illustrationResId ?: R.drawable.benchpress
                                ),
                                contentDescription = workoutExercise.name,
                                modifier = Modifier
                                    .width(205.dp)
                                    .aspectRatio(1f)
                                    .align(Alignment.Center)
                                    .shadow(2.dp),
                                contentScale = ContentScale.Fit
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = workoutExercise.name,
                                fontSize = 37.5.sp,
                                lineHeight = 30.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = colors.onBackground
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            color = colors.surfaceVariant,
                                            shape = RoundedCornerShape(32.dp)
                                        )
                                        .border(
                                            color = colors.surfaceVariant,
                                            shape = RoundedCornerShape(32.dp),
                                            width = 1.dp
                                        )
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(shape = CircleShape)
                                            .background(color = colors.surface)
                                    )
                                    {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_fitness_center_24),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .align(Alignment.Center),
                                            tint = colors.primary
                                        )

                                    }
                                    Column(
                                        modifier = Modifier,
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = "Equipment".uppercase(),
                                            fontSize = 9.sp,
                                            lineHeight = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onSurfaceVariant
                                        )
                                        Text(
                                            text = workoutExercise.equipmentNeeded.joinToString(", "),
                                            fontSize = 14.sp,
                                            lineHeight = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onBackground
                                        )
                                    }
                                }
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            color = colors.surfaceVariant,
                                            shape = RoundedCornerShape(32.dp)
                                        )
                                        .border(
                                            color = colors.surfaceVariant,
                                            shape = RoundedCornerShape(32.dp),
                                            width = 1.dp
                                        )
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(shape = CircleShape)
                                            .background(color = colors.surface)
                                    )
                                    {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_fitness_center_24),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .align(Alignment.Center),
                                            tint = colors.primary
                                        )

                                    }
                                    Column(
                                        modifier = Modifier,
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = "Time".uppercase(),
                                            fontSize = 9.sp,
                                            lineHeight = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onSurfaceVariant
                                        )
                                        Text(
                                            text = "${workoutExercise.predictedTimeInMinutes} minutes",
                                            fontSize = 14.sp,
                                            lineHeight = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = colors.onBackground
                                        )
                                    }
                                }

                            }
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "Description".uppercase(),
                                    fontSize = 10.sp,
                                    lineHeight = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 2.sp,
                                    color = colors.onSurfaceVariant
                                )
                                Text(
                                    text = workoutExercise.description,
                                    fontSize = 14.sp,
                                    lineHeight = 22.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = colors.onSurfaceVariant
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
                                    navigateToSet()
                                }
                            }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                )
                                Text(
                                    text = "Start workout".uppercase(),
                                    fontSize = 18.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.Bold,
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
//fun WorkoutPreviewScreenPreview() {
//    WorkoutPreviewScreen(modifier = Modifier, {}, {})
//}