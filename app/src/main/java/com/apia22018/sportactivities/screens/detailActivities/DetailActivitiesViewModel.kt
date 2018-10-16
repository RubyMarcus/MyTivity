package com.apia22018.sportactivities.screens.detailActivities

import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.data.messages.MessageRepository

class DetailActivitiesViewModel(private val attendeeRepository: AttendeeRepository,
                                private val messageRepository: MessageRepository,
                                private val locationRepository: LocationRepository
) : ViewModel() {

}