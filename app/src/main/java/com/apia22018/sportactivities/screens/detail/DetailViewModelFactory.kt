package com.apia22018.sportactivities.screens.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.activities.ActivitiesRepository

class DetailViewModelFactory(private val activityId: String, private val activitiesRepository: ActivitiesRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(activityId, activitiesRepository) as T
    }
}