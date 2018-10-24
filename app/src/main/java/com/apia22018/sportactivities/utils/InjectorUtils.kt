package com.apia22018.sportactivities.utils

import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository
import com.apia22018.sportactivities.data.messages.MessageRepository
import com.apia22018.sportactivities.screens.listActivities.ActivitiesViewModelFactory
import com.apia22018.sportactivities.screens.mapActivities.MapViewModelFactory
import com.apia22018.sportactivities.screens.newActivities.AddActivityViewModelFactory
import com.apia22018.sportactivities.screens.message.MessageViewModelFactory

object InjectorUtils {
    private fun getActivitiesRepository() = ActivitiesRepository.getInstance()

    private fun getAttendeeRepository() = AttendeeRepository.getInstance()

    private fun getMessageRepository() = MessageRepository.getInstance()

    fun provideAddActivityViewModelFactory() = AddActivityViewModelFactory(getActivitiesRepository(), getAttendeeRepository())

    fun provideActivitiesViewModelFactory() = ActivitiesViewModelFactory(getActivitiesRepository())

    fun provideMessageViewModelFactory(activity: Activities) = MessageViewModelFactory(getMessageRepository(), activity)

    fun provideMapViewModelFactory() = MapViewModelFactory(getActivitiesRepository())
}
