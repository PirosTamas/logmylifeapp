package com.example.logmylifeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ThumbUp
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.R
import com.example.logmylifeapp.screen.components.AchievementProgressItem
import com.example.logmylifeapp.screen.components.DailyAchievementItem
import com.example.logmylifeapp.viewmodel.HomeViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(
    navigateToAddProgress: () -> Unit,
    navigateToAddQuestion: () -> Unit,
    navigateToDailyLifeData: () -> Unit,
    navigateToYearInPixels: () -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val today = LocalDate.now()
    val currentDayOfWeek = today.dayOfWeek
    val achievementProgresses =
        viewModel.getAllAchievementProgresses.collectAsState(initial = listOf())

    var navigationSelectedIndex by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 22.dp, horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Welcome back, Tamás!",
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
                achievementProgresses.value.forEach {
                    AchievementProgressItem(achievement = it)
                }
            }

            Text(
                "Daily tasks",
                modifier = Modifier.padding(bottom = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(
                modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                achievementProgresses.value.filter { achievement ->
                    !achievement.startDate.isAfter(today) && achievement.scheduledDays.contains(
                        currentDayOfWeek
                    )
                }.forEach { achievement ->
                    DailyAchievementItem(
                        achievement = achievement,
                        modifier = Modifier,
                        viewModel = viewModel
                    )
                }
            }
        }

//        SingleChoiceSegmentedButtonRow(
//            modifier = Modifier.align(Alignment.BottomCenter)
//                .padding(bottom = 40.dp)
//        ) {
//            SegmentedButton(
//                shape = SegmentedButtonDefaults.itemShape(
//                    index = 0,
//                    count = 4
//                ),
//                onClick = { navigationSelectedIndex = 0 },
//                selected = navigationSelectedIndex == 0,
//                label = { Icon(
//                    painter = painterResource(id = R.drawable.outline_analytics_24),
//                    contentDescription = "Year in pixels"
//                )}
//            )
//            SegmentedButton(
//                shape = SegmentedButtonDefaults.itemShape(
//                    index = 1,
//                    count = 4
//                ),
//                onClick = { navigationSelectedIndex = 1 },
//                selected = navigationSelectedIndex == 1,
//                label = { Icon(
//                    painter = painterResource(id = R.drawable.outline_exercise_24),
//                    contentDescription = "Year in pixels"
//                )}
//            )
//        }

        FloatingActionButton(
            onClick = { navigateToAddQuestion() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 130.dp, end = 16.dp)
        ) {
            Icon(Icons.Default.ThumbUp, contentDescription = "Add DailyLifeDataQuestion")
        }
        FloatingActionButton(
            onClick = { navigateToAddProgress() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 60.dp, end = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add achievement")
        }
        FloatingActionButton(
            onClick = { navigateToDailyLifeData() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 200.dp, end = 16.dp)
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
    HomeScreen({}, {}, {}, {})
}