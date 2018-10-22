package com.apia22018.sportactivities.screens.message

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.messages.Message
import com.apia22018.sportactivities.data.messages.MessageRepository

class MessageViewModel(private val messageRepository: MessageRepository, activityId: String) : ViewModel() {
    private val messagesLiveData: LiveData<List<Message>> = messageRepository.getMessages(activityId)

    fun getMessages() = messagesLiveData

}