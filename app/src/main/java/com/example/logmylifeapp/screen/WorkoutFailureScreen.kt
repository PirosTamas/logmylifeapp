package com.example.logmylifeapp.screen

import android.renderscript.ScriptGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.logmylifeapp.Screen
import com.example.logmylifeapp.model.AchievementCategory
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.screen.components.FormControl
import com.example.logmylifeapp.screen.components.InputField
import com.example.logmylifeapp.viewmodel.WorkoutSessionViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun WorkoutFailureScreen(
    modifier: Modifier = Modifier,
    viewModel: WorkoutSessionViewModel,
    navigateToPreview: () -> Unit,
    navigateToSet: () -> Unit,
    navigateToSummary: () -> Unit
) {


    val currentWorkoutNullable by
    viewModel.currentWorkoutSet.collectAsState(initial = null)

    val setIndexNullable by viewModel.setIndex.collectAsState(initial = null)
    val exerciseIndexNullable by viewModel.setIndex.collectAsState(initial = null)
    val exerciseNullable by viewModel.workoutExercise.collectAsState(initial = null)

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val fields = listOf(
        InputField.NumberField(label = "How many reps did you complete?"),
        InputField.NumberField(label = "What weight did you use?"),
        InputField.TextField(label = "What caused you to fail?")
    )


    if (currentWorkoutNullable == null && setIndexNullable == null) {
        Text(
            text = "Loading workout...",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        val workout = currentWorkoutNullable!!
        val setIndex = setIndexNullable!!
        val exerciseIndex = exerciseIndexNullable!!
        val exercise = exerciseNullable!!
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Failure log",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            fields.forEach { field ->
                FormControl(field)
            }
            Button(onClick = {
                coroutineScope.launch {
                    val numberOfReps = (fields[0] as InputField.NumberField).value
                    val weight = (fields[1] as InputField.NumberField).value
                    val description = (fields[2] as InputField.TextField).value

                    val exerciseLogId = viewModel.currentExerciseLogId
                    if (numberOfReps != null && weight != null && exerciseLogId != null) {
                        val workoutExerciseSetLog = WorkoutExerciseSetLog(
                            id = 0,
                            exerciseLogId = exerciseLogId.toInt(),
                            order = setIndex,
                            targetReps = 10,
                            completedReps = numberOfReps,
                            weight = weight.toFloat(),
                            success = false,
                            description = description
                        )

                        viewModel.addWorkoutExerciseSetLog(workoutExerciseSetLog)

                        if (setIndex < workout.numberOfSets - 1) {
                            navigateToSet()
                        } else {
                            navigateToPreview()
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "Please fill all required fields",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }


            }) { Text("Save") }
        }
    }
}


