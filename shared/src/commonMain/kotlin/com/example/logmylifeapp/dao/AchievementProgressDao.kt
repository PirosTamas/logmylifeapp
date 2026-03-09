package com.example.logmylifeapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.logmylifeapp.model.AchievementProgress
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AchievementProgressDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addAchievementProgress(achievementProgressEntity: AchievementProgress)

    @Query("select * from achievement_progress")
    abstract fun getAllAchievementProgresses(): Flow<List<AchievementProgress>>

    @Update
    abstract suspend fun updateAchievementProgress(achievementProgressEntity: AchievementProgress)

    @Delete
    abstract suspend fun deleteAchievementProgress(achievementProgressEntity: AchievementProgress)

    @Query("select * from achievement_progress where id =:id")
    abstract fun getAchievementProgressById(id: Int): Flow<AchievementProgress>
}