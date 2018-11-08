package com.apia22018.sportactivities.screens.add

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.google.android.gms.location.places.Place
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class AddViewModel(private val repoActivity: ActivitiesRepository, val repoAttendee: AttendeeRepository) : ViewModel() {

    private val dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    val timestampCalendar = Calendar.getInstance()

    val showPlacePickerDialog = SingleLiveEvent<Boolean>()
    val showDatePickerDialog = SingleLiveEvent<Boolean>()
    val showTimePickerDialog = SingleLiveEvent<Boolean>()

    private val user = FirebaseAuth.getInstance().currentUser

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

    private fun insertActivity(activity: Activities) {
        repoActivity.insertActivity(activity) {
            activityId = it
            insertAttendee()
        }
    }

    private fun insertAttendee() {
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

        val currentTime = Calendar.getInstance()
        currentTime.timeInMillis = System.currentTimeMillis() - 1000

        currentTime.apply {
            this.add(Calendar.HOUR, 1)
        }

        if(currentTime > timestampCalendar) {
            //Snackbar?

            time.value = ""
        } else {
            time.value = timeFormat.format(timestampCalendar.time)
        }
    }

    fun setPLace(context: Context, currentPlace: Place) {
        val gcd = Geocoder(context, Locale.getDefault())
        place.value = gcd.getFromLocation(currentPlace.latLng.latitude, currentPlace.latLng.longitude, 1)
    }

    fun createActivity(eventName: String, description: String, location: String,
                       date: String, time: String, emptySpots: String) : Boolean {

        var isEmptyFallback = false

        if (eventName.isEmpty()) {
            eventNameError.value = "You need a name!"
            isEmptyFallback = true
        } else {
            eventNameError.value = null
        }
        if (description.isEmpty()) {
            descriptionError.value = "Add some information!"
            isEmptyFallback = true
        } else {
            descriptionError.value = null
        }
        when {
            location.isEmpty() -> {
                locationError.value = "Pick a location!"
                isEmptyFallback = true
            }
            location == "Invalid address" -> {
                locationError.value = "Invalid address, pick new location!"
                isEmptyFallback = true
            }
            else -> locationError.value = null
        }
        if (date.isEmpty()) {
            dateError.value = "Pick a date!"
            isEmptyFallback = true
        } else {
            dateError.value = null
        }
        if (time.isEmpty()) {
            timeError.value = "Pick a time!"
            isEmptyFallback = true
        } else {
            timeError.value = null
        }
        if (emptySpots.isEmpty()) {
            emptySpotsError.value = "Choose how many people!"
            isEmptyFallback = true
        } else {
            emptySpotsError.value = null
        }

        if (isEmptyFallback) return false

        place.value?.let {
            insertActivity(Activities(eventName, description, emptySpots.toInt(), 1,
                    timestampCalendar.timeInMillis, it[0].locality ?: "",
                    it[0].thoroughfare + " " + it[0].subThoroughfare, user?.uid ?: "",
                    it[0].latitude, it[0].longitude))
            }
        return true
        }
    }
