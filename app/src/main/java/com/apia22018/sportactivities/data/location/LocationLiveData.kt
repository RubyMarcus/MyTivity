package com.apia22018.sportactivities.data.location

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*

class LocationLiveData(private val reference: DatabaseReference) : LiveData<List<Location>>() {

    private val eventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot.children.mapNotNull {
                it.getValue(Location::class.java).apply {
                    it?.key?.let { id ->
                        this?.activityId = id
                    }
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            System.out.println(databaseError)
        }
    }

    override fun onActive() {
        reference.addValueEventListener(eventListener)
        super.onActive()
    }

    override fun onInactive() {
        reference.removeEventListener(eventListener)
        super.onInactive()
    }
}