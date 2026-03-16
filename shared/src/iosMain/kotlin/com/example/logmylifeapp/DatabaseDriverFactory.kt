package com.example.logmylifeapp

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.logmylifeapp.data.LogMyLifeDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(LogMyLifeDatabase.Schema, "logmylife.db")
}
