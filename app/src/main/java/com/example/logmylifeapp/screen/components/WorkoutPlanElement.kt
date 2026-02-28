package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.R
import com.example.logmylifeapp.model.WorkoutPlan
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun WorkoutPlanElement(
    workoutPlan: WorkoutPlan,
    modifier: Modifier = Modifier,
    onStartClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .height(98.dp)
            .border(
                1.dp, color = colorResource(R.color.off_white),
                shape = RoundedCornerShape(16.dp)
            )
            .background(colorResource(R.color.white), RoundedCornerShape(24.dp))
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.squat),
            contentDescription = workoutPlan.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(colorResource(R.color.grey_100))
        )
        Column(modifier = modifier.weight(1f)) {
            val progress =
                workoutPlan.currentSession.toFloat() / workoutPlan.numberOfSessions.toFloat()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    workoutPlan.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp,
                    color = colorResource(R.color.blue_900)
                )
                Text(
                    "${(progress * 100).toInt()}%",
                    fontSize = 10.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.green_200)
                )
            }

            LinearProgressIndicator(
                progress = { progress },
                color = colorResource(R.color.green_200),
                trackColor = colorResource(R.color.off_white),
                gapSize = 0.dp,
                drawStopIndicator = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
            )
        }
        Button(
            onClick = {
                onStartClick(workoutPlan.id)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.off_white),
                contentColor = colorResource(R.color.grey_300)
            ),
            modifier = modifier.size(40.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
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
        startDate = LocalDate.now().minusWeeks(2)
    )

    WorkoutPlanElement(workoutPlan = dummyWorkoutPlan, onStartClick = {})
}