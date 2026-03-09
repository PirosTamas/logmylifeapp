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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.shared.R
import com.example.logmylifeapp.screen.components.WorkoutPlanElement
import com.example.logmylifeapp.ui.theme.LocalAppColors
import com.example.logmylifeapp.viewmodel.WorkoutHomeViewModel
import kotlinx.coroutines.launch

@Composable
fun WorkoutHomeScreen(modifier: Modifier = Modifier, navigateToCurrentWorkout: (Long) -> Unit, navigateToAdd: () -> Unit) {
    val colors = LocalAppColors.current
    val viewModel: WorkoutHomeViewModel = viewModel()
    val workoutPlans = viewModel.workoutPlansForToday.collectAsState(initial = listOf())
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
            .padding(start = 24.dp, top = 56.dp, end = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "profile picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .border(width = 1.dp, color = colors.primary.copy(alpha = 0.3f), shape = CircleShape)
                )
                Column {
                    Text(
                        text = "Workout tracker".uppercase(),
                        color = colors.onSurfaceVariant,
                        fontSize = 12.sp, lineHeight = 16.sp, fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Tamas Piros",
                        color = colors.onBackground,
                        fontSize = 18.sp, lineHeight = 28.sp, fontWeight = FontWeight.Bold
                    )
                }
            }
            IconButton(
                onClick = { navigateToAdd() },
                modifier = Modifier.size(40.dp).shadow(elevation = 2.dp, CircleShape).clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = colors.primary,
                    contentColor = colors.onPrimary
                )
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add workout")
            }
        }
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "In progress",
                    color = colors.onBackground,
                    fontSize = 20.sp, lineHeight = 28.sp, fontWeight = FontWeight.ExtraBold
                )
                Box(
                    modifier = Modifier
                        .background(color = colors.primary.copy(alpha = 0.1f), shape = RoundedCornerShape(100.dp))
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Text(
                        text = "2 active".uppercase(),
                        color = colors.primary,
                        fontSize = 10.sp, lineHeight = 15.sp, fontWeight = FontWeight.ExtraBold
                    )
                }
            }
            workoutPlans.value.forEach { workoutPlan ->
                WorkoutPlanElement(workoutPlan = workoutPlan, onStartClick = { planId ->
                    coroutineScope.launch {
                        val sessionId = viewModel.addWorkoutSession(planId)
                        navigateToCurrentWorkout(sessionId)
                    }
                })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutHomeScreenPreview() {
    WorkoutHomeScreen(navigateToCurrentWorkout = {}, navigateToAdd = {})
}
