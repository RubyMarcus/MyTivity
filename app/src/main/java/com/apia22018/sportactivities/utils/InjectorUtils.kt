package com.apia22018.sportactivities.utils

import android.content.Context
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.data.messages.MessageRepository
import com.apia22018.sportactivities.screens.detailActivities.DetailActivitiesViewModelFactory
import com.apia22018.sportactivities.screens.listActivities.ActivitiesViewModelFactory

object InjectorUtils {

    private fun getLocationRepository(): LocationRepository =  LocationRepository.getInstance()

    private fun getActivitiesRepository(): ActivitiesRepository = ActivitiesRepository.getInstance()

    private fun getAttendeeRepository() = AttendeeRepository.getInstance()

    private fun getMessageRepository() = MessageRepository.getInstance()

    fun provideActivitiesViewModelFactory() = ActivitiesViewModelFactory(getActivitiesRepository())

    fun provideDetailActivitiesViewModelFactory() = DetailActivitiesViewModelFactory(getAttendeeRepository(), getMessageRepository(), getLocationRepository())
}