package com.apia22018.sportactivities.data.listActivities

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivitiesRepository {

    private val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference("activities")

    companion object {
        @Volatile private var instance: ActivitiesRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: ActivitiesRepository().also { instance = it }
                }
    }

    fun readActivities(): LiveData<List<Activities>>{
        return ActivitiesLiveData()
    }

    fun insertActivity (activity: Activities) {
        val key = reference.push().key
        key?.let {
            activity.activityId = it
            reference.child(it).setValue(activity)
        }
    }
}