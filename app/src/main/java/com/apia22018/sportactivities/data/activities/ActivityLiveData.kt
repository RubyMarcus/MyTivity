package com.apia22018.sportactivities.data.activities

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ActivityLiveData(private val reference: DatabaseReference) : LiveData<Activities>() {
    private val eventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot.getValue(Activities::class.java)?.apply {
                activityId = dataSnapshot.key!!
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
