package com.apia22018.sportactivities.screens.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.data.attendee.AttendeeRepository

class DetailViewModelFactory(private val activities: Activities,
                             private val activitiesRepository: ActivitiesRepository,
                             private val attendeeRepository: AttendeeRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(activities, activitiesRepository, attendeeRepository) as T
    }
}