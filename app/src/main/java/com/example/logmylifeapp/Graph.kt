package com.example.logmylifeapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.logmylifeapp.repository.AchievementProgressRepository
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import com.example.logmylifeapp.repository.WorkoutExerciseRepository
import com.example.logmylifeapp.repository.WorkoutPlanExerciseCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Graph {
    lateinit var database: LogMyLifeDatabase

    val achievementProgressRepository by lazy {
        AchievementProgressRepository(achievementProgressDao = database.achievementProgressDao())
    }
    val dailyLifeDataAnswerRepository by lazy {
        DailyLifeDataAnswerRepository(dao = database.dailyLifeDataAnswerDao())
    }
    val dailyLifeDataQuestionRepository by lazy {
        DailyLifeDataQuestionRepository(dao = database.dailyLifeDataQuestionDao())
    }

    val workoutPlanRepository by lazy {
        WorkoutPlanRepository(dao = database.workoutPlanDao())
    }

    val workoutExerciseRepository by lazy {
        WorkoutExerciseRepository(dao = database.workoutExerciseDao())
    }

    val workoutPlanExerciseCrossRefRepository by lazy {
        WorkoutPlanExerciseCrossRefRepository(dao = database.workoutPlanExerciseCrossRefDao())
    }

    fun provide(context: Context) {
        context.applicationContext.deleteDatabase("logmylife.db");
        database = Room.databaseBuilder(context, LogMyLifeDatabase::class.java, "logmylife.db")
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    CoroutineScope(Dispatchers.IO).launch {
                        val questionDao = database.dailyLifeDataQuestionDao()
                        val answerDao = database.dailyLifeDataAnswerDao()
                        val workoutPlanDao = database.workoutPlanDao()
                        val workoutExerciseDao = database.workoutExerciseDao()
                        val crossRefDao = database.workoutPlanExerciseCrossRefDao()
                        val workoutSessionDao = database.workoutSessionDao()
                        val workoutExerciseLogDao = database.workoutExerciseLogDao()
                        val workoutExerciseSetLogDao = database.workoutExerciseSetLogDao()

                        val initialQuestionIds = questionDao.addQuestions(InitialData.questions())
                        questionDao.addQuestions(DummyData.questions())

                        val moodQuestionId = initialQuestionIds.first().toInt()

                        answerDao.addAnswers(InitialData.answers(moodQuestionId))

                        val initialWorkoutIds =
                            workoutPlanDao.addWorkoutPlans(DummyData.workoutPlans())
                        val initialWorkoutPlanId = initialQuestionIds.first().toInt()
                        val exerciseIds = workoutExerciseDao.addWorkoutExercises(
                            DummyData.workoutExercises(
                            )
                        );

                        exerciseIds.forEachIndexed { index, exerciseId ->
                            crossRefDao.addWorkoutPlanExerciseCrossRef(
                                WorkoutPlanExerciseCrossRef(
                                    planId = initialWorkoutPlanId,
                                    exerciseId = exerciseId.toInt(),
                                    orderInWorkout = index + 1
                                )
                            )
                        }

                        val workoutSessionIds = workoutSessionDao.addWorkoutSessions(DummyData.workoutSessions(initialWorkoutPlanId))
                        workoutSessionIds.forEach{ sessionId ->
                            exerciseIds.forEach { exerciseId ->
                                workoutExerciseLogDao.addWorkoutExerciseLog(WorkoutExerciseLog(sessionId = sessionId.toInt(), exerciseId = exerciseId.toInt()))
                            }

                        }

                    }
                }

            }).build()

    }
}