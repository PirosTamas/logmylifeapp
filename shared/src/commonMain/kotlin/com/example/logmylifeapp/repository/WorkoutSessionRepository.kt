package com.example.logmylifeapp.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.logmylifeapp.data.LogMyLifeDatabase
import com.example.logmylifeapp.data.localDateAdapter
import com.example.logmylifeapp.data.toModel
import com.example.logmylifeapp.model.WorkoutSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

class WorkoutSessionRepository(private val db: LogMyLifeDatabase) {

    suspend fun addWorkoutSession(planId: Int): Long = withContext(Dispatchers.Default) {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        db.workoutSessionQueries.insertWorkoutSession(
            planId = planId,
            date = localDateAdapter.encode(today),
            completed = false
        )
        db.workoutSessionQueries.lastInsertRowId().executeAsOne()
    }

    suspend fun addWorkoutSessions(sessions: List<WorkoutSession>): List<Long> = withContext(Dispatchers.Default) {
        sessions.map { session ->
            db.workoutSessionQueries.insertWorkoutSession(
                planId = session.planId,
                date = localDateAdapter.encode(session.date),
                completed = session.completed
            )
            db.workoutSessionQueries.lastInsertRowId().executeAsOne()
        }
    }

    suspend fun updateWorkoutSession(session: WorkoutSession) = withContext(Dispatchers.Default) {
        db.workoutSessionQueries.updateWorkoutSession(
            id = session.id,
            planId = session.planId,
            date = localDateAdapter.encode(session.date),
            completed = session.completed
        )
    }

    suspend fun deleteWorkoutSession(session: WorkoutSession) = withContext(Dispatchers.Default) {
        db.workoutSessionQueries.deleteWorkoutSession(session.id)
    }

    suspend fun setSessionCompleted(sessionId: Int) = withContext(Dispatchers.Default) {
        db.workoutSessionQueries.setSessionCompleted(sessionId)
    }

    fun getAllWorkoutSessions(): Flow<List<WorkoutSession>> =
        db.workoutSessionQueries.getAllWorkoutSessions()
            .asFlow().mapToList(Dispatchers.Default)
            .map { list -> list.map { it.toModel() } }

    fun getWorkoutSessionById(id: Int): Flow<WorkoutSession?> =
        db.workoutSessionQueries.getWorkoutSessionById(id)
            .asFlow().mapToOneOrNull(Dispatchers.Default)
            .map { it?.toModel() }
}
