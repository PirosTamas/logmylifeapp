package com.example.logmylifeapp.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.R
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
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, colorResource(R.color.off_white), RoundedCornerShape(24.dp))
            .background(colorResource(R.color.white), RoundedCornerShape(24.dp))
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .background(color = colorResource(R.color.orange_500).copy(0.1f))
            )
            {
                Icon(
                    painter = painterResource(R.drawable.outline_celebration_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center),
                    tint = colorResource(id = R.color.orange_500)
                )

            }

            Column(
                modifier = modifier
            ) {
                Text(
                    text = achievement.name,
                    style = if (achievement.dayChecked) {
                        TextStyle(textDecoration = TextDecoration.LineThrough)
                    } else {
                        TextStyle(textDecoration = TextDecoration.None)
                    },
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Day ${achievement.currentSession}",
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.ExtraLight
                )
            }
        }
        Checkbox(
            checked = achievement.dayChecked,
            onCheckedChange = {
                if (!achievement.dayChecked) {
                    viewModel.updateAchievementProgress(
                        achievementProgress = achievement.copy(
                            dayChecked = true
                        )
                    )
                }
            },
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(R.color.green_200),
                checkmarkColor = colorResource(R.color.blue_900)
            ),
            modifier = Modifier.scale(1.3f).clip(RoundedCornerShape(8.dp))
        )

    }

//        FloatingActionButton(
//            onClick = {
//                if (!achievement.dayChecked) {
////                    viewModel.updateAchievementProgress(
////                        achievementProgress = achievement.copy(
////                            dayChecked = true
////                        )
////                    )
//                }
//            },
//            modifier = Modifier
//                .align(Alignment.CenterEnd)
//                .padding(end = 24.dp),
//            shape = CircleShape,
//            containerColor = if (achievement.dayChecked) {
//                Color(0xFF405843)
//            } else {
//                Color(0xFFE0E0E0)
//            },
//            elevation = FloatingActionButtonDefaults.elevation(4.dp)
//        ) {
//            if(achievement.dayChecked) {
//                Icon(
//                    imageVector = Icons.Filled.Check,
//                    contentDescription = null,
//                    tint = Color(0xFFE3E3E3)
//                )
//            }
//        }

}

//@Preview(showBackground = true)
//@Composable
//fun DailyAchievementItemPreview() {
////    val viewModel: HomeViewModel = viewModel()
//    DailyAchievementItem(
//        AchievementProgress(
//            1, "30 days of practice", AchievementCategory.WORK,
//            setOf(
//                DayOfWeek.SUNDAY
//            ),
//            3, 15,
//            true,
//            LocalDate.now(),
//        ),
//        Modifier,
//        //viewModel
//
//    )
//}