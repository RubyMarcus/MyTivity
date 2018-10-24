package com.apia22018.sportactivities.screens.detailActivity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.listActivities.Activities

class DetailViewModelFactory(private val activities: Activities) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailViewModel(activities) as T
    }
}