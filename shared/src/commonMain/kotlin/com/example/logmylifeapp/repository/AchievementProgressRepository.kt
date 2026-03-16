package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.achievementCategoryAdapter
import com.example.logmylifeapp.data.dayOfWeekSetAdapter
import com.example.logmylifeapp.data.localDateAdapter
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.AchievementProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AchievementProgressRepository(private val db: LogMyLifeDatabase) {

    suspend fun addAchievementProgress(progress: AchievementProgress) = withContext(Dispatchers.Default) {
        db.achievementProgressQueries.addAchievementProgress(
            name = progress.name,
            category = achievementCategoryAdapter.encode(progress.category),
            scheduledDays = dayOfWeekSetAdapter.encode(progress.scheduledDays),
            currentSession = progress.currentSession,
            numberOfSessions = progress.numberOfSessions,
            dayChecked = progress.dayChecked,
            startDate = localDateAdapter.encode(progress.startDate)
        )
    }

    fun getAchievementProgresses(): Flow<List<AchievementProgress>> =
        db.achievementProgressQueries.getAllAchievementProgresses()
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }

    fun getAchievementProgressById(id: Int): Flow<AchievementProgress> =
        db.achievementProgressQueries.getAchievementProgressById(id)
            .asFlow().mapToOne(Dispatchers.Default)
            .map { it.toModel() }

    suspend fun updateAchievementProgress(progress: AchievementProgress) = withContext(Dispatchers.Default) {
        db.achievementProgressQueries.updateAchievementProgress(
            id = progress.id,
            name = progress.name,
            category = achievementCategoryAdapter.encode(progress.category),
            scheduledDays = dayOfWeekSetAdapter.encode(progress.scheduledDays),
            currentSession = progress.currentSession,
            numberOfSessions = progress.numberOfSessions,
            dayChecked = progress.dayChecked,
            startDate = localDateAdapter.encode(progress.startDate)
        )
    }

    suspend fun deleteAchievementProgress(progress: AchievementProgress) = withContext(Dispatchers.Default) {
        db.achievementProgressQueries.deleteAchievementProgress(progress.id)
    }
}
