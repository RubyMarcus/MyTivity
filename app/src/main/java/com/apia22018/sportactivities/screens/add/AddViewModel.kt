package com.apia22018.sportactivities.screens.add

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.view.View
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.apia22018.sportactivities.utils.showSnackbar
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(private val repoActivity: ActivitiesRepository, val repoAttendee: AttendeeRepository) : ViewModel() {

    private val dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val timestampCalendar = Calendar.getInstance()

    val currentUser = FirebaseAuth.getInstance().currentUser?.uid

    val showPlacePickerDialog = SingleLiveEvent<Boolean>()
    val showDatePickerDialog = SingleLiveEvent<Boolean>()
    val showTimePickerDialog = SingleLiveEvent<Boolean>()
    val user = FirebaseAuth.getInstance().currentUser
    val place = MutableLiveData<List<Address>>()
    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()

    val eventNameError = MutableLiveData<String>()
    val descriptionError = MutableLiveData<String>()
    val locationError = MutableLiveData<String>()
    val dateError = MutableLiveData<String>()
    val timeError = MutableLiveData<String>()
    val emptySpotsError = MutableLiveData<String>()

    var activityId: String = ""

    fun insertActivity(activity: Activities) {
        repoActivity.insertActivity(activity) {
            activityId = it
            insertAttendee()
        }
    }

    fun insertAttendee() {
        val uid = user?.uid ?: ""
        val email = user?.email ?: ""
        val attendee = Attendee(uid, email)
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

    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        timestampCalendar.set(year, month, dayOfMonth)
        date.value = dateFormat.format(timestampCalendar.time)
    }

    fun setTime(hourOfDay: Int, minute: Int) {
        timestampCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        timestampCalendar.set(Calendar.MINUTE, minute)
        time.value = timeFormat.format(timestampCalendar.time)
    }

    fun setPLace(context: Context, currentPlace: Place) {
        val gcd = Geocoder(context, Locale.getDefault())
        place.value = gcd.getFromLocation(currentPlace.latLng.latitude, currentPlace.latLng.longitude, 1)
    }

    fun createActivity(eventName: String, description: String, location: String,
                       date: String, time: String, emptySpots: String) {
        if (eventName.isEmpty()) {
            eventNameError.value = "You need a name!"
            return
        } else {
            eventNameError.value = null
        }
        if (description.isEmpty()) {
            descriptionError.value = "Add some information!"
            return
        } else {
            descriptionError.value = null
        }
        if (location.isEmpty()) {
            locationError.value = "Pick a location!"
            return
        } else {
            locationError.value = null
        }
        if (date.isEmpty()) {
            dateError.value = "Pick a date!"
            return
        } else {
            dateError.value = null
        }
        if (time.isEmpty()) {
            timeError.value = "Pick a time!"
            return
        } else {
            timeError.value = null
        }
        if (emptySpots.isEmpty()) {
            emptySpotsError.value = "Choose how many people!"
            return
        } else {
            emptySpotsError.value = null
        }


    }
}