package com.apia22018.sportactivities.data.messages

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

    fun getMessages(activityId: String): List<Message>? {
        var messages: List<Message>? = null

        reference.child(activityId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                messages = dataSnapshot.children.mapNotNull {
                    it.getValue(Message::class.java)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        return messages
    }

}