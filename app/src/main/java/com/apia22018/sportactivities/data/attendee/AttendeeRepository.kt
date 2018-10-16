package com.apia22018.sportactivities.data.attendee

import com.apia22018.sportactivities.data.location.Location
import com.apia22018.sportactivities.data.location.LocationRepository
import com.google.firebase.database.*

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

    fun getAttendees(activityId: String): List<Attendee>? {
        var attendees: List<Attendee>? = null

        reference.child(activityId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                attendees = dataSnapshot.children.mapNotNull {
                    it.getValue(Attendee::class.java)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        return attendees
    }

}