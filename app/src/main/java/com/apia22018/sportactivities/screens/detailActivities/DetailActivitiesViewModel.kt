package com.apia22018.sportactivities.screens.detailActivities

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.location.Location
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.data.messages.MessageRepository

class DetailActivitiesViewModel(private val attendeeRepository: AttendeeRepository,
                                private val messageRepository: MessageRepository,
                                private val locationRepository: LocationRepository,
                                private val activities: Activities
) : ViewModel() {

    private val attendeesList = attendeeRepository.getAttendees(activities.activityId)
    private val messageList = messageRepository.getMessages(activities.activityId)
    private val location: MutableLiveData<Location> = MutableLiveData()

    init {
        locationRepository.readLocation(activities.activityId)?.also {
            location.postValue(it)
        }
    }

    fun getAttendees() = attendeesList

    fun getMessages() = messageList

    fun getLocation() = location

}