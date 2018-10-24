package com.apia22018.sportactivities.screens.message

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.messages.MessageRepository

class MessageViewModelFactory(private val messageRepository: MessageRepository, private val activity: Activities) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MessageViewModel(messageRepository, activity) as T
    }
}