package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DailyLifeDataViewModel(private val dailyLifeDataQuestionRepository: DailyLifeDataQuestionRepository = Graph.dailyLifeDataQuestionRepository,
                             private val dailyLifeDataAnswerRepository: DailyLifeDataAnswerRepository = Graph.dailyLifeDataAnswerRepository
) : ViewModel() {

    lateinit var getUnansweredQuestionsForToday: Flow<List<DailyLifeDataQuestion>>


    init {
        viewModelScope.launch {
            getUnansweredQuestionsForToday = dailyLifeDataQuestionRepository.getUnansweredQuestionsForToday()
        }
    }

    fun addDailyLifeAnswers(dailyLifeDataAnswers: List<DailyLifeDataAnswer>) {
        viewModelScope.launch(Dispatchers.IO) {
            dailyLifeDataAnswerRepository.addAnswers(dailyLifeDataAnswers)
        }
    }

}