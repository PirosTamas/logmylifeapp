package com.example.logmylifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.viewmodel.DailyLifeDataViewModel
import com.example.logmylifeapp.viewmodel.YearInPixelsViewModel
import kotlinx.datetime.LocalDate

@Composable
fun YearInPixelsScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit
) {
    val viewModel: YearInPixelsViewModel = viewModel()
    val answers = viewModel.moodAnswers.collectAsState(initial = listOf())
    val answersByDate = answers.value.associateBy { it.createdAt }

    val months = listOf("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D")
    val daysInMonth = listOf(
        31, 28, 31, 30, 31, 30,
        31, 31, 30, 31, 30, 31
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier.fillMaxSize()) {
            Text(
                text = "Year in pixels",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold

            )
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row {
                    Spacer(modifier = Modifier.size(24.dp))

                    months.forEach { month ->
                        Text(
                            text = month,
                            modifier = Modifier
                                .width(22.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                for (day in 1..31) {
                    Row {

                        Text(
                            text = day.toString(),
                            modifier = Modifier
                                .width(22.dp),
                            textAlign = TextAlign.Center
                        )


                        for (monthIndex in months.indices) {
                            val isValidDay = day <= daysInMonth[monthIndex]
                            val date = if (isValidDay) {
                                LocalDate(2026, monthIndex + 1, day)
                            } else null

                            val answerForDay = date.let { answersByDate[it] }
                            val boxColor = when {
                                !isValidDay -> Color.Transparent
                                answerForDay != null -> moodColor(answerForDay.answer)
                                else -> Color.LightGray
                            }
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .padding(2.dp)
                                    .background(
                                        color = boxColor,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

fun moodColor(answer: String): Color {
    return when (answer) {
        "1" -> Color(0xFFE53935) // red
        "2" -> Color(0xFFFF7043) // orange
        "3" -> Color(0xFFFFEB3B) // yellow
        "4" -> Color(0xFF81C784) // light green
        "5" -> Color(0xFF2E7D32) // green
        else -> Color.LightGray
    }
}

@Preview(showBackground = true)
@Composable
fun YearInPixelsScreenPreview() {
    YearInPixelsScreen(Modifier, {})
}