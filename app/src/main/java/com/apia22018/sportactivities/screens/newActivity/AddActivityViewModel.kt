package com.apia22018.sportactivities.screens.newActivity

import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent

class AddActivityViewModel(private val repoActivity : ActivitiesRepository, val repolocation: LocationRepository) : ViewModel() {

    val showPlacePickerDialog = SingleLiveEvent<Boolean>()
    val showDatePickerDialog = SingleLiveEvent<Boolean>()
    val showTimePickerDialog = SingleLiveEvent<Boolean>()

    fun insertActivity(activity : Activities) {
        repoActivity.insertActivity(activity)
    }

    fun showCreatePlacePickerDialog() {
        showPlacePickerDialog.postValue(true)
    }

    fun showCreateDatePickerDialog() {
        showDatePickerDialog.postValue(true)
    }

    fun showCreateTimePickerDialog() {
        showTimePickerDialog.postValue(true)
    }

}