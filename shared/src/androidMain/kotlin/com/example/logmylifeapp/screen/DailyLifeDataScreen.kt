package com.example.logmylifeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.shared.R
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.ui.theme.LocalAppColors
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.screen.components.CustomRadioButton
import com.example.logmylifeapp.viewmodel.DailyLifeDataViewModel
import com.example.logmylifeapp.ui.components.RadioButtonSingleSelection
import kotlinx.coroutines.launch
import kotlin.collections.contains
import kotlin.collections.get


@Composable
fun DailyLifeDataScreen(modifier: Modifier = Modifier, navigateToHome: () -> Unit) {
    val viewModel: DailyLifeDataViewModel = viewModel()
//    val questionsNullable = remember {
//        mutableStateOf(
//            listOf(
//                DailyLifeDataQuestion(
//                    id = 1,
//                    question = "How are you feeling today?",
//                    scheduledDays = emptySet(),
//                    startDate = LocalDate.now(),
//                    customAnswerAllowed = true,
//                    predefinedAnswers = setOf(
//                        "Great 😄",
//                        "Okay 🙂",
//                        "Bad 😞"
//                    )
//                ),
//                DailyLifeDataQuestion(
//                    id = 2,
//                    question = "Did you exercise today?",
//                    scheduledDays = emptySet(),
//                    startDate = LocalDate.now(),
//                    customAnswerAllowed = true,
//                    predefinedAnswers = setOf(
//                        "Great",
//                        "Not so well"
//                    )
//                )
//            )
//        )
//    }
    val currentQuestionNullable by
    viewModel.currentQuestion.collectAsState(initial = null)

    val indexNullable by viewModel.index.collectAsState(initial = null)
    val questionsNullable by viewModel.questions.collectAsState(initial = null)

//    val currentQuestionNullable = DailyLifeDataQuestion(
//        id = 2,
//        question = "Did you exercise today?",
//        scheduledDays = emptySet(),
//        startDate = LocalDate.now(),
//        customAnswerAllowed = true,
//        predefinedAnswers = setOf(
//            "Great",
//            "Not so well"
//        )
//    )
//    val indexNullable = 1


    val coroutineScope = rememberCoroutineScope()

    val currentQuestion = currentQuestionNullable
    val index = indexNullable
    val questions = questionsNullable


    if (currentQuestion == null || index == null || questions == null) {
        Text("valami szar", fontSize = 32.sp)
        return
    }
    val questionSize = questions.size;

    var selectedAnswer by remember {
        mutableStateOf("")
    }

    var customAnswerText by remember { mutableStateOf("") }

    val progress: Float = (index) / questionSize.toFloat()

    val colors = LocalAppColors.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.background,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(74.dp)
            )
            {
                IconButton(
                    onClick = {
                        navigateToHome()
                    },
                    modifier = Modifier.align(Alignment.CenterStart),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colors.surface,
                        contentColor = colors.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                    )
                }
                Text(
                    text = "Step $index of $questionSize".uppercase(),
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.onSurfaceVariant
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(horizontal = 12.dp, vertical = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Overall Progress",
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = colors.onSurfaceVariant
                        )
                        Text(
                            text = "${(progress * 100).toInt()}%",
                            fontSize = 12.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.primary
                        )
                    }

                }
                Column(
                    modifier = Modifier
                        .fillMaxSize(),

                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            LinearProgressIndicator(
                                progress = { progress },
                                color = colors.primary,
                                trackColor = colors.surfaceVariant,
                                gapSize = 0.dp,
                                drawStopIndicator = {},
                                modifier = Modifier.height(6.dp).fillMaxWidth()
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(32.dp)
                    ) {


                        Text(
                            text = currentQuestion.question,
                            fontSize = 32.sp,
                            lineHeight = 40.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        CustomRadioButton(
                            options = currentQuestion.predefinedAnswers,
                            selected = selectedAnswer.ifBlank { currentQuestion.predefinedAnswers.first() },
                            onSelectedChange = { selectedAnswer = it},
                            otherAllowed = currentQuestion.customAnswerAllowed,
                            otherValue = customAnswerText,
                            onOtherValueChange = { customAnswerText = it }
                        )

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 24.dp),
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colors.primary,
                                contentColor = colors.onPrimary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                val finalAnswer = if (selectedAnswer == "Other") {
                                    customAnswerText
                                } else {
                                    selectedAnswer
                                }

                                if (finalAnswer.isNotBlank()) {
                                    viewModel.saveAnswer(currentQuestion.id, finalAnswer)

                                    if (index == questions.lastIndex) {
                                        viewModel.finish()
                                        navigateToHome()
                                    } else {
                                        viewModel.increaseIndex()
                                    }

                                    selectedAnswer = ""
                                    customAnswerText = ""
                                }
                            }) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                Text(
                                    text = if(index == questions.lastIndex) "Finish" else "Next",
                                    fontSize = 18.sp,
                                    lineHeight = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp),
                                )

                            }

                        }
                    }
                }
            }


        })


}


@Preview(showBackground = true)
@Composable
fun DailyLifeDataScreenPreview() {
    DailyLifeDataScreen(
        modifier = Modifier,
        {}
    )
}

