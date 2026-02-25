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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.R
import com.example.logmylifeapp.enums.WorkoutExerciseType
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.screen.components.AddWorkoutPlanExercise
import com.example.logmylifeapp.screen.components.FormControl
import com.example.logmylifeapp.screen.components.InputField
import com.example.logmylifeapp.viewmodel.AddWorkoutPlanViewModel
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun AddWorkoutPlanScreen(viewModel: AddWorkoutPlanViewModel, navigateToHome: () -> Unit, navigateToSelectExercise: (WorkoutExerciseType) -> Unit) {
    val context = LocalContext.current
    val warmups by viewModel.warmups.collectAsState()
//    val warmups = listOf(
//        WorkoutExercise(
//            name = "Squats",
//            description = "Leg exercise",
//            equipmentNeeded = setOf("None"),
//            predictedTimeInMinutes = 10,
//            illustrationResId = R.drawable.warmup2,
//            illustrationUri = null,
//            numberOfSets = 3,
//            restTimeBetweenSets = 30
//        ),
//        WorkoutExercise(
//            name = "Push-ups",
//            description = "Upper body exercise",
//            equipmentNeeded = setOf("None"),
//            predictedTimeInMinutes = 5,
//            illustrationResId = R.drawable.warmup1,
//            illustrationUri = null,
//            numberOfSets = 3,
//            restTimeBetweenSets = 30
//        )
//    )

    val fields = listOf(
        InputField.TextField(label = "Name", placeholder = "e.g. Hypertrophy Split"),
        InputField.NumberField(label = "Sessions", placeholder = "3"),
        InputField.DateField(label = "Start Date"),
        InputField.MultiSelectDays(label = "Scheduled Days"),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorResource(R.color.green_900),
        topBar = {
            val borderColor = colorResource(R.color.green_200).copy(alpha = 0.1f)
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
                    onClick = { /* Handle back */ },
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
                    color = colorResource(R.color.off_white)
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
                ) {

                    FormControl(
                        inputField = fields[0],
                        modifier = Modifier.fillMaxWidth()
                    )


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {


                        FormControl(
                            inputField = fields[1],
                            modifier = Modifier.weight(1f)
                        )


                        FormControl(
                            inputField = fields[2],
                            modifier = Modifier.weight(1f)
                        )
                    }


                    FormControl(
                        inputField = fields[3],
                        modifier = Modifier.fillMaxWidth()
                    )
                    AddWorkoutPlanExercise(exercises = warmups, addMoreClick = {
                        navigateToSelectExercise(WorkoutExerciseType.WARMUP)
                    }, emptyListMessage = "No workout added yet")
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
                            containerColor = colorResource(R.color.green_200),
                            contentColor = colorResource(R.color.green_900)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            val name = (fields[0] as InputField.TextField).value
                            val scheduledDays =
                                (fields[1] as InputField.MultiSelectDays).selectedDays
                            val numberOfSessions = (fields[2] as InputField.NumberField).value
                            val startDate =
                                (fields[3] as InputField.DateField).date ?: LocalDate.now()


//            if (name.isNotBlank() && numberOfSessions != null) {
//                val newWorkoutPlan = WorkoutPlan(
//                    id = 0,
//                    name = name,
//                    numberOfSessions = numberOfSessions,
//                    scheduledDays = scheduledDays,
//                    startDate = startDate,
//
//                )
//
//                viewModel.addWorkoutPlan(newWorkoutPlan)
//
//                navigateToHome()
//            } else {
//                Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
//            }

                            navigateToHome()
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


