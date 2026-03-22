package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

class WorkoutSummaryViewModelFactory (
    private val sessionId: Long,

) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        if (modelClass == WorkoutSummaryViewModel::class) {
            return WorkoutSummaryViewModel(sessionId = sessionId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
