package com.apia22018.sportactivities.data.listActivities

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class ActivitiesRepository {

    companion object {
        @Volatile private var instance: ActivitiesRepository? = null

        fun getInstance() =
                instance ?: synchronized(this) {
                    instance ?: ActivitiesRepository().also { instance = it }
                }
    }

    fun get(): LiveData<List<Activities>> {
        val list = MutableLiveData<List<Activities>>()
        val activities = mutableListOf<Activities>()

        activities.add(Activities("1","Football", "We are going to have fun", 13, 1, 2398472398472398472))
        activities.add(Activities("2", "Dancing", "Night out on the town", 5, 2, 34334656556))
        activities.add(Activities("3", "Hiking", "Forest walk with some new friends", 4, 1, 304239402349))
        activities.add(Activities("4","Football", "We are going to have fun", 13, 1, 2398472398472398472))
        activities.add(Activities("5", "Dancing", "Night out on the town", 5, 2, 34334656556))
        activities.add(Activities("6", "Hiking", "Forest walk with some new friends", 4, 1, 304239402349))
        activities.add(Activities("7","Football", "We are going to have fun", 13, 1, 2398472398472398472))
        activities.add(Activities("8", "Dancing", "Night out on the town", 5, 2, 34334656556))
        activities.add(Activities("9", "Hiking", "Forest walk with some new friends", 4, 1, 304239402349))
        activities.add(Activities("10","Football", "We are going to have fun", 13, 1, 2398472398472398472))
        activities.add(Activities("11", "Dancing", "Night out on the town", 5, 2, 34334656556))
        activities.add(Activities("12", "Hiking", "Forest walk with some new friends", 4, 1, 304239402349))

        list.postValue(activities)
        return list
    }

}