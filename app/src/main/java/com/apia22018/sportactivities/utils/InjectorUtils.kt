package com.apia22018.sportactivities.utils

import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.data.messages.MessageRepository
import com.apia22018.sportactivities.screens.detailActivities.DetailActivitiesViewModelFactory
import com.apia22018.sportactivities.screens.listActivities.ActivitiesViewModelFactory
import com.apia22018.sportactivities.screens.newActivity.AddActivityViewModelFactory

object InjectorUtils {

    private fun getLocationRepository() = LocationRepository.getInstance()

    private fun getActivitiesRepository() = ActivitiesRepository.getInstance()

    private fun getAttendeeRepository() = AttendeeRepository.getInstance()

    private fun getMessageRepository() = MessageRepository.getInstance()

    fun provideAddActivityViewModelFactorty() = AddActivityViewModelFactory(getActivitiesRepository(), getLocationRepository())

    fun provideActivitiesViewModelFactory() = ActivitiesViewModelFactory(getActivitiesRepository())

    fun provideDetailActivitiesViewModelFactory(activities: Activities) = DetailActivitiesViewModelFactory(getAttendeeRepository(), getMessageRepository(), getLocationRepository(), activities)
}