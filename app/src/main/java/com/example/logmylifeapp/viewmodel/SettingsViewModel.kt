package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

// @HiltViewModel tells Hilt to manage this ViewModel's lifecycle and injection
// @Inject constructor tells Hilt what dependencies to provide automatically
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    // stateIn converts a cold Flow into a hot StateFlow that the UI can observe.
    // WhileSubscribed(5000) keeps it alive 5s after the last subscriber (e.g. screen rotation)
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

    fun setName(name: String) {
        viewModelScope.launch { settingsRepository.setName(name) }
    }

    fun setWeightUnit(unit: String) {
        viewModelScope.launch { settingsRepository.setWeightUnit(unit) }
    }

    fun setWaterReminderEnabled(enabled: Boolean) {
        viewModelScope.launch { settingsRepository.setWaterReminderEnabled(enabled) }
    }
}
