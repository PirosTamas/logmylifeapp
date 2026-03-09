package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository = Graph.settingsRepository
) : ViewModel() {

    val name = settingsRepository.name.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ""
    )

    val weightUnit = settingsRepository.weightUnit.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = "kg"
    )

    val waterReminderEnabled = settingsRepository.waterReminderEnabled.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = true
    )

    val isDarkMode = settingsRepository.isDarkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    fun setName(name: String) {
        viewModelScope.launch { settingsRepository.setName(name) }
    }

    fun setWeightUnit(unit: String) {
        viewModelScope.launch { settingsRepository.setWeightUnit(unit) }
    }

    fun setWaterReminderEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setWaterReminderEnabled(enabled) }
    }

    fun setDarkMode(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setDarkMode(enabled) }
    }
}
