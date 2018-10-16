package com.apia22018.sportactivities.data.listActivities

import android.arch.lifecycle.LiveData

class ActivitiesRepository {

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
}