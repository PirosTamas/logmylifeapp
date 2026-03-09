package com.example.logmylifeapp

import android.app.Application
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.buildDatabase
import com.example.logmylifeapp.buildSettingsRepository

class LogMyLifeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.database = buildDatabase(this)
        Graph.settingsRepository = buildSettingsRepository(this)
    }
}
