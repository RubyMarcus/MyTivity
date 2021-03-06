package com.apia22018.sportactivities.data.attendee

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*

class AttendeeLiveData (private val reference: DatabaseReference) : LiveData<List<Attendee>>() {
    private val eventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot.children.mapNotNull {
                it.getValue(Attendee::class.java).apply {
                    it?.key?.let { id ->
                        this?.attendeeId = id
                    }
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            println(databaseError.message)
        }

    }

    override fun onActive() {
        reference.addValueEventListener(eventListener)
    }

    override fun onInactive() {
        reference.removeEventListener(eventListener)
    }
}