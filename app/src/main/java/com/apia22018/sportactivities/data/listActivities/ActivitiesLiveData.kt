package com.apia22018.sportactivities.data.listActivities

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*

class ActivitiesLiveData : LiveData<List<Activities>>() {
    private val reference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("activities")

    private val eventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot.children.mapNotNull {
                it.getValue(Activities::class.java).apply {
                    this!!.activityId = it.key!!
                }
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            System.out.println(databaseError.message)
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