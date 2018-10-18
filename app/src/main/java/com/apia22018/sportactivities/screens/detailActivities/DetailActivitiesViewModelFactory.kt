package com.apia22018.sportactivities.screens.detailActivities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.data.messages.MessageRepository

class DetailActivitiesViewModelFactory(private val locationRepository: LocationRepository,
                                       private val activities: Activities
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailActivitiesViewModel(locationRepository, activities) as T
    }
}