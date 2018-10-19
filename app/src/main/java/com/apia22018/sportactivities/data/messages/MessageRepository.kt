package com.apia22018.sportactivities.data.messages

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*

class MessageRepository {

    private val reference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("messages")

    companion object {
        @Volatile
        private var instance: MessageRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: MessageRepository().also { instance = it }
                }
    }

    fun getMessages(activityId: String): LiveData<List<Message>> {
        return MessageLiveData(reference.child(activityId))
    }

}