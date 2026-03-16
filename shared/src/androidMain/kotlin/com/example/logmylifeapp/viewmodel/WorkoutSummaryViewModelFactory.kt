package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WorkoutSummaryViewModelFactory (
    private val sessionId: Long,

) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WorkoutSummaryViewModel::class.java)){
            return WorkoutSummaryViewModel(sessionId = sessionId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
