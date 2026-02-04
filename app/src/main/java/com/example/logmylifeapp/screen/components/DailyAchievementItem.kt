package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.model.AchievementCategory
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.viewmodel.HomeViewModel
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun DailyAchievementItem(
    achievement: AchievementProgress,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(24.dp))
                .background(Color(0xFFCCDBDA), RoundedCornerShape(24.dp))
                .padding(vertical = 33.dp, horizontal = 26.dp)
        ) {
            Text(
                text = achievement.name,
                style = if(achievement.dayChecked){
                    TextStyle(textDecoration = TextDecoration.LineThrough)
                }else{
                    TextStyle(textDecoration = TextDecoration.None)
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "Day ${achievement.currentSession}",
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraLight
            )
        }
        FloatingActionButton(
            onClick = {
                if (!achievement.dayChecked) {
                    viewModel.updateAchievementProgress(
                        achievementProgress = achievement.copy(
                            dayChecked = true
                        )
                    )
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 24.dp),
            shape = CircleShape,
            containerColor = if (achievement.dayChecked) {
                Color(0xFF405843)
            } else {
                Color(0xFFE0E0E0)
            },
            elevation = FloatingActionButtonDefaults.elevation(4.dp)
        ) {
            if(achievement.dayChecked) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = Color(0xFFE3E3E3)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DailyAchievementItemPreview() {
    val viewModel: HomeViewModel = viewModel()
    DailyAchievementItem(
        AchievementProgress(
            1, "30 days of practice", AchievementCategory.WORK,
            setOf(
                DayOfWeek.SUNDAY
            ),
            3, 15,
            false,
            LocalDate.now(),
        ),
        Modifier,
        viewModel

    )
}