package com.example.logmylifeapp.screen.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(24.dp))
            .background(
                color = colorResource(R.color.blue_900).copy(alpha = 0.4f),
                shape = RoundedCornerShape(24.dp),
            )
            .border(
                color = colorResource(R.color.blue_800),
                shape = RoundedCornerShape(24.dp),
                width = 1.dp
            )
            .padding(20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Image(
                modifier = Modifier
                    .width(56.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(2.dp),
                painter = painterResource(summary.illustrationResId),
                contentDescription = summary.name,
                contentScale = ContentScale.Fit
            )
            Column(modifier.weight(1f), verticalArrangement = Arrangement.Center){
                Text(
                    text = summary.name,
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.off_white)
                )
                Text(
                    text = "${summary.sets.size} sets",
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.grey_300)
                )
            }
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(shape = CircleShape)
                    .border(
                        color = colorResource(R.color.green_200).copy(0.2f),
                        shape = CircleShape,
                        width = 1.dp
                    )
            )
            {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center),
                    tint = colorResource(id = R.color.green_200)
                )

            }

        }
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Set".uppercase(),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.grey_700),
                    modifier = Modifier.width(70.dp)
                )
                Text(
                    text = "Weight".uppercase(),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.grey_700),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Reps".uppercase(),
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.grey_700)
                )
            }
            summary.sets.forEach { set ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            color = colorResource(R.color.blue_800).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Text(
                        text = "${set.order + 1}",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(R.color.green_200),
                        modifier = Modifier.width(70.dp)
                    )
                    Text(
                        text ="${set.weight ?: "-"}",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.off_white),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${set.completedReps}",
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(R.color.off_white)
                    )
                }
            }
        }

    }
}



@Preview(showBackground = true, backgroundColor = 0xFF102216)
@Composable
fun WorkoutSummaryElementPreview() {

    val summary = WorkoutSummaryExerciseDTO(
         "Squats", R.drawable.warmup1,
        sets= listOf(WorkoutSummarySetDTO(0, 10, 10, 40f),
            WorkoutSummarySetDTO(1, 10, 10, 50f),
            WorkoutSummarySetDTO(2, 10, 8, 60f),)
    )

    WorkoutSummaryElement(summary = summary)
}