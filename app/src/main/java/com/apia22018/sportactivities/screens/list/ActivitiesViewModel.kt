package com.apia22018.sportactivities.screens.list

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository

class ActivitiesViewModel(private val activitiesRepository: ActivitiesRepository): ViewModel() {

    private val activitiesList = MediatorLiveData<List<Activities>>()
    val isLoading: ObservableBoolean = ObservableBoolean(true)

    init {
        activitiesList.addSource(activitiesRepository.readActivities(), activitiesList::setValue)
    }

    fun getActivities() = activitiesList

    fun stopLoading() {
        isLoading.set(false)
    }
}