package com.example.logmylifeapp.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.example.logmylifeapp.R
import kotlin.random.Random

class WaterNotificationService(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    companion object{
        const val CHANNEL_ID = "water_reminder"
    }

    init{
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Water Reminder",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Reminds you to drink water"
        }

        notificationManager.createNotificationChannel(channel)
    }

    fun showBasicNotification(){
        val notification = NotificationCompat.Builder(context, "water_reminder")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Water Reminder")
            .setContentText("Time to drink some water")
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }



    private fun Context.bitmapFromResource(
        @DrawableRes resId: Int
    ) = BitmapFactory.decodeResource(resources, resId)

}