package com.apia22018.sportactivities.screens.detailActivities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.data.messages.MessageRepository

class DetailActivitiesViewModelFactory(private val attendeeRepository: AttendeeRepository,
                                       private val messageRepository: MessageRepository,
                                       private val locationRepository: LocationRepository,
                                       private val activitie: Activities
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailActivitiesViewModel(attendeeRepository, messageRepository, locationRepository, activitie) as T
    }
}