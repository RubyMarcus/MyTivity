package com.apia22018.sportactivities.data.location

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*

class LocationLiveData : LiveData<List<Location>>() {
    private val reference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("locations")

    private val eventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot.children.mapNotNull {
                it.getValue(Location::class.java).apply {
                    this!!.activityId = it.key!!
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            System.out.println(databaseError)
        }
    }

    override fun onActive() {
        reference.addValueEventListener(eventListener)
    }

    override fun onInactive() {
        reference.removeEventListener(eventListener)
    }
}