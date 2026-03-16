package com.example.logmylifeapp

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.logmylifeapp.data.LogMyLifeDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(LogMyLifeDatabase.Schema, context, "logmylife.db")
}
