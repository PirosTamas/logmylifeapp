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

class YearInPixelsViewModel(private val dailyLifeDataAnswerRepository: DailyLifeDataAnswerRepository = Graph.dailyLifeDataAnswerRepository
) : ViewModel() {

    lateinit var moodAnswers: Flow<List<DailyLifeDataAnswer>>


    init {
        viewModelScope.launch {
            moodAnswers = dailyLifeDataAnswerRepository.getAnswersForQuestion(1)
        }
    }



}