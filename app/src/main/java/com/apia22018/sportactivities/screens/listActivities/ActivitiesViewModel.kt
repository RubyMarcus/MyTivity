package com.apia22018.sportactivities.screens.listActivities

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository
import com.apia22018.sportactivities.data.location.LocationRepository

class ActivitiesViewModel(private val activitiesRepository: ActivitiesRepository,
                          private val locationRepository: LocationRepository): ViewModel() {

    private val activitiesList = MediatorLiveData<List<Activities>>()

    init {
        locationRepository.readLocations()
        activitiesList.addSource(activitiesRepository.readActivities(), activitiesList::setValue)
    }

    fun getActivities() = activitiesList

}