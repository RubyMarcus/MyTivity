package com.apia22018.sportactivities.screens.add

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.activities.ActivitiesRepository

class AddViewModelFactory(private val activitiesRepository: ActivitiesRepository, private val attendeeRepository: AttendeeRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddViewModel(activitiesRepository, attendeeRepository) as T
    }
}