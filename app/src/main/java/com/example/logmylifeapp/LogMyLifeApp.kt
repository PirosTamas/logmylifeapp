package com.example.logmylifeapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LogMyLifeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)

        val notificationChannel = NotificationChannel("water_reminder", "Water reminder channel",
            NotificationManager.IMPORTANCE_HIGH)

        notificationChannel.description = "A notification channel for water reminders"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)


    }
}


