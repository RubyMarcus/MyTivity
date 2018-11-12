package com.apia22018.sportactivities.screens.message

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.BaseObservable
import android.databinding.Bindable
import com.apia22018.sportactivities.BR
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.messages.Message
import com.apia22018.sportactivities.data.messages.MessageRepository
import com.apia22018.sportactivities.utils.ObservableViewModel
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth

class MessageViewModel(private val messageRepository: MessageRepository, private val activity: Activities) : ObservableViewModel() {
    private val messagesLiveData: LiveData<List<Message>> = messageRepository.getMessages(activity.activityId)
    private val user = FirebaseAuth.getInstance().currentUser

    var inputText: String = ""
        @Bindable get() {
            return field
        }
        set(value) {
            if (field == value) return
            field = value
            notifyPropertyChanged(BR.inputText)
        }

    fun getMessages() = messagesLiveData

    fun onSendMessageClicked() {
        postMessage()
    }

    private fun postMessage() {
        if (inputText.isNotEmpty()) user?.email?.let { email ->
            val message = Message(inputText, email)
            messageRepository.createMessage(activity.activityId, message)
            inputText = ""
        } else {
            //TODO(DISPLAY SNACKBAR MESSAGE WITH SOME ERROR``!??!)
        }
    }

    fun deleteMessage(message: Message) {
        messageRepository.deleteMessage(activity.activityId, message)
    }

    fun canDelete(): Boolean {
        return activity.createdBy == user?.uid
    }
}