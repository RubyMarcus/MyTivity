package com.apia22018.sportactivities.utils

import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.data.messages.MessageRepository
import com.apia22018.sportactivities.screens.listActivities.ActivitiesViewModelFactory
import com.apia22018.sportactivities.screens.newActivity.AddActivityViewModelFactory
import com.apia22018.sportactivities.screens.message.MessageViewModelFactory

object InjectorUtils {

    private fun getLocationRepository() = LocationRepository.getInstance()

    private fun getActivitiesRepository() = ActivitiesRepository.getInstance()

    private fun getAttendeeRepository() = AttendeeRepository.getInstance()

    private fun getMessageRepository() = MessageRepository.getInstance()

    fun provideAddActivityViewModelFactorty() = AddActivityViewModelFactory(getActivitiesRepository(), getLocationRepository(), getAttendeeRepository())

    fun provideActivitiesViewModelFactory() = ActivitiesViewModelFactory(getActivitiesRepository())

    fun provideMessageViewModelFactory(activity: Activities) = MessageViewModelFactory(getMessageRepository(), activity)
}
