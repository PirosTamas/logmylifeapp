package com.example.logmylifeapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import com.example.logmylifeapp.screen.components.InputField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddDailyLifeQuestionViewModel(
    private val dailyLifeDataQuestionRepository: DailyLifeDataQuestionRepository = Graph.dailyLifeDataQuestionRepository
) : ViewModel() {

    val fields = listOf(
        InputField.TextField(label = "Question"),
        InputField.MultiSelectDays(label = "Scheduled Days"),
        InputField.DateField(label = "Start Date")
    )

    var customAnswerAllowed by mutableStateOf(false)

    val predefinedAnswers = mutableStateListOf<String>()

    private var isSaving = false

    fun addDailyLifeDataQuestion(
        dailyLifeDataQuestion: DailyLifeDataQuestion,
        onDone: () -> Unit
    ) {
        if (isSaving) return
        isSaving = true

        viewModelScope.launch(Dispatchers.IO) {
            dailyLifeDataQuestionRepository.addQuestion(dailyLifeDataQuestion)
            withContext(Dispatchers.Main) {
                onDone()
            }
        }
    }
}
