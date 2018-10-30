package com.apia22018.sportactivities.screens.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.google.firebase.auth.FirebaseAuth

class DetailViewModel(private val activityId: String, private val attendeeRepository: AttendeeRepository) : ViewModel() {

    private val attendeeLiveData : LiveData<List<Attendee>> = attendeeRepository.getAttendees(activityId)
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    fun getAttendees() = attendeeLiveData

    fun deleteAttendee(attendee: Attendee) = attendeeRepository.deleteMessage(activityId, attendee)

    fun canDelete(userAttendeeUID: String): Int = if (currentUserUid == userAttendeeUID) VISIBLE else GONE
}