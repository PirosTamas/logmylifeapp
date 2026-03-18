package com.example.logmylifeapp

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.example.logmylifeapp.repository.SettingsRepository
import okio.Path.Companion.toPath
import platform.Foundation.NSHomeDirectory

fun buildSettingsRepository(): SettingsRepository {
    val dataStore = PreferenceDataStoreFactory.createWithPath {
        (NSHomeDirectory() + "/Documents/settings.preferences_pb").toPath()
    }
    return SettingsRepository(dataStore)
}
