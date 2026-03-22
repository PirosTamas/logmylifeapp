package com.example.logmylifeapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

class WorkoutSessionViewModelFactory (
    private val sessionId: Long,
) : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        if (modelClass == WorkoutSessionViewModel::class) {
            return WorkoutSessionViewModel(sessionId = sessionId.toInt()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
