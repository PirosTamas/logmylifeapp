package com.example.logmylifeapp.repository

import com.example.logmylifeapp.dao.AchievementProgressDao
import com.example.logmylifeapp.model.AchievementProgress
import kotlinx.coroutines.flow.Flow

class AchievementProgressRepository(private val achievementProgressDao: AchievementProgressDao) {
    suspend fun addAchievementProgress(achievementProgress: AchievementProgress){
        achievementProgressDao.addAchievementProgress(achievementProgress)
    }

    fun getAchievementProgresses(): Flow<List<AchievementProgress>> = achievementProgressDao.getAllAchievementProgresses()

    fun getAchievementProgressById(id: Int): Flow<AchievementProgress> {
        return achievementProgressDao.getAchievementProgressById(id)
    }



    suspend fun updateAchievementProgress(achievementProgress: AchievementProgress){
        achievementProgressDao.updateAchievementProgress(achievementProgress)
    }

    suspend fun deleteAchievementProgress(achievementProgress: AchievementProgress){
        achievementProgressDao.deleteAchievementProgress(achievementProgress)
    }
}
