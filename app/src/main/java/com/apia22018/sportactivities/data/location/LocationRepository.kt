package com.apia22018.sportactivities.data.location

import android.arch.lifecycle.LiveData
import com.google.firebase.database.*

class LocationRepository {
    private val reference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("locations")

    companion object {
        @Volatile private var instance: LocationRepository? = null

        fun getInstance() =
                instance ?: synchronized(this){
                    instance ?: LocationRepository().also { instance = it }
                }
    }

    // If you only want one location then provide the function with ID
    fun readLocations(activityId: String = ""): LiveData<List<Location>>{
        return LocationLiveData(reference.child(activityId))
    }

}