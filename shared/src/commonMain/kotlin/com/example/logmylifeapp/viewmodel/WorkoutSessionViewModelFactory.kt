package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WorkoutSessionViewModelFactory (
    private val sessionId: Long,
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WorkoutSessionViewModel::class.java)){
            return WorkoutSessionViewModel(sessionId = sessionId.toInt()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}