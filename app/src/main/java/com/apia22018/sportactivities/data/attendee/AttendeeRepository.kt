package com.apia22018.sportactivities.data.attendee

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*
import kotlin.math.truncate

class AttendeeRepository {
    private val reference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("attendees")

    companion object {
        @Volatile
        private var instance: AttendeeRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: AttendeeRepository().also { instance = it }
                }
    }

    fun getAttendees(activityId: String): LiveData<List<Attendee>> {
        return AttendeeLiveData(reference.child(activityId))
    }

    fun insertAttendees(attendee: Attendee, activityId: String) {
        reference.child(activityId).setValue(attendee)
    }

}