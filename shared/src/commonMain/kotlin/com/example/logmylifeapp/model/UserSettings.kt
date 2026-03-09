package com.example.logmylifeapp.model

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserSettingsKeys {
    val NAME          = stringPreferencesKey("name")
    val WEIGHT_UNIT   = stringPreferencesKey("weight_unit")
    val WATER_REMINDER = booleanPreferencesKey("water_reminder")
    val DARK_MODE     = booleanPreferencesKey("dark_mode")
}

object UserSettingsDefaults {
    const val NAME           = ""
    const val WEIGHT_UNIT    = "kg"
    const val WATER_REMINDER = true
    const val DARK_MODE      = false
}
