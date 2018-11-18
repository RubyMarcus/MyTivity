package com.apia22018.sportactivities.data.attendee

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*

class AttendeeRepository {
    private val reference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("attendees")

    companion object {
        @Volatile
        private var instance: AttendeeRepository? = null

        @Synchronized
        fun getInstance(): AttendeeRepository = instance
                ?: AttendeeRepository().also { instance = it }
    }

    fun getAttendees(activityId: String): LiveData<List<Attendee>> {
        return AttendeeLiveData(reference.child(activityId))
    }

    fun insertAttendees(attendee: Attendee, activityId: String) {
        val key = reference.child(activityId).push().key
        key?.apply {
            reference.child(activityId).child(this).setValue(attendee)
        }
    }

    fun deleteAttendee(activityId: String, attendee: Attendee, complete: (Boolean) -> Unit = {}) {
        reference.child(activityId).child(attendee.attendeeId).removeValue()
                .addOnSuccessListener {
                    complete(true)
                }.addOnFailureListener {
                    complete(false)
                }
    }

}