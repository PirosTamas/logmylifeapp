package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.model.WorkoutPlan
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun WorkoutPlanElement(
    workoutPlan: WorkoutPlan,
    modifier: Modifier = Modifier,
    onStartClick: (Int) -> Unit,
) {
    Row(modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(24.dp))
        .background(Color(0xFFCCDBDA), RoundedCornerShape(24.dp))
        .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        Column(modifier = modifier.weight(1f)) {
            val progress = workoutPlan.currentSession.toFloat() / workoutPlan.numberOfSessions.toFloat()
            Text(workoutPlan.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
        Button(onClick = {
            onStartClick(workoutPlan.id)
        }, modifier.size(36.dp), contentPadding = PaddingValues(0.dp)) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutPlanPreview() {
    val dummyWorkoutPlan = WorkoutPlan(
        id = 1,
        name = "Road to 70kg",
        scheduledDays = setOf(
            DayOfWeek.MONDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.FRIDAY
        ),
        currentSession = 6,
        numberOfSessions = 10,
        startDate = LocalDate.now().minusWeeks(2),
        warmUpExercises = setOf(
            "Jumping Jacks",
            "High Knees"
        ),
        workoutExercises = setOf(
            "Squats",
            "Push-ups",
            "Deadlifts"
        ),
        stretchingExercises = setOf(
            "Hamstring Stretch",
            "Quad Stretch"
        )
    )

    WorkoutPlanElement(workoutPlan = dummyWorkoutPlan, onStartClick = {})
}