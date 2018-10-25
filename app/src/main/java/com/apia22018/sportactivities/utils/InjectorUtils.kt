package com.apia22018.sportactivities.utils

import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.data.messages.MessageRepository
import com.apia22018.sportactivities.screens.list.ActivitiesViewModelFactory
import com.apia22018.sportactivities.screens.map.MapViewModelFactory
import com.apia22018.sportactivities.screens.add.AddViewModelFactory
import com.apia22018.sportactivities.screens.attendee.AttendeeViewModelFactory
import com.apia22018.sportactivities.screens.detail.DetailViewModelFactory
import com.apia22018.sportactivities.screens.message.MessageViewModelFactory

object InjectorUtils {
    private fun getActivitiesRepository() = ActivitiesRepository.getInstance()

    private fun getAttendeeRepository() = AttendeeRepository.getInstance()

    private fun getMessageRepository() = MessageRepository.getInstance()

    fun provideAddActivityViewModelFactory() = AddViewModelFactory(getActivitiesRepository(), getAttendeeRepository())

    fun provideActivitiesViewModelFactory() = ActivitiesViewModelFactory(getActivitiesRepository())

    fun provideMessageViewModelFactory(activity: Activities) = MessageViewModelFactory(getMessageRepository(), activity)

    fun provideMapViewModelFactory() = MapViewModelFactory(getActivitiesRepository())

    fun provideDetailViewModelFactory(activityId: String) = DetailViewModelFactory(activityId, getActivitiesRepository())

    fun provideAttendeeViewModelFactory() = AttendeeViewModelFactory(getAttendeeRepository())

}
