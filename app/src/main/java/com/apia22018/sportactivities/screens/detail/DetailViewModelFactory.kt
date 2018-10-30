package com.apia22018.sportactivities.screens.detail

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.data.attendee.AttendeeRepository

class DetailViewModelFactory(private val activityId: String,  private val attendeeRepository: AttendeeRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(activityId, attendeeRepository) as T
    }
}