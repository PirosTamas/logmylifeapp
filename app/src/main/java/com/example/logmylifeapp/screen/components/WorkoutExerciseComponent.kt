package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.logmylifeapp.R
import com.example.logmylifeapp.dto.WorkoutExerciseComponentDTO
import com.example.logmylifeapp.dto.WorkoutSummaryExerciseDTO
import com.example.logmylifeapp.dto.WorkoutSummarySetDTO

@Composable
fun WorkoutExerciseComponent(exercise: WorkoutExerciseComponentDTO, color: Color){
    Column(modifier = Modifier.width(132.dp).height(147.dp).clip(RoundedCornerShape(6.dp)).background(color), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(text = exercise.name)
        Image(
            painter = painterResource(exercise.illustrationResId),
            contentDescription = exercise.name,
            modifier = Modifier
                .width(83.dp)
                .height(83.dp)
                .aspectRatio(1f)

        )
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutExerciseComponentPreview() {
    WorkoutExerciseComponent(exercise = WorkoutExerciseComponentDTO(name = "Fekvenyomás", illustrationResId = R.drawable.benchpress), color = Color(0xCFCFEDDE))
}