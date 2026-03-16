package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.localDateAdapter
import com.example.logmylifeapp.data.dayOfWeekSetAdapter
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.WorkoutPlan
import com.example.logmylifeapp.model.WorkoutPlanWithExercises
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class WorkoutPlanRepository(private val db: LogMyLifeDatabase) {

    suspend fun addWorkoutPlan(workoutPlan: WorkoutPlan) = withContext(Dispatchers.Default) {
        db.workoutPlanQueries.addWorkoutPlan(
            name = workoutPlan.name,
            scheduledDays = dayOfWeekSetAdapter.encode(workoutPlan.scheduledDays),
            currentSession = workoutPlan.currentSession,
            numberOfSessions = workoutPlan.numberOfSessions,
            startDate = localDateAdapter.encode(workoutPlan.startDate)
        )
    }

    suspend fun insertWorkoutPlanGetId(workoutPlan: WorkoutPlan): Long = withContext(Dispatchers.Default) {
        db.workoutPlanQueries.insertWorkoutPlanGetId(
            name = workoutPlan.name,
            scheduledDays = dayOfWeekSetAdapter.encode(workoutPlan.scheduledDays),
            currentSession = workoutPlan.currentSession,
            numberOfSessions = workoutPlan.numberOfSessions,
            startDate = localDateAdapter.encode(workoutPlan.startDate)
        )
        db.workoutPlanQueries.lastInsertRowId().executeAsOne()
    }

    suspend fun addWorkoutPlans(workoutPlans: List<WorkoutPlan>): List<Long> = withContext(Dispatchers.Default) {
        workoutPlans.map { plan ->
            db.workoutPlanQueries.insertWorkoutPlanGetId(
                name = plan.name,
                scheduledDays = dayOfWeekSetAdapter.encode(plan.scheduledDays),
                currentSession = plan.currentSession,
                numberOfSessions = plan.numberOfSessions,
                startDate = localDateAdapter.encode(plan.startDate)
            )
            db.workoutPlanQueries.lastInsertRowId().executeAsOne()
        }
    }

    suspend fun updateWorkoutPlan(workoutPlan: WorkoutPlan) = withContext(Dispatchers.Default) {
        db.workoutPlanQueries.updateWorkoutPlan(
            id = workoutPlan.id,
            name = workoutPlan.name,
            scheduledDays = dayOfWeekSetAdapter.encode(workoutPlan.scheduledDays),
            currentSession = workoutPlan.currentSession,
            numberOfSessions = workoutPlan.numberOfSessions,
            startDate = localDateAdapter.encode(workoutPlan.startDate)
        )
    }

    suspend fun deleteWorkoutPlan(workoutPlan: WorkoutPlan) = withContext(Dispatchers.Default) {
        db.workoutPlanQueries.deleteWorkoutPlan(workoutPlan.id)
    }

    fun getAllWorkoutPlans(): Flow<List<WorkoutPlan>> =
        db.workoutPlanQueries.getAllWorkoutPlans()
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }

    fun getWorkoutPlanById(id: Int): Flow<WorkoutPlan?> =
        db.workoutPlanQueries.getWorkoutPlanById(id)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it?.toModel() }

    fun getWorkoutPlanForToday(): Flow<List<WorkoutPlan>> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).dayOfWeek.name
        return db.workoutPlanQueries.getWorkoutPlanForDay(today)
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }
    }

    fun getWorkoutPlanWithExercises(planId: Int): Flow<WorkoutPlanWithExercises> {
        val planFlow = db.workoutPlanQueries.getWorkoutPlanById(planId)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
        val mainFlow = db.workoutExerciseQueries.getMainExercisesForPlan(planId)
            .asFlow().mapToList(Dispatchers.Default)
        val warmupFlow = db.workoutExerciseQueries.getWarmupsForPlan(planId)
            .asFlow().mapToList(Dispatchers.Default)
        val stretchFlow = db.workoutExerciseQueries.getStretchesForPlan(planId)
            .asFlow().mapToList(Dispatchers.Default)
        return combine(planFlow, mainFlow, warmupFlow, stretchFlow) { plan, main, warmup, stretch ->
            WorkoutPlanWithExercises(
                plan = plan!!.toModel(),
                mainExercises = main.map { it.toModel() },
                warmups = warmup.map { it.toModel() },
                stretches = stretch.map { it.toModel() }
            )
        }
    }

    suspend fun getWorkoutPlanBySessionId(sessionId: Int): WorkoutPlan? = withContext(Dispatchers.Default) {
        db.workoutPlanQueries.getWorkoutPlanBySessionId(sessionId).executeAsOneOrNull()?.toModel()
    }
}
