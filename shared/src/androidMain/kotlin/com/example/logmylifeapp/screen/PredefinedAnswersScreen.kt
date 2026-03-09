package com.example.logmylifeapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logmylifeapp.ui.theme.LocalAppColors
import com.example.logmylifeapp.viewmodel.AddDailyLifeQuestionViewModel

@Composable
fun PredefinedAnswersScreen(
    viewModel: AddDailyLifeQuestionViewModel,
    navigateBack: () -> Unit
) {
    val colors = LocalAppColors.current
    val green = colors.primary
    val predefinedAnswers = viewModel.predefinedAnswers
    var newAnswerText by remember { mutableStateOf("") }

    Scaffold(
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
                    onClick = navigateBack,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = colors.onBackground
                    )
                }
                Text(
                    text = "Predefined answers",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
                IconButton(
                    onClick = { /* TODO: show info dialog */ },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info",
                        tint = colors.onSurfaceVariant
                    )
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
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
                        val trimmed = newAnswerText.trim()
                        if (trimmed.isNotBlank()) {
                            predefinedAnswers.add(trimmed)
                            newAnswerText = ""
                        }
                        predefinedAnswers.removeAll { it.isBlank() }
                        navigateBack()
                    }
                ) {
                    Text(
                        text = "Save Answers",
                        fontSize = 18.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(text ="Add your possible answer", fontSize = 14.sp, lineHeight = 20.sp, fontWeight = FontWeight.Medium, color =  colors.onSurfaceVariant)
            predefinedAnswers.forEachIndexed { index, answer ->
                key(index) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(colors.surface, RoundedCornerShape(12.dp))
                            .padding(start = 16.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BasicTextField(
                            value = answer,
                            onValueChange = { predefinedAnswers[index] = it },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = colors.onSurface
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { predefinedAnswers.removeAt(index) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete answer",
                                tint = colors.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = newAnswerText,
                onValueChange = { newAnswerText = it },
                placeholder = {
                    Text("Type a new answer...", color = colors.onSurfaceVariant)
                },
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    color = colors.onBackground
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = colors.inputBackground,
                    unfocusedContainerColor = colors.inputBackground,
                    focusedBorderColor = colors.inputBorder,
                    unfocusedBorderColor = colors.inputBorder,
                    cursorColor = green
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = green,
                            style = Stroke(
                                width = 1.5.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(12f, 8f),
                                    phase = 0f
                                )
                            ),
                            cornerRadius = CornerRadius(12.dp.toPx())
                        )
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        val trimmed = newAnswerText.trim()
                        if (trimmed.isNotBlank()) {
                            predefinedAnswers.add(trimmed)
                            newAnswerText = ""
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = green,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Add Answer",
                        color = green,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = colors.onSurfaceVariant.copy(alpha = 0.2f))
            Spacer(modifier = Modifier.height(8.dp))
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "ANSWER SETTINGS",
                    color = colors.onSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "These answers will appear in your question",
                    color = colors.onSurfaceVariant,
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                )
            }

        }
    }
}
