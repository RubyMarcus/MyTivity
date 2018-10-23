package com.apia22018.sportactivities.screens.message

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.messages.Message
import com.apia22018.sportactivities.data.messages.MessageRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth

class MessageViewModel(private val messageRepository: MessageRepository, private val activity: Activities) : ViewModel() {
    private val messagesLiveData: LiveData<List<Message>> = messageRepository.getMessages(activity.activityId)
    val createMessage = SingleLiveEvent<Boolean>()
    val user = FirebaseAuth.getInstance().currentUser

    fun getMessages() = messagesLiveData

    fun onSendMessageClicked() {
        this.createMessage.postValue(true)
    }

    fun postMessage(text: String, userName: String) {
        if (text.isNotEmpty() && userName.isNotEmpty()) {
            val message = Message(text, userName)
            messageRepository.createMessage(activity.activityId, message)
        } else {
            //TODO(DISPLAY SNACKBAR MESSAGE WITH SOME ERROR``!??!)
        }
    }

    fun deleteMessage(message: Message){
        messageRepository.deleteMessage(activity.activityId, message)
    }

    fun canDelete(): Boolean {
        return activity.createdBy == user?.uid
    }
}