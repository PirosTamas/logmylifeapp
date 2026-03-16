package com.example.logmylifeapp

import android.app.Application

class LogMyLifeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.driverFactory = buildDatabase(this)
        Graph.settingsRepository = buildSettingsRepository(this)
    }
}
