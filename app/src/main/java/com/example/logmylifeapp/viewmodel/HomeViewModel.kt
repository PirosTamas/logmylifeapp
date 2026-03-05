package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.repository.AchievementProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(private val achievementProgressRepository: AchievementProgressRepository = Graph.achievementProgressRepository) :
    ViewModel() {

    val getAllAchievementProgresses: Flow<List<AchievementProgress>> =
        achievementProgressRepository.getAchievementProgresses()

    fun addAchievementProgress(achievementProgress: AchievementProgress) {
        viewModelScope.launch(Dispatchers.IO) {
            achievementProgressRepository.addAchievementProgress(achievementProgress)
        }
    }

    fun updateAchievementProgress(achievementProgress: AchievementProgress) {
        viewModelScope.launch(Dispatchers.IO) {
            achievementProgressRepository.updateAchievementProgress(achievementProgress)
        }
    }

    fun getAchievementProgress(id: Int): Flow<AchievementProgress> {
        return achievementProgressRepository.getAchievementProgressById(id)

    }
}