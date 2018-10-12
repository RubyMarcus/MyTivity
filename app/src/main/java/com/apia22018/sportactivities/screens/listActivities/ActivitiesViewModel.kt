package com.apia22018.sportactivities.screens.listActivities

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository

class ActivitiesViewModel(private val activitiesRepository: ActivitiesRepository): ViewModel() {

    private val activitiesList = MediatorLiveData<List<Activities>>()

    init {
        activitiesList.addSource(activitiesRepository.readActivities(), activitiesList::setValue)
    }

    fun getActivities() = activitiesList

}