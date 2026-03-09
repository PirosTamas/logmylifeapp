package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WorkoutPreviewViewModelFactory (
    private val sessionId: Int,
    private val orderIndex: Int
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(WorkoutPreviewViewModel::class.java)){
            return WorkoutPreviewViewModel(sessionId, orderIndex) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}