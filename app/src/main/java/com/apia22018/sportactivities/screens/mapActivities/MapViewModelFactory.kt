package com.apia22018.sportactivities.screens.mapActivities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.location.LocationRepository

class MapViewModelFactory(private val locationRepository: LocationRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapViewModel(locationRepository) as T
    }
}