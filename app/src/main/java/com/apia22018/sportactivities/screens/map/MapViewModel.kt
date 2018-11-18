package com.apia22018.sportactivities.screens.map

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.data.activities.Activities

class MapViewModel (repoActivities: ActivitiesRepository): ViewModel() {

    private val activitiesList = MediatorLiveData<List<Activities>>()

    init {
        activitiesList.addSource(repoActivities.readActivities(), activitiesList::setValue)
    }

    fun getActivities() = activitiesList

    fun getActivity(activityId: String): Activities? {
        return activitiesList.value?.find { it.activityId == activityId }
    }
}