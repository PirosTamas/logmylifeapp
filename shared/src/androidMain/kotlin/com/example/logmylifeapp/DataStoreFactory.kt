package com.example.logmylifeapp

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.logmylifeapp.repository.SettingsRepository

private val Context.dataStore by preferencesDataStore(name = "settings")

fun buildSettingsRepository(context: Context): SettingsRepository {
    return SettingsRepository(context.applicationContext.dataStore)
}
