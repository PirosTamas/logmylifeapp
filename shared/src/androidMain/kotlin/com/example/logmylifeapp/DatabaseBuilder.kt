package com.example.logmylifeapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import com.example.logmylifeapp.model.WorkoutPlanStretchCrossRef
import com.example.logmylifeapp.model.WorkoutPlanWarmupCrossRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun buildDatabase(context: Context): LogMyLifeDatabase {
    context.applicationContext.deleteDatabase("logmylife.db")
    return Room.databaseBuilder(context, LogMyLifeDatabase::class.java, "logmylife.db")
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val database = Graph.database
                    val questionDao = database.dailyLifeDataQuestionDao()
                    val answerDao = database.dailyLifeDataAnswerDao()
                    val workoutPlanDao = database.workoutPlanDao()
                    val workoutExerciseDao = database.workoutExerciseDao()
                    val crossRefDao = database.workoutPlanExerciseCrossRefDao()
                    val warmupCrossRefDao = database.workoutPlanWarmupCrossRefDao()
                    val stretchCrossRefDao = database.workoutPlanStretchCrossRefDao()
                    val workoutSessionDao = database.workoutSessionDao()
                    val workoutExerciseLogDao = database.workoutExerciseLogDao()
                    val workoutExerciseSetLogDao = database.workoutExerciseSetLogDao()

                    val initialQuestionIds = questionDao.addQuestions(InitialData.questions())
                    questionDao.addQuestions(DummyData.questions())

                    val moodQuestionId = initialQuestionIds.first().toInt()
                    answerDao.addAnswers(InitialData.answers(moodQuestionId))

                    val initialWorkoutIds = workoutPlanDao.addWorkoutPlans(DummyData.workoutPlans())
                    val initialWorkoutPlanId = initialWorkoutIds.first().toInt()

                    val exerciseIds = workoutExerciseDao.addWorkoutExercises(DummyData.workoutExercises())
                    val warmupExerciseIds = workoutExerciseDao.addWorkoutExercises(DummyData.warmupExercises())
                    val stretchExerciseIds = workoutExerciseDao.addWorkoutExercises(DummyData.stretchExercises())

                    exerciseIds.forEachIndexed { index, exerciseId ->
                        crossRefDao.addWorkoutPlanExerciseCrossRef(
                            WorkoutPlanExerciseCrossRef(planId = initialWorkoutPlanId, exerciseId = exerciseId.toInt(), orderInWorkout = index)
                        )
                    }
                    warmupExerciseIds.forEachIndexed { index, exerciseId ->
                        warmupCrossRefDao.addWorkoutPlanWarmupCrossRef(
                            WorkoutPlanWarmupCrossRef(planId = initialWorkoutPlanId, exerciseId = exerciseId.toInt(), orderInWarmup = index)
                        )
                    }
                    stretchExerciseIds.forEachIndexed { index, exerciseId ->
                        stretchCrossRefDao.addWorkoutPlanStretchCrossRef(
                            WorkoutPlanStretchCrossRef(planId = initialWorkoutPlanId, exerciseId = exerciseId.toInt(), orderInStretch = index)
                        )
                    }

                    val workoutSessionId = workoutSessionDao.addWorkoutSessions(
                        DummyData.workoutSessions(initialWorkoutPlanId)
                    ).first()

                    exerciseIds.forEach { exerciseId ->
                        val workoutExerciseLogId = workoutExerciseLogDao.addWorkoutExerciseLog(
                            WorkoutExerciseLog(sessionId = workoutSessionId.toInt(), exerciseId = exerciseId.toInt())
                        )
                        workoutExerciseSetLogDao.addWorkoutExerciseSetLogs(
                            listOf(
                                WorkoutExerciseSetLog(exerciseLogId = workoutExerciseLogId.toInt(), order = 0, targetReps = 10, completedReps = 10, weight = 40f, success = true, description = "SUCCESS"),
                                WorkoutExerciseSetLog(exerciseLogId = workoutExerciseLogId.toInt(), order = 1, targetReps = 10, completedReps = 10, weight = 50f, success = true, description = "SUCCESS"),
                                WorkoutExerciseSetLog(exerciseLogId = workoutExerciseLogId.toInt(), order = 2, targetReps = 10, completedReps = 8, weight = 60f, success = false, description = "Nem ment")
                            )
                        )
                    }
                }
            }
        })
        .build()
}
