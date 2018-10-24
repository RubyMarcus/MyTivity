package com.apia22018.sportactivities.screens.newActivities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository

class AddActivityViewModelFactory(private val activitiesRepository: ActivitiesRepository, private val attendeeRepository: AttendeeRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddActivityViewModel(activitiesRepository, attendeeRepository) as T
    }
}