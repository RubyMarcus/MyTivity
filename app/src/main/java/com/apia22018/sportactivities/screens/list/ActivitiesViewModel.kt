package com.apia22018.sportactivities.screens.list

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository

class ActivitiesViewModel(activitiesRepository: ActivitiesRepository): ViewModel() {
    private val activitiesList = MediatorLiveData<List<Activities>>()

    init {
        activitiesList.addSource(activitiesRepository.readActivities(), activitiesList::setValue)
    }

    fun getActivities() = activitiesList
}