package com.example.logmylifeapp.screen.components

import android.R.attr.id
import android.graphics.Color.green
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.R
import com.example.logmylifeapp.dto.WorkoutExerciseComponentDTO
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.viewmodel.AddWorkoutPlanViewModel

@Composable
fun AddWorkoutPlanExercise(addMoreClick: () -> Unit, exercises: List<WorkoutExercise>, emptyListMessage: String) {
//    val viewModel: AddWorkoutPlanViewModel = viewModel()
//    val warmups by viewModel.warmups.collectAsState()

    val green = colorResource(R.color.green_200)

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_fitness_center_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = colorResource(id = R.color.green_200)
                )
                Text(
                    text = "Warmup",
                    color = colorResource(id = R.color.off_white),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    lineHeight = 28.sp
                )
            }
            Button(
                onClick = {
                    addMoreClick()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green_700),
                    contentColor = colorResource(id = R.color.green_200)
                ),
                contentPadding = PaddingValues(
                    vertical = 6.dp,
                    horizontal = 12.dp
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                    Text(text = "Add More")
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)){
            if(exercises.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(96.dp)
                        .background(
                            color = colorResource(R.color.green_200).copy(alpha = 0.05f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()

                            drawRoundRect(
                                color = green.copy(alpha = 0.1f),
                                style = Stroke(
                                    width = strokeWidth,
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(
                                            6.dp.toPx(),
                                            4.dp.toPx()
                                        ),
                                        0f
                                    ),
                                    join = StrokeJoin.Miter,
                                    miter = 28.96f
                                ),
                                cornerRadius = CornerRadius(12.dp.toPx())
                            )
                        },
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = emptyListMessage,
                        color = colorResource(R.color.grey_300),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            exercises.forEach { AddWorkoutPlanExerciseElement(name = it.name, imageResourceId = it.illustrationResId, predictedTimeInMinutes = it.predictedTimeInMinutes) }

        }
    }
}

@Composable
fun AddWorkoutPlanExerciseElement(name: String, imageResourceId: Int?, predictedTimeInMinutes: Int) {
    Column(
        modifier = Modifier
            .width(128.dp)
            .height(152.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier
            .width(126.dp)
            .height(126.dp)
            .border(
                width = 0.5.dp,
                color = colorResource(id = R.color.green_200).copy(alpha = 0.2f),
                shape = RoundedCornerShape(24.dp)
            )) {
            Image(
                painter = painterResource(
                    id = imageResourceId ?: R.drawable.baseline_fitness_center_24
                ),
                contentScale = ContentScale.Crop,
                contentDescription = name,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))

            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f),
                            )
                        )
                    )

            )
            Text(
                text = ("$predictedTimeInMinutes mins"),
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 9.dp, bottom = 9.dp)
            )
        }
        Text(text = name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = colorResource(R.color.off_white))
    }
}

@Preview(showBackground = true)
@Composable
fun AddWorkoutPlanExercisePreview() {

    val warmups = listOf(
        WorkoutExercise(
            name = "Squats",
            description = "Leg exercise",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 10,
            illustrationResId = R.drawable.warmup2,
            illustrationUri = null,
            numberOfSets = 3,
            restTimeBetweenSets = 30
        ),
        WorkoutExercise(
            name = "Push-ups",
            description = "Upper body exercise",
            equipmentNeeded = setOf("None"),
            predictedTimeInMinutes = 5,
            illustrationResId = R.drawable.warmup1,
            illustrationUri = null,
            numberOfSets = 3,
            restTimeBetweenSets = 30
        )
    )
    Surface(
        color = Color(0xFF102216),
        modifier = Modifier.fillMaxWidth()
    ) {
        AddWorkoutPlanExercise(exercises = listOf(), addMoreClick = {}, emptyListMessage = "No stretches added yet")
    }
}