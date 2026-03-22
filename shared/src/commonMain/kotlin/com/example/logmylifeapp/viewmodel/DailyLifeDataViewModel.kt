package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class DailyLifeDataViewModel(
    private val dailyLifeDataQuestionRepository: DailyLifeDataQuestionRepository = Graph.dailyLifeDataQuestionRepository,
    private val dailyLifeDataAnswerRepository: DailyLifeDataAnswerRepository = Graph.dailyLifeDataAnswerRepository
) : ViewModel() {
    private val _questions = MutableStateFlow<List<DailyLifeDataQuestion>>(emptyList())
    val questions: StateFlow<List<DailyLifeDataQuestion>> = _questions

    private val _answers = MutableStateFlow<MutableMap<Int, String>>(mutableMapOf())
    val answers: StateFlow<Map<Int, String>> = _answers


    private val _index = MutableStateFlow(0)
    val index: StateFlow<Int> = _index

    val currentQuestion: StateFlow<DailyLifeDataQuestion?> =
        _index.combine(_questions) { idx, list ->
            if (list.isNotEmpty() && idx in list.indices) {
                list[idx]
            } else null
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
        )


    init {
        viewModelScope.launch {
            val list = dailyLifeDataQuestionRepository.getUnansweredQuestionsForToday()
            _questions.value = list
        }
    }

    fun saveAnswer(questionId: Int, answer: String) {
        _answers.value = _answers.value.toMutableMap().apply {
            put(questionId, answer)
        }
    }

    fun getAnswer(questionId: Int): String? {
        return _answers.value[questionId]
    }

    fun increaseIndex() {
        if (_index.value < questions.value.lastIndex) {
            _index.value++;
        }

    }

    fun decreaseIndex() {
        if (_index.value > 0) {
            _index.value--;
        }
    }

    fun finish() {
        viewModelScope.launch {
            dailyLifeDataAnswerRepository.addAnswers(_answers.value.map { answer ->
                DailyLifeDataAnswer(
                    questionId = answer.key,
                    answer = answer.value,
                    createdAt = Clock.System.todayIn(TimeZone.currentSystemDefault())
                )
            }.toList())
            _answers.value = mutableMapOf()
            _index.value = 0
        }
    }


}
