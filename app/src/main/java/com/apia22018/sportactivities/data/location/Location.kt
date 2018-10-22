package com.apia22018.sportactivities.data.location

import com.google.firebase.database.Exclude

data class Location(
        val lat: Double = 0.0,
        val long: Double = 0.0
) {
    @set:Exclude @get:Exclude var activityId: String= ""
}