package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WorkoutSetViewModelFactory (
    private val sessionId: Long,
    private val exerciseLogId: Int,
    private val setIndex: Int
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WorkoutSetViewModel::class.java)){
            return WorkoutSetViewModel(sessionId = sessionId ,exerciseLogId = exerciseLogId, setIndex = setIndex) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}