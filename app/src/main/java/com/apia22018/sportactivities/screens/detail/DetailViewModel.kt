package com.apia22018.sportactivities.screens.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.view.View.GONE
import android.view.View.VISIBLE
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.data.activities.ActivitiesRepository
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.attendee.AttendeeRepository
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth

class DetailViewModel(private val activityId: String,
                      private val activitiesRepository: ActivitiesRepository,
                      private val attendeeRepository: AttendeeRepository
) : ViewModel() {
    private val attendeeLiveData = attendeeRepository.getAttendees(activityId)
    private val activities: LiveData<Activities> = activitiesRepository.readActivity(activityId)
    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    val removeActivity = SingleLiveEvent<Boolean>()

    fun attendEvent() {
        val user = FirebaseAuth.getInstance().currentUser
        //TODO("NEED TO UPDATE ATTENDEE HERE")
        activitiesRepository.updateActivityAttendees(activityId, 2) {
            if (it) user?.email?.let {
                attendeeRepository.insertAttendees(Attendee(user.uid, it), activityId)
            } else {
                //TODO("DISPLAY SOME KIND OF ERROR, COULD NOT ATTEND TRY AGAIN")
            }
        }
    }

    fun getAttendees() = attendeeLiveData

    fun deleteAttendee(attendee: Attendee) {
        attendeeRepository.deleteAttendee(activityId, attendee) { value ->
            if (value) {
                //TODO("NEED TO REMOVE ATTENDEE HERE")
                activitiesRepository.updateActivityAttendees(activityId, 1)
            }
        }
    }

    fun canRemoveAttendee(userAttendeeUID: String): Int = if (currentUserUid == userAttendeeUID) VISIBLE else GONE

    fun getActivity() = activities

    fun canDeleteActivity(){}

    fun deleteActivity() {
        activitiesRepository.deleteActivity(activityId) {
            if (it) {
                removeActivity.postValue(it)
            } else {
                //TODO("ERROR KUNDE INTE TA BORT AKTIVIET")
            }
        }
    }

}