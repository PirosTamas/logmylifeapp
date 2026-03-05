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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavHostController
import com.example.logmylifeapp.R
import com.example.logmylifeapp.Screen
import com.example.logmylifeapp.ui.theme.LocalAppColors
import com.example.logmylifeapp.dto.WorkoutSummaryDTO
import com.example.logmylifeapp.dto.WorkoutSummaryExerciseDTO
import com.example.logmylifeapp.dto.WorkoutSummarySetDTO
import com.example.logmylifeapp.screen.components.WorkoutSummaryElement
import com.example.logmylifeapp.viewmodel.WorkoutSummaryViewModel
import com.example.logmylifeapp.viewmodel.WorkoutSummaryViewModelFactory
import kotlinx.coroutines.launch

@Composable
fun WorkoutSummaryScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    sessionId: Long,
) {
    val viewModel: WorkoutSummaryViewModel =
        viewModel(
            factory = WorkoutSummaryViewModelFactory(
                sessionId = sessionId,
            )
        )

    val workoutSummaryNullable by
    viewModel.workoutSummary.collectAsState(initial = null)
//    val workoutSummary = WorkoutSummaryDTO(
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


    val colors = LocalAppColors.current
    val coroutineScope = rememberCoroutineScope()

    val workoutSummary = workoutSummaryNullable
    if (workoutSummary == null) {
        Text(
            text = "Loading summary...",
            modifier = Modifier.padding(16.dp)
        )
    } else {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = colors.background,
            topBar = {
                val borderColor = colors.primary.copy(alpha = 0.1f)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(74.dp)
                        .drawBehind {
                            drawLine(
                                color = borderColor,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = 1.dp.toPx()
                            )
                        }
                )
                {
                    IconButton(
                        onClick = {
                            navigateToHome()
                        },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close, // X icon
                            contentDescription = "Close",
                            tint = Color(0xFF94A3BB)
                        )
                    }
                    Text(
                        text = "Workout Summary",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 18.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.onBackground
                    )
                }
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(75.dp)
                                    .clip(shape = CircleShape)
                                    .background(color = colors.primary.copy(0.2f))
                            )
                            {
                                Icon(
                                    painter = painterResource(R.drawable.outline_celebration_24),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(41.dp)
                                        .align(Alignment.Center),
                                    tint = colors.primary
                                )

                            }
                            Text(
                                text = "Congratulations!".uppercase(),
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.4.sp,
                                color = colors.primary
                            )
                            Text(
                                text = "Workout finished!",
                                fontSize = 32.sp,
                                lineHeight = 45.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = colors.onBackground
                            )
                            Text(
                                text = "You crushed your goals today. 45 minutes of pure intensity!",
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.Light,
                                color = colors.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                            Row(
                                modifier = Modifier.padding(top = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .width(102.dp)
                                        .height(70.dp)
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
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Time".uppercase(),
                                        fontSize = 10.sp,
                                        lineHeight = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onSurfaceVariant
                                    )

                                    Text(
                                        text = "48:12",
                                        fontSize = 18.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onBackground
                                    )


                                }
                                Column(
                                    modifier = Modifier
                                        .width(102.dp)
                                        .height(70.dp)
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
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Calories".uppercase(),
                                        fontSize = 10.sp,
                                        lineHeight = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onSurfaceVariant
                                    )

                                    Text(
                                        text = "342",
                                        fontSize = 18.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onBackground
                                    )


                                }
                                Column(
                                    modifier = Modifier
                                        .width(102.dp)
                                        .height(70.dp)
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
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Volume".uppercase(),
                                        fontSize = 10.sp,
                                        lineHeight = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onSurfaceVariant
                                    )

                                    Text(
                                        text = "4.2k kg",
                                        fontSize = 18.sp,
                                        lineHeight = 28.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = colors.onBackground
                                    )


                                }
                            }

                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            Text(
                                text = "Exercise Breakdown",
                                fontSize = 20.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = colors.onBackground
                            )
                            workoutSummary.exercises.forEach {
                                WorkoutSummaryElement(it)
                            }

                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 24.dp),
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.primary,
                                contentColor = colors.background
                            ),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                coroutineScope.launch {
                                    navigateToHome()
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
                                    text = "Back to home",
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


@Preview(showBackground = true)
@Composable
fun WorkoutSummaryScreenPreview() {
    WorkoutSummaryScreen(modifier = Modifier, {}, 3)
}