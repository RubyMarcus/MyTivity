package com.apia22018.sportactivities.data.listActivities

import android.arch.lifecycle.LiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ActivitiesRepository {

    private val reference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .getReference("activities")

    companion object {
        @Volatile private var instance: ActivitiesRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: ActivitiesRepository().also { instance = it }
                }
    }

    fun create(activity: Activities){
        //TODO research create method
    }

    fun readActivities(): LiveData<List<Activities>>{
        return ActivitiesLiveData()
    }

    fun update(activity: Activities){
        reference.child(activity.activityId).setValue(activity)
    }

    fun delete(activity: Activities){
        reference.child(activity.activityId).removeValue()

    }

    /*
    fun get(): LiveData<List<Activities>> {
        val list = MutableLiveData<List<Activities>>()
        val activities = mutableListOf<Activities>()

        activities.add(Activities("1","Football", "We are going to have fun", 13, 1, 1539255728))
        activities.add(Activities("2", "Dancing", "Night out on the town", 5, 2, 1539255728))
        activities.add(Activities("3", "Hiking", "Forest walk with some new friends", 4, 1, 1539255728))
        activities.add(Activities("4","Football", "We are going to have fun", 13, 1, 1539255728))
        activities.add(Activities("5", "Dancing", "Night out on the town", 5, 2, 1539255728))
        activities.add(Activities("6", "Hiking", "Forest walk with some new friends", 4, 1, 1539255728))
        activities.add(Activities("7","Football", "We are going to have fun", 13, 1, 1539255728))
        activities.add(Activities("8", "Dancing", "Night out on the town", 5, 2, 1539255728))
        activities.add(Activities("9", "Hiking", "Forest walk with some new friends", 4, 1, 1539255728))
        activities.add(Activities("10","Football", "We are going to have fun", 13, 1, 1539255728))
        activities.add(Activities("11", "Dancing", "Night out on the town", 5, 2, 1539255728))
        activities.add(Activities("12", "Hiking", "Forest walk with some new friends", 4, 1, 1539255728))

        list.postValue(activities)
        return list
    }*/

}