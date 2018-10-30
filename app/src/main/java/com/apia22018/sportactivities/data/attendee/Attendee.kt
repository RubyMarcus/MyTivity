package com.apia22018.sportactivities.data.attendee

import com.google.firebase.database.Exclude

data class Attendee(
        val uid: String = "",
        val userName: String = ""
) {
    @set:Exclude @get:Exclude var attendeeId: String = ""
}