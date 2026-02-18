package com.example.logmylifeapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.logmylifeapp.repository.AchievementProgressRepository
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import com.example.logmylifeapp.repository.CurrentWorkoutExerciseRepository
import com.example.logmylifeapp.repository.DailyLifeDataAnswerRepository
import com.example.logmylifeapp.repository.DailyLifeDataQuestionRepository
import com.example.logmylifeapp.repository.WorkoutExerciseRepository
import com.example.logmylifeapp.repository.WorkoutExerciseSetLogRepository
import com.example.logmylifeapp.repository.WorkoutPlanExerciseCrossRefRepository
import com.example.logmylifeapp.repository.WorkoutPlanRepository
import com.example.logmylifeapp.repository.WorkoutSessionRepository
import com.example.logmylifeapp.repository.WorkoutExerciseLogRepository
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

    val workoutSessionRepository by lazy {
        WorkoutSessionRepository(dao = database.workoutSessionDao())
    }

    val workoutExerciseLogRepository by lazy {
        WorkoutExerciseLogRepository(dao = database.workoutExerciseLogDao())
    }

    val workoutExerciseSetLogRepository by lazy {
        WorkoutExerciseSetLogRepository(dao = database.workoutExerciseSetLogDao())
    }

    val workoutExerciseRepository by lazy {
        WorkoutExerciseRepository(dao = database.workoutExerciseDao())
    }
    val currentWorkoutExerciseRepository by lazy {
        CurrentWorkoutExerciseRepository(dao = database.currentWorkoutExerciseDao())
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
                                    orderInWorkout = index
                                )
                            )
                        }

                        val workoutSessionId = workoutSessionDao.addWorkoutSessions(
                            DummyData.workoutSessions(initialWorkoutPlanId)
                        ).first()


                        exerciseIds.forEach { exerciseId ->
                            val workoutExerciseLogId =
                                workoutExerciseLogDao.addWorkoutExerciseLog(
                                    WorkoutExerciseLog(
                                        sessionId = workoutSessionId.toInt(),
                                        exerciseId = exerciseId.toInt()
                                    )
                                )
                            workoutExerciseSetLogDao.addWorkoutExerciseSetLogs(
                                listOf(
                                    WorkoutExerciseSetLog(
                                        exerciseLogId = workoutExerciseLogId.toInt(),
                                        order = 0,
                                        targetReps = 10,
                                        completedReps = 10,
                                        weight = 40f,
                                        success = true,
                                        description = "SUCCESS"
                                    ),
                                    WorkoutExerciseSetLog(
                                        exerciseLogId = workoutExerciseLogId.toInt(),
                                        order = 1,
                                        targetReps = 10,
                                        completedReps = 10,
                                        weight = 50f,
                                        success = true,
                                        description = "SUCCESS"
                                    ),
                                    WorkoutExerciseSetLog(
                                        exerciseLogId = workoutExerciseLogId.toInt(),
                                        order = 2,
                                        targetReps = 10,
                                        completedReps = 8,
                                        weight = 60f,
                                        success = false,
                                        description = "Nem ment"
                                    )
                                )
                            )
                        }

                    }
                }

            }).build()

    }
}