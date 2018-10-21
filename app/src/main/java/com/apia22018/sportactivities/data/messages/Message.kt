package com.apia22018.sportactivities.data.messages

import com.google.firebase.database.Exclude

data class Message(
        val text: String = "",
        val userName: String = ""
) {
    @set:Exclude @get:Exclude var messageId: String = ""
}