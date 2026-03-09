package com.example.logmylifeapp.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.screen.components.FormControl
import com.example.logmylifeapp.screen.components.InputField
import com.example.logmylifeapp.screen.components.labelText
import com.example.logmylifeapp.ui.theme.LocalAppColors
import com.example.logmylifeapp.viewmodel.AddDailyLifeQuestionViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn


@Composable
fun AddDailyLifeDataQuestionScreen(
    viewModel: AddDailyLifeQuestionViewModel,
    navigateToHome: () -> Unit,
    navigateToPredefinedAnswers: () -> Unit
) {
    val colors = LocalAppColors.current
    val green = colors.primary
    val context = LocalContext.current

    val fields = viewModel.fields
    val answerCount = viewModel.predefinedAnswers.size

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
                    .drawBehind {
                        drawLine(
                            color = green.copy(alpha = 0.1f),
                            start = Offset(0f, size.height),
                            end = Offset(size.width, size.height),
                            strokeWidth = 1.dp.toPx()
                        )
                    }
            ) {
                IconButton(
                    onClick = navigateToHome,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = colors.onSurfaceVariant
                    )
                }
                Text(
                    text = "Add day finish question",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    fields.forEach { field ->
                        FormControl(field)
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Allow custom answer",
                            fontSize = 16.sp,
                            color = colors.onBackground
                        )
                        Switch(
                            checked = viewModel.customAnswerAllowed,
                            onCheckedChange = { viewModel.customAnswerAllowed = it }
                        )
                    }

                    labelText("Predefined Answers")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.surface, RoundedCornerShape(12.dp))
                            .clickable { navigateToPredefinedAnswers() }
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null,
                                tint = green,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = if (answerCount == 0) "Add predefined answers"
                                       else "$answerCount answer${if (answerCount == 1) "" else "s"} added",
                                fontSize = 16.sp,
                                color = if (answerCount == 0) colors.onSurfaceVariant else colors.onSurface
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = colors.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 8.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = green,
                            contentColor = colors.onPrimary
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            val question = (fields[0] as InputField.TextField).value
                            val scheduledDays = (fields[1] as InputField.MultiSelectDays).selectedDays
                            val startDate = (fields[2] as InputField.DateField).date ?: Clock.System.todayIn(
                                TimeZone.currentSystemDefault())

                            if (question.isNotBlank()) {
                                val newQuestion = DailyLifeDataQuestion(
                                    id = 0,
                                    question = question,
                                    scheduledDays = scheduledDays,
                                    startDate = startDate,
                                    customAnswerAllowed = viewModel.customAnswerAllowed,
                                    predefinedAnswers = viewModel.predefinedAnswers
                                        .filter { it.isNotBlank() }
                                        .toSet()
                                )
                                viewModel.addDailyLifeDataQuestion(newQuestion) {
                                    navigateToHome()
                                }
                            } else {
                                Toast.makeText(context, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Save",
                                fontSize = 18.sp,
                                lineHeight = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    )
}
