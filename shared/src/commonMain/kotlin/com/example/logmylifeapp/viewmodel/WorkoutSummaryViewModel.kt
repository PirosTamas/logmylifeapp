package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.logmylifeapp.Graph
import com.example.logmylifeapp.dto.WorkoutSummaryDTO
import com.example.logmylifeapp.repository.WorkoutSummaryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkoutSummaryViewModel(
    private val sessionId: Long,
    private val workoutSummaryRepository: WorkoutSummaryRepository = Graph.workoutSummaryRepository
) : ViewModel() {
    private val _workoutSummary = MutableStateFlow<WorkoutSummaryDTO?>(null)
    val workoutSummary: StateFlow<WorkoutSummaryDTO?> = _workoutSummary

    init{
        viewModelScope.launch {
            _workoutSummary.value =
                workoutSummaryRepository.getWorkoutSummaryBySessionId(sessionId)
        }
    }

}