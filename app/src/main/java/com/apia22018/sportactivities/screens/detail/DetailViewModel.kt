package com.apia22018.sportactivities.screens.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.Bindable
import android.view.View
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.google.firebase.auth.FirebaseAuth

class DetailViewModel(private val activityId: String, private val attendeeRepository: AttendeeRepository) : ViewModel() {

    private val attendeeLiveData : LiveData<List<Attendee>> = attendeeRepository.getAttendees(activityId)

    val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    fun getAttendees() = attendeeLiveData

    fun deleteAttendee(attendee: Attendee){
        attendeeRepository.deleteMessage(activityId, attendee)
    }

    fun canDelete(attendeeUid: String): Int = if (attendeeUid == FirebaseAuth.getInstance().currentUser?.uid) View.VISIBLE else View.GONE
}