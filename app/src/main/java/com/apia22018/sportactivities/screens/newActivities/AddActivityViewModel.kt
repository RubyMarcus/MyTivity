package com.apia22018.sportactivities.screens.newActivities

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Address
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.listActivities.ActivitiesRepository
import com.apia22018.sportactivities.data.location.Location
import com.apia22018.sportactivities.data.location.LocationRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent

class AddActivityViewModel(private val repoActivity : ActivitiesRepository, val repolocation: LocationRepository, val repoAttendee: AttendeeRepository) : ViewModel() {

    val showPlacePickerDialog = SingleLiveEvent<Boolean>()
    val showDatePickerDialog = SingleLiveEvent<Boolean>()
    val showTimePickerDialog = SingleLiveEvent<Boolean>()

    val place = MutableLiveData<List<Address>>()
    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()

    fun insertActivity(activity : Activities) {
        repoActivity.insertActivity(activity)
    }

    fun insertLocation(location: Location) {
        repolocation.insertLocation(location, repoActivity.currentFbKey)
    }

    fun insertAttendee(attendee: Attendee) {
        repoAttendee.insertAttendees(attendee, repoActivity.currentFbKey)
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