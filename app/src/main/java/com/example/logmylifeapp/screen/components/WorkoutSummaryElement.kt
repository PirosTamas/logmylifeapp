package com.example.logmylifeapp.screen.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.R
import com.example.logmylifeapp.dto.WorkoutSummaryDTO
import com.example.logmylifeapp.dto.WorkoutSummaryExerciseDTO
import com.example.logmylifeapp.dto.WorkoutSummarySetDTO

import com.example.logmylifeapp.model.WorkoutPlan

import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun WorkoutSummaryElement(
    summary: WorkoutSummaryExerciseDTO,
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier.fillMaxWidth()
        .shadow(
            elevation = 4.dp,
            shape = RoundedCornerShape(12.dp),
            clip = false
        )
        .clip(RoundedCornerShape(12.dp))
        .background(Color(0xFFE9E9E9))
        .padding(16.dp)
        , horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = summary.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)){
            Image(
                painter = painterResource(summary.illustrationResId),
                contentDescription = summary.name,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Column(
                modifier = Modifier
                    .weight(2f)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Set", fontWeight = FontWeight.Bold)
                    Text("Weight", fontWeight = FontWeight.Bold)
                    Text("Reps", fontWeight = FontWeight.Bold)
                }
                summary.sets.forEach { set ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${set.order + 1}.")
                        Text("${set.weight ?: "-"}")
                        Text("${set.completedReps}")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutSummaryElementPreview() {

    val summary = WorkoutSummaryExerciseDTO(
         "Squats", R.drawable.squat,
        sets= listOf(WorkoutSummarySetDTO(0, 10, 10, 40f),
            WorkoutSummarySetDTO(1, 10, 10, 50f),
            WorkoutSummarySetDTO(2, 10, 8, 60f),)
    )

    WorkoutSummaryElement(summary = summary)
}