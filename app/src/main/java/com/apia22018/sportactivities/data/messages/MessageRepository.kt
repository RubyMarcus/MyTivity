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

    fun createMessage(activityId: String, message: Message) {
        val key = reference.push().key
        key?.apply {
            reference.child(activityId).child(this).setValue(message)
        }
    }

    fun deleteMessage(activityId: String, message: Message) {
        reference.child(activityId).child(message.messageId).removeValue()
    }
}