package com.apia22018.sportactivities.screens.detailActivities

import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.data.messages.MessageRepository

class DetailActivitiesViewModel(private val attendeeRepository: AttendeeRepository,
                                private val messageRepository: MessageRepository,
                                private val locationRepository: LocationRepository,
                                private val activities: Activities
) : ViewModel() {

    private val attendeesList = attendeeRepository.getAttendees(activities.activityId)
    private val messageList = messageRepository.getMessages(activities.activityId)
    private val location = locationRepository.readLocations()

    init {
        println(activities.activityId)

        println(attendeesList.value)

        println(messageList.value)

        println(location.value)
    }

}