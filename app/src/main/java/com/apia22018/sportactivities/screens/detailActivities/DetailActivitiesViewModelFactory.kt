package com.apia22018.sportactivities.screens.detailActivities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class DetailActivitiesViewModelFactory(private val hello: String): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailActivitiesViewModel(hello) as T
    }
}