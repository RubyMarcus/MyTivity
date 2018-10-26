package com.apia22018.sportactivities.data.activities

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivitiesRepository {
    private val referenceActivity: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("activities")

    companion object {
        @Volatile
        private var instance: ActivitiesRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: ActivitiesRepository().also { instance = it }
                }
    }

    fun readActivities(): LiveData<List<Activities>> {
        return ActivitiesLiveData(referenceActivity)
    }

    fun readActivity(activityId: String): LiveData<Activities> {
        return ActivityLiveData(referenceActivity.child(activityId))
    }

    fun insertActivity(activity: Activities, complete: (String) -> Unit = {}) {
        val key = referenceActivity.push().key
        key?.let { generatedKey ->
            referenceActivity.child(generatedKey).setValue(activity)
                    .addOnCompleteListener {
                        complete(generatedKey)
                    }
                    .addOnFailureListener {
                        complete("")
                    }

        }
    }

    fun updateActivityAttendees(activityId: String, value: Int, complete: (Boolean) -> Unit = {}) {
        referenceActivity.child(activityId).child("occupiedSeats").setValue(value)
                .addOnSuccessListener {
                    complete(true)
                }
                .addOnFailureListener {
                    complete(false)
                }
    }
}