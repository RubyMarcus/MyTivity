package com.apia22018.sportactivities.screens.mapActivities

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.location.Location
import com.apia22018.sportactivities.data.location.LocationRepository

class MapViewModel (private val repoLocation : LocationRepository): ViewModel() {

    private val locationList = MediatorLiveData<List<Location>>()

    init {
        locationList.addSource(repoLocation.readLocations(), locationList::setValue)
    }

    fun getLocation() = locationList
}