package com.example.logmylifeapp.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.viewmodel.DailyLifeDataViewModel
import com.example.logmylifeapp.ui.components.RadioButtonSingleSelection
import java.time.LocalDate
import kotlin.collections.contains
import kotlin.collections.get


@Composable
fun DailyLifeDataScreen(modifier: Modifier = Modifier, navigateToHome: () -> Unit) {
    val viewModel: DailyLifeDataViewModel = viewModel()
    val questions = viewModel.getUnansweredQuestionsForToday.collectAsState(initial = listOf())
//    val questions = remember {
//        mutableStateOf(
//            listOf(
//                DailyLifeDataQuestion(
//                    id = 1,
//                    question = "How was your day?",
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
//                        "Yes 💪",
//                        "No 😅"
//                    )
//                )
//            )
//        )
//    }
    var currentIndex by remember { mutableIntStateOf(0) }

    val answers = remember { mutableStateMapOf<Int, String>() }

    if (questions.value.isEmpty()) {
        Text("Loading...", modifier = Modifier
            .fillMaxSize()
            .padding(top = 45.dp))
        return
    }

    val currentQuestion = questions.value[currentIndex]

    val options = if (currentQuestion.customAnswerAllowed){
        currentQuestion.predefinedAnswers + "Other"
    } else {
        currentQuestion.predefinedAnswers
    }.toSet()

    var selectedAnswer by remember {
        mutableStateOf("")
    }

    var customAnswerText by remember { mutableStateOf("") }

    val progress = (currentIndex) / questions.value.size.toFloat()

    fun restoreAnswer(question: DailyLifeDataQuestion) {
        val savedAnswer = answers[question.id]

        if (savedAnswer == null) {
            selectedAnswer = question.predefinedAnswers.first()
            customAnswerText = ""
        } else if (question.predefinedAnswers.contains(savedAnswer)) {
            selectedAnswer = savedAnswer
            customAnswerText = ""
        } else {
            selectedAnswer = "Other"
            customAnswerText = savedAnswer
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 16.dp),
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
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 4.dp)
                )
                Text(
                    text = "${currentIndex + 1} / ${questions.value.size}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 4.dp, start = 10.dp)
                )
            }


            Text(
                text = currentQuestion.question,
                modifier = Modifier.padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            RadioButtonSingleSelection(
                options = options,
                selectedOption = selectedAnswer,
                onOptionSelected = { selectedAnswer = it }
            )

            if (selectedAnswer == "Other") {
                OutlinedTextField(
                    value = customAnswerText,
                    onValueChange = { customAnswerText = it },
                    label = { Text("Your answer") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (currentIndex > 0) {
                Button(
                    onClick = {
                        currentIndex--
                        restoreAnswer(questions.value[currentIndex])

                    },
                ) {

                    Text(
                        text = "Back"
                    )

                }
            }

            Button(
                onClick = {
                    val answerToSave =
                        if (selectedAnswer == "Other") customAnswerText else selectedAnswer
                    if (answerToSave.isNotBlank()) {
                        answers[currentQuestion.id] = answerToSave
                    }

                    if (currentIndex < questions.value.lastIndex) {
                        currentIndex++
                        customAnswerText = ""
                        selectedAnswer = questions.value[currentIndex].predefinedAnswers.first()
                    } else {
                        val dailyLifeAnswers = answers.map { (questionId, answerText) ->
                            DailyLifeDataAnswer(
                                questionId = questionId,
                                answer = answerText,
                                createdAt = LocalDate.now()

                            )

                        }
                        viewModel.addDailyLifeAnswers(dailyLifeAnswers)
                        navigateToHome()
                    }
                },
            ) {
                Text(
                    text = if (currentIndex < questions.value.lastIndex) "Next" else "Done"
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DailyLifeDataScreenPreview() {
    DailyLifeDataScreen(
        modifier = Modifier,
        {}
    )
}

