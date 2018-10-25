package com.apia22018.sportactivities.screens.attendee

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository

class AttendeeViewModel(private val attendeeRepository: AttendeeRepository, private val activity: Activities) : ViewModel () {

    private val attendeeLiveData : LiveData<List<Attendee>> = attendeeRepository.getAttendees(activity.activityId)

    fun getAttendees() = attendeeLiveData
}