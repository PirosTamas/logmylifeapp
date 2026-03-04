package com.example.logmylifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.R
import com.example.logmylifeapp.model.AchievementCategory
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.screen.components.AchievementProgressItem
import com.example.logmylifeapp.screen.components.DailyAchievementItem
import com.example.logmylifeapp.viewmodel.HomeViewModel
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun HomeScreen(
    navigateToAddProgress: () -> Unit,
    navigateToAddQuestion: () -> Unit,
    navigateToDailyLifeData: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val today = LocalDate.now()
    val currentDayOfWeek = today.dayOfWeek
    val achievementProgresses =
        viewModel.getAllAchievementProgresses.collectAsState(initial = listOf())
//    val achievementProgresses = listOf(
//        AchievementProgress(
//            name = "Reading",
//            startDate = LocalDate.now(),
//            scheduledDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
//            currentSession = 3,
//            numberOfSessions = 4,
//            dayChecked = false,
//            category = AchievementCategory.READ
//        ),
//        AchievementProgress(
//            name = "Coding",
//            startDate = LocalDate.now(),
//            scheduledDays = setOf(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
//            currentSession = 2,
//            numberOfSessions = 8,
//            dayChecked = true,
//            category = AchievementCategory.WORK
//        )
    //   )


    var navigationSelectedIndex by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.off_white_300))
                .padding(vertical = 22.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome back, Tamás!",
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 24.sp,
                color = colorResource(R.color.blue_900),
                fontWeight = FontWeight.SemiBold
            )
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                Text(
                    "Goal Progress",
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    color = colorResource(R.color.blue_900),
                    fontWeight = FontWeight.ExtraBold
                )
                val progresses = achievementProgresses.value;

                if (progresses.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = colorResource(R.color.white),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .border(
                                color = colorResource(R.color.white),
                                shape = RoundedCornerShape(24.dp),
                                width = 1.dp
                            )
                            .padding(vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(shape = RoundedCornerShape(100.dp))
                                .background(color = colorResource(R.color.off_white_300))
                        )
                        {
                            Icon(
                                painter = painterResource(R.drawable.outline_flag_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(23.dp)
                                    .align(Alignment.Center),
                                tint = colorResource(id = R.color.grey_300)
                            )

                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No goals yet.",
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = colorResource(R.color.blue_900)
                            )
                            Text(
                                text = "Smart small and build momentum!",
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(R.color.grey_300)
                            )
                        }
                        Button(
                            onClick = {
                                navigateToAddProgress()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.white),
                                contentColor = colorResource(id = R.color.green_200)
                            ),
                            contentPadding = PaddingValues(
                                vertical = 12.dp,
                                horizontal = 24.dp
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = "Add Goal",
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                } else {
                    progresses.forEach {
                        AchievementProgressItem(achievement = it)
                    }
                }
            }


            Text(
                text = "Daily tasks",
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 32.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = colorResource(R.color.blue_900)
            )

            Column(
                modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val todaysAchievements = achievementProgresses.value.filter { achievement ->
                    !achievement.startDate.isAfter(today) &&
                            achievement.scheduledDays.contains(currentDayOfWeek)
                }

                if (todaysAchievements.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = colorResource(R.color.white),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .border(
                                color = colorResource(R.color.white),
                                shape = RoundedCornerShape(24.dp),
                                width = 1.dp
                            )
                            .padding(vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(shape = CircleShape)
                                .background(color = colorResource(R.color.green_200).copy(alpha = 0.2f))
                        )
                        {
                            Icon(
                                painter = painterResource(R.drawable.outline_rewarded_ads_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(42.dp)
                                    .align(Alignment.Center),
                                tint = colorResource(id = R.color.green_600)
                            )

                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "All caught up for today.",
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = colorResource(R.color.blue_900)
                            )
                            Text(
                                text = "You've completed everything on your list.",
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = colorResource(R.color.grey_300)
                            )
                        }
                        Button(
                            onClick = {
                                navigateToAddProgress()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.white),
                                contentColor = colorResource(id = R.color.green_200)
                            ),
                            contentPadding = PaddingValues(
                                vertical = 12.dp,
                                horizontal = 24.dp
                            )
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp),
                                )
                                Text(
                                    text = "New Task",
                                    fontSize = 14.sp,
                                    lineHeight = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                } else {
                    todaysAchievements.forEach { achievement ->
                        DailyAchievementItem(
                            achievement = achievement,
                            modifier = Modifier,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { navigateToAddQuestion() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 130.dp, end = 16.dp),
            containerColor = colorResource(R.color.green_200),
            contentColor = colorResource(R.color.blue_900),
            shape = CircleShape
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_maps_ugc_24),
                contentDescription = "Add DailyLifeDataQuestion"
            )
        }
        FloatingActionButton(
            onClick = { navigateToAddProgress() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 60.dp, end = 16.dp),
            containerColor = colorResource(R.color.green_200),
            contentColor = colorResource(R.color.blue_900),
            shape = CircleShape

        ) {
            Icon(Icons.Default.Add, contentDescription = "Add achievement")
        }
        FloatingActionButton(
            onClick = { navigateToDailyLifeData() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 200.dp, end = 16.dp),
            containerColor = colorResource(R.color.blue_900),
            contentColor = colorResource(R.color.white),
            shape = CircleShape
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_quiz_24),
                contentDescription = "Go to dailyLifeData"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen({}, {}, {})
}