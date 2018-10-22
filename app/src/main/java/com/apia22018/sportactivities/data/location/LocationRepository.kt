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

    fun readLocations(): LiveData<List<Location>>{
        return LocationLiveData(reference)
    }

    fun readLocation(activityId: String): Location? {
        var location: Location? = null
        reference.child(activityId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                location = dataSnapshot.getValue(Location::class.java)
                println(location)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println(databaseError.message)
            }
        })
        return location
    }

    fun insertLocation(location: Location, activityId: String) {
        reference.child(activityId).setValue(location)
    }

}