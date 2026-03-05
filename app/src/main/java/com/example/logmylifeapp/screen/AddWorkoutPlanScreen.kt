package com.example.logmylifeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.enums.WorkoutExerciseType
import com.example.logmylifeapp.ui.theme.LocalAppColors
import com.example.logmylifeapp.screen.components.AddWorkoutPlanExercise
import com.example.logmylifeapp.screen.components.FormControl
import com.example.logmylifeapp.viewmodel.AddWorkoutPlanViewModel

@Composable
fun AddWorkoutPlanScreen(viewModel: AddWorkoutPlanViewModel, navigateToHome: () -> Unit, navigateToSelectExercise: (WorkoutExerciseType) -> Unit) {
    val colors = LocalAppColors.current
    val context = LocalContext.current
    val warmups by viewModel.warmups.collectAsState()
    val mainExercises by viewModel.mainExercises.collectAsState()
    val stretches by viewModel.stretches.collectAsState()

    // Fields come from the ViewModel so their values survive navigating to SelectExerciseScreen and back
    val fields = listOf(
        viewModel.planName,
        viewModel.planSessions,
        viewModel.planStartDate,
        viewModel.planScheduledDays,
    )

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
                    text = "Add workout plan",
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
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    FormControl(
                        inputField = fields[0],
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FormControl(inputField = fields[1], modifier = Modifier.weight(1f))
                        FormControl(inputField = fields[2], modifier = Modifier.weight(1f))
                    }

                    FormControl(
                        inputField = fields[3],
                        modifier = Modifier.fillMaxWidth()
                    )

                    AddWorkoutPlanExercise(
                        label = "Warmup",
                        exercises = warmups,
                        addMoreClick = { navigateToSelectExercise(WorkoutExerciseType.WARMUP) },
                        emptyListMessage = "No warmups added yet"
                    )

                    AddWorkoutPlanExercise(
                        label = "Exercises",
                        exercises = mainExercises,
                        addMoreClick = { navigateToSelectExercise(WorkoutExerciseType.MAIN) },
                        emptyListMessage = "No exercises added yet"
                    )

                    AddWorkoutPlanExercise(
                        label = "Stretches",
                        exercises = stretches,
                        addMoreClick = { navigateToSelectExercise(WorkoutExerciseType.STRETCH) },
                        emptyListMessage = "No stretches added yet"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
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
                            if (viewModel.planName.value.isNotBlank()) {
                                viewModel.addWorkoutPlanWithExercises()
                                navigateToHome()
                            } else {
                                Toast.makeText(context, "Please enter a plan name", Toast.LENGTH_SHORT).show()
                            }
                        }) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Create Workout Plan",
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

//@Preview(showBackground = true)
//@Composable
//fun AddWorkoutPlanPreview() {
//    AddWorkoutPlanScreen(navigateToHome = {}, navigateToSelectExercise = {})
//}


