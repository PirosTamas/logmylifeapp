package com.example.logmylifeapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.logmylifeapp.dao.AchievementProgressDao
import com.example.logmylifeapp.model.DailyLifeDataAnswer
import com.example.logmylifeapp.dao.DailyLifeDataAnswerDao
import com.example.logmylifeapp.model.DailyLifeDataQuestion
import com.example.logmylifeapp.dao.DailyLifeDataQuestionDao
import com.example.logmylifeapp.dao.WorkoutExerciseDao
import com.example.logmylifeapp.dao.WorkoutExerciseLogDao
import com.example.logmylifeapp.dao.WorkoutExerciseSetLogDao
import com.example.logmylifeapp.dao.WorkoutPlanDao
import com.example.logmylifeapp.dao.WorkoutPlanExerciseCrossRefDao
import com.example.logmylifeapp.dao.WorkoutSessionDao
import com.example.logmylifeapp.model.AchievementProgress
import com.example.logmylifeapp.model.WorkoutExercise
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import com.example.logmylifeapp.model.WorkoutSession

@Database(
    entities = [AchievementProgress::class, DailyLifeDataQuestion::class, DailyLifeDataAnswer::class, WorkoutPlan::class, WorkoutExercise::class, WorkoutPlanExerciseCrossRef::class, WorkoutSession::class, WorkoutExerciseLog::class, WorkoutExerciseSetLog::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LogMyLifeDatabase : RoomDatabase() {
    abstract fun achievementProgressDao(): AchievementProgressDao
    abstract fun dailyLifeDataQuestionDao(): DailyLifeDataQuestionDao
    abstract fun dailyLifeDataAnswerDao(): DailyLifeDataAnswerDao
    abstract fun workoutPlanDao(): WorkoutPlanDao
    abstract fun workoutExerciseDao(): WorkoutExerciseDao
    abstract fun workoutPlanExerciseCrossRefDao(): WorkoutPlanExerciseCrossRefDao
    abstract fun workoutSessionDao(): WorkoutSessionDao
    abstract fun workoutExerciseLogDao(): WorkoutExerciseLogDao
    abstract fun workoutExerciseSetLogDao(): WorkoutExerciseSetLogDao


}