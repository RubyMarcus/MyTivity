package com.apia22018.sportactivities.screens.attendee

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.attendee.AttendeeRepository

class AttendeeViewModelFactory(private val attendeeRepository: AttendeeRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AttendeeViewModelFactory(attendeeRepository) as T
    }
}