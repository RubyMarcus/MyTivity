package com.apia22018.sportactivities.utils

import android.content.Context
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.screens.listActivities.ActivitiesViewModelFactory

object InjectorUtils {

    private fun getLocationRepository(): LocationRepository {
        return LocationRepository.getInstance()
    }

    private fun getActivitiesRepository(): ActivitiesRepository {
        return ActivitiesRepository.getInstance()
    }

    fun provideActivitesViewModelFactory() = ActivitiesViewModelFactory(getActivitiesRepository())

}