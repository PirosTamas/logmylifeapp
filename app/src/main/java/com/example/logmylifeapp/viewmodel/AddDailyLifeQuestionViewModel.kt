package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AddDailyLifeQuestionViewModel(private val dailyLifeDataQuestionRepository: DailyLifeDataQuestionRepository = Graph.dailyLifeDataQuestionRepository
) : ViewModel() {


    fun addDailyLifeDataQuestion(dailyLifeDataQuestion: DailyLifeDataQuestion) {
        viewModelScope.launch(Dispatchers.IO) {
            dailyLifeDataQuestionRepository.addQuestion(dailyLifeDataQuestion)
        }
    }

}