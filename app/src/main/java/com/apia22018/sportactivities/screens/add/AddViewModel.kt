package com.apia22018.sportactivities.screens.add

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.location.Address
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent
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

    val place = MutableLiveData<Address>()
    val date = MutableLiveData<String>()
    val time = MutableLiveData<String>()

    val eventNameError = MutableLiveData<Int>()
    val descriptionError = MutableLiveData<Int>()
    val locationError = MutableLiveData<Int>()
    val dateError = MutableLiveData<Int>()
    val timeError = MutableLiveData<Int>()
    val emptySpotsError = MutableLiveData<Int>()

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
        dateError.value = null
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
            timeError.value = R.string.not_valid_time
            time.value = ""
        } else {
            time.value = timeFormat.format(timestampCalendar.time)
            timeError.value = null
        }
    }

    fun setPLace(addressList: List<Address?>?) {
        addressList?.mapNotNull { address ->
            if (address != null) {
                place.value = address
            }
        }
    }

    fun createActivity(eventName: String, description: String, location: String,
                       date: String, time: String, emptySpots: String) : Boolean {

        var isEmptyFallback = false

        if (eventName.isEmpty()) {
            eventNameError.value = R.string.need_name
            isEmptyFallback = true
        } else {
            eventNameError.value = null
        }
        if (description.isEmpty()) {
            descriptionError.value = R.string.add_info
            isEmptyFallback = true
        } else {
            descriptionError.value = null
        }
        when {
            location.isEmpty() -> {
                locationError.value = R.string.pick_a_location
                isEmptyFallback = true
            }
            location == "Invalid address!" -> {
                locationError.value = R.string.invalid_address
                isEmptyFallback = true
            }
            else -> locationError.value = null
        }
        if (date.isEmpty()) {
            dateError.value = R.string.hint_Date
            isEmptyFallback = true
        } else {
            dateError.value = null
        }
        if (time.isEmpty()) {
            timeError.value = R.string.pick_a_time
            isEmptyFallback = true
        } else {
            timeError.value = null
        }
        if (emptySpots.isEmpty()) {
            emptySpotsError.value = R.string.choose_number_people
            isEmptyFallback = true
        } else {
            emptySpotsError.value = null
        }

        if (isEmptyFallback) return false

        place.value?.let {
            insertActivity(Activities(eventName, description, emptySpots.toInt(), 1,
                    timestampCalendar.timeInMillis, it.locality ?: "",
                    it.thoroughfare + " " + it.subThoroughfare, user?.uid ?: "",
                    it.latitude, it.longitude))
            }
        return true
        }
    }
