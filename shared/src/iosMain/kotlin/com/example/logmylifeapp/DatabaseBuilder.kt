package com.example.logmylifeapp

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.model.WorkoutExerciseLog
import com.example.logmylifeapp.model.WorkoutExerciseSetLog
import com.example.logmylifeapp.model.WorkoutPlanExerciseCrossRef
import com.example.logmylifeapp.model.WorkoutPlanStretchCrossRef
import com.example.logmylifeapp.model.WorkoutPlanWarmupCrossRef
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import platform.Foundation.NSHomeDirectory

fun buildDatabase(): LogMyLifeDatabase {
    val dbPath = NSHomeDirectory() + "/Documents/logmylife.db"
    val db = Room.databaseBuilder<LogMyLifeDatabase>(name = dbPath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.Default)
        .build()

    CoroutineScope(Dispatchers.Default).launch {
        val plans = db.workoutPlanDao().getAllWorkoutPlans().first()
        if (plans.isEmpty()) {
            seedDatabase(db)
        }
    }

    return db
}

private suspend fun seedDatabase(db: LogMyLifeDatabase) {
    val questionDao = db.dailyLifeDataQuestionDao()
    val answerDao = db.dailyLifeDataAnswerDao()
    val workoutPlanDao = db.workoutPlanDao()
    val workoutExerciseDao = db.workoutExerciseDao()
    val crossRefDao = db.workoutPlanExerciseCrossRefDao()
    val warmupCrossRefDao = db.workoutPlanWarmupCrossRefDao()
    val stretchCrossRefDao = db.workoutPlanStretchCrossRefDao()
    val workoutSessionDao = db.workoutSessionDao()
    val workoutExerciseLogDao = db.workoutExerciseLogDao()
    val workoutExerciseSetLogDao = db.workoutExerciseSetLogDao()

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
