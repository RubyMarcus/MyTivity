package com.apia22018.sportactivities.screens.add

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Address
import android.view.View
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.apia22018.sportactivities.utils.showSnackbar

class AddViewModel(private val repoActivity : ActivitiesRepository, val repoAttendee: AttendeeRepository) : ViewModel() {

    val showPlacePickerDialog = SingleLiveEvent<Boolean>()
    val showDatePickerDialog = SingleLiveEvent<Boolean>()
    val showTimePickerDialog = SingleLiveEvent<Boolean>()

    val place = MutableLiveData<List<Address>>()
    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()

    var activityId : String = ""

    fun insertActivity(activity : Activities) {
        repoActivity.insertActivity(activity) {
            activityId = it
        }
    }

    fun insertAttendee(attendee: Attendee) {
        repoAttendee.insertAttendees(attendee, activityId)
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

    //Create activity here?
    fun createActivity(title : String, description : String, totalSeats : Int, view : View) {

        if (title.isEmpty()) {
            showSnackbar(view, "Fill in title")
            return
        }

        if (totalSeats.toString().isEmpty()) {
            showSnackbar(view, "Fill in totalpeople")
            return
        }

        place.let {

        }

    }

}