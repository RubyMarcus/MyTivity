package com.apia22018.sportactivities.data.activities

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivitiesRepository {
    private val referenceActivity: DatabaseReference = FirebaseDatabase.getInstance().getReference("activities")

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

    fun insertActivity (activity: Activities, complete: (String) -> Unit = {}) {
        val key = referenceActivity.push().key
        key?.let {
            referenceActivity.child(it).setValue(activity)
            complete(it)
        }
    }
}