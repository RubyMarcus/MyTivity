package com.apia22018.sportactivities.data.messages

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class MessageLiveData(private val reference: DatabaseReference) : LiveData<List<Message>>() {
    private val eventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot.children.mapNotNull {
                Message(it.value as String, it.key as String)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            println(databaseError.message)
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