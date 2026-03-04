package com.example.logmylifeapp.model

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

// This is the "shape" of your settings — same idea as WorkoutPlan.kt,
// but instead of a Room @Entity, these are DataStore keys.
object UserSettingsKeys {
    val NAME = stringPreferencesKey("name")
    val WEIGHT_UNIT = stringPreferencesKey("weight_unit")
    val WATER_REMINDER = booleanPreferencesKey("water_reminder")
}

// Default values live here too, so they are never scattered around the code
object UserSettingsDefaults {
    const val NAME = ""
    const val WEIGHT_UNIT = "kg"
    const val WATER_REMINDER = true
}
