package com.apia22018.sportactivities.screens.message

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.messages.Message
import com.apia22018.sportactivities.data.messages.MessageRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent

class MessageViewModel(private val messageRepository: MessageRepository, private val activityId: String) : ViewModel() {
    private val messagesLiveData: LiveData<List<Message>> = messageRepository.getMessages(activityId)
    val createMessage = SingleLiveEvent<Boolean>()

    fun getMessages() = messagesLiveData

    fun onSendMessageClicked() {
        this.createMessage.postValue(true)
    }

    fun postMessage(text: String, userName: String) {
        if (text.isNotEmpty() && userName.isNotEmpty()) {
            val message = Message(text, userName)
            messageRepository.createMessage(activityId, message)
        } else {
            //TODO(DISPLAY SNACKBAR MESSAGE WITH SOME ERROR``!??!)
        }
    }

    fun deleteMessage(){
        messageRepository.deleteMessage(activityId, Message())
    }

}