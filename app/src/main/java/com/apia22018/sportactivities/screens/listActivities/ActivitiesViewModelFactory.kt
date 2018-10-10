package com.apia22018.sportactivities.screens.listActivities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository

class ActivitiesViewModelFactory(private val activitiesRepository: ActivitiesRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ActivitiesViewModel(activitiesRepository) as T
    }
}