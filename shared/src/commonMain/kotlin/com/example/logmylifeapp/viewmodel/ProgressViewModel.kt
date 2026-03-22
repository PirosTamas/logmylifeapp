package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.repository.AchievementProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProgressViewModel(
    private val achievementProgressRepository: AchievementProgressRepository = Graph.achievementProgressRepository
) : ViewModel() {

    private val _editingAchievement = MutableStateFlow<AchievementProgress?>(null)
    val editingAchievement: StateFlow<AchievementProgress?> = _editingAchievement.asStateFlow()

    val getAllAchievementProgresses: Flow<List<AchievementProgress>> =
        achievementProgressRepository.getAchievementProgresses()

    fun setEditingAchievement(achievement: AchievementProgress?) {
        _editingAchievement.value = achievement
    }

    fun addAchievementProgress(achievementProgress: AchievementProgress) {
        viewModelScope.launch(Dispatchers.Default) {
            achievementProgressRepository.addAchievementProgress(achievementProgress)
        }
    }

    fun updateAchievementProgress(achievementProgress: AchievementProgress) {
        viewModelScope.launch(Dispatchers.Default) {
            achievementProgressRepository.updateAchievementProgress(achievementProgress)
        }
    }

    fun getAchievementProgress(id: Int): Flow<AchievementProgress> {
        return achievementProgressRepository.getAchievementProgressById(id)
    }
}
