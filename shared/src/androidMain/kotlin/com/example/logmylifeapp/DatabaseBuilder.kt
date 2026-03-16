package com.example.logmylifeapp

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun buildDatabase(context: Context): DatabaseDriverFactory {
    context.applicationContext.deleteDatabase("logmylife.db")
    val factory = DatabaseDriverFactory(context)
    CoroutineScope(Dispatchers.IO).launch {
        seedData()
    }
    return factory
}

private suspend fun seedData() {
    val db = Graph.database
    db.workoutPlanQueries.transaction {
        val questionIds = InitialData.questions().map { q ->
            db.dailyLifeDataQuestionQueries.insertQuestion(
                question = q.question,
                scheduledDays = com.example.logmylifeapp.data.dayOfWeekSetAdapter.encode(q.scheduledDays),
                startDate = com.example.logmylifeapp.data.localDateAdapter.encode(q.startDate),
                predefinedAnswers = com.example.logmylifeapp.data.stringSetAdapter.encode(q.predefinedAnswers),
                customAnswerAllowed = q.customAnswerAllowed
            )
            db.dailyLifeDataQuestionQueries.lastInsertRowId().executeAsOne()
        }
        DummyData.questions().forEach { q ->
            db.dailyLifeDataQuestionQueries.insertQuestion(
                question = q.question,
                scheduledDays = com.example.logmylifeapp.data.dayOfWeekSetAdapter.encode(q.scheduledDays),
                startDate = com.example.logmylifeapp.data.localDateAdapter.encode(q.startDate),
                predefinedAnswers = com.example.logmylifeapp.data.stringSetAdapter.encode(q.predefinedAnswers),
                customAnswerAllowed = q.customAnswerAllowed
            )
        }

        val moodQuestionId = questionIds.first().toInt()
        InitialData.answers(moodQuestionId).forEach { a ->
            db.dailyLifeDataAnswerQueries.addAnswer(
                questionId = a.questionId,
                answer = a.answer,
                createdAt = com.example.logmylifeapp.data.localDateAdapter.encode(a.createdAt)
            )
        }

        DummyData.workoutPlans().forEach { plan ->
            db.workoutPlanQueries.insertWorkoutPlanGetId(
                name = plan.name,
                scheduledDays = com.example.logmylifeapp.data.dayOfWeekSetAdapter.encode(plan.scheduledDays),
                currentSession = plan.currentSession,
                numberOfSessions = plan.numberOfSessions,
                startDate = com.example.logmylifeapp.data.localDateAdapter.encode(plan.startDate)
            )
        }
        val planId = db.workoutPlanQueries.lastInsertRowId().executeAsOne().toInt()

        val exerciseIds = DummyData.workoutExercises().map { e ->
            db.workoutExerciseQueries.insertWorkoutExercise(
                name = e.name,
                description = e.description,
                equipmentNeeded = com.example.logmylifeapp.data.stringSetAdapter.encode(e.equipmentNeeded),
                predictedTimeInMinutes = e.predictedTimeInMinutes,
                illustrationResId = e.illustrationResId,
                illustrationUri = e.illustrationUri,
                numberOfSets = e.numberOfSets,
                restTimeBetweenSets = e.restTimeBetweenSets
            )
            db.workoutExerciseQueries.lastInsertRowId().executeAsOne().toInt()
        }
        val warmupIds = DummyData.warmupExercises().map { e ->
            db.workoutExerciseQueries.insertWorkoutExercise(
                name = e.name,
                description = e.description,
                equipmentNeeded = com.example.logmylifeapp.data.stringSetAdapter.encode(e.equipmentNeeded),
                predictedTimeInMinutes = e.predictedTimeInMinutes,
                illustrationResId = e.illustrationResId,
                illustrationUri = e.illustrationUri,
                numberOfSets = e.numberOfSets,
                restTimeBetweenSets = e.restTimeBetweenSets
            )
            db.workoutExerciseQueries.lastInsertRowId().executeAsOne().toInt()
        }
        val stretchIds = DummyData.stretchExercises().map { e ->
            db.workoutExerciseQueries.insertWorkoutExercise(
                name = e.name,
                description = e.description,
                equipmentNeeded = com.example.logmylifeapp.data.stringSetAdapter.encode(e.equipmentNeeded),
                predictedTimeInMinutes = e.predictedTimeInMinutes,
                illustrationResId = e.illustrationResId,
                illustrationUri = e.illustrationUri,
                numberOfSets = e.numberOfSets,
                restTimeBetweenSets = e.restTimeBetweenSets
            )
            db.workoutExerciseQueries.lastInsertRowId().executeAsOne().toInt()
        }

        exerciseIds.forEachIndexed { index, exerciseId ->
            db.workoutPlanExerciseCrossRefQueries.insertWorkoutPlanExerciseCrossRef(
                planId = planId, exerciseId = exerciseId, orderInWorkout = index
            )
        }
        warmupIds.forEachIndexed { index, exerciseId ->
            db.workoutPlanWarmupCrossRefQueries.insertWorkoutPlanWarmupCrossRef(
                planId = planId, exerciseId = exerciseId, orderInWarmup = index
            )
        }
        stretchIds.forEachIndexed { index, exerciseId ->
            db.workoutPlanStretchCrossRefQueries.insertWorkoutPlanStretchCrossRef(
                planId = planId, exerciseId = exerciseId, orderInStretch = index
            )
        }

        db.workoutSessionQueries.insertWorkoutSession(
            planId = planId,
            date = com.example.logmylifeapp.data.localDateAdapter.encode(DummyData.workoutSessions(planId).first().date),
            completed = false
        )
        val sessionId = db.workoutSessionQueries.lastInsertRowId().executeAsOne().toInt()

        exerciseIds.forEach { exerciseId ->
            db.workoutExerciseLogQueries.insertWorkoutExerciseLog(
                sessionId = sessionId,
                exerciseId = exerciseId
            )
            val logId = db.workoutExerciseLogQueries.lastInsertRowId().executeAsOne().toInt()
            db.workoutExerciseSetLogQueries.insertWorkoutExerciseSetLog(exerciseLogId = logId, order = 0, targetReps = 10, completedReps = 10, weight = 40f, success = true, description = "SUCCESS")
            db.workoutExerciseSetLogQueries.insertWorkoutExerciseSetLog(exerciseLogId = logId, order = 1, targetReps = 10, completedReps = 10, weight = 50f, success = true, description = "SUCCESS")
            db.workoutExerciseSetLogQueries.insertWorkoutExerciseSetLog(exerciseLogId = logId, order = 2, targetReps = 10, completedReps = 8, weight = 60f, success = false, description = "Nem ment")
        }
    }
}
