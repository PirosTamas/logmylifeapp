package com.example.logmylifeapp.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.logmylifeapp.model.UserSettingsDefaults
import com.example.logmylifeapp.model.UserSettingsKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    val name: Flow<String> = dataStore.data.map { it[UserSettingsKeys.NAME] ?: UserSettingsDefaults.NAME }
    val weightUnit: Flow<String> = dataStore.data.map { it[UserSettingsKeys.WEIGHT_UNIT] ?: UserSettingsDefaults.WEIGHT_UNIT }
    val waterReminderEnabled: Flow<Boolean> = dataStore.data.map { it[UserSettingsKeys.WATER_REMINDER] ?: UserSettingsDefaults.WATER_REMINDER }
    val isDarkMode: Flow<Boolean> = dataStore.data.map { it[UserSettingsKeys.DARK_MODE] ?: UserSettingsDefaults.DARK_MODE }

    suspend fun setName(name: String) {
        dataStore.edit { it[UserSettingsKeys.NAME] = name }
    }

    suspend fun setWeightUnit(unit: String) {
        dataStore.edit { it[UserSettingsKeys.WEIGHT_UNIT] = unit }
    }

    suspend fun setWaterReminderEnabled(enabled: Boolean) {
        dataStore.edit { it[UserSettingsKeys.WATER_REMINDER] = enabled }
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { it[UserSettingsKeys.DARK_MODE] = enabled }
    }
}
