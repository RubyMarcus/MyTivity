package com.apia22018.sportactivities.utils

import android.databinding.BindingAdapter
import android.databinding.InverseBindingListener
import android.location.Address
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.data.activities.Activities
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("day")
fun setDayAttr(view: TextView, value: Long?) {
    val sdf = SimpleDateFormat("dd", Locale.getDefault())
    val cal = Calendar.getInstance().apply { timeInMillis = value ?: 1539255728 }
    view.text = sdf.format(cal.time)
}

@BindingAdapter("month")
fun setMonthAttr(view: TextView, value: Long?) {
    val sdf = SimpleDateFormat("MMM", Locale.getDefault())
    val cal = Calendar.getInstance().apply { timeInMillis = value ?: 1539255728 }
    view.text = sdf.format(cal.time)
}

@BindingAdapter("dayAndTime")
fun setDayAndTimeAttr(view: TextView, value: Long?) {
    val sdf = SimpleDateFormat("EEE - HH:mm", Locale.getDefault())
    val cal = Calendar.getInstance().apply { timeInMillis = value ?: 1539255728 }
    view.text = sdf.format(cal.time).toUpperCase()
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.VISIBLE else View.GONE
}

@BindingAdapter("place")
fun setPlaceValue(view: TextView, value: Address?) {
    if (value != null) {
        if (value.locality == null || value.thoroughfare == null) {
            view.text = view.resources.getString(R.string.invalid_address)
        } else {
            val text = value.locality + " " + value.thoroughfare + " " + value.subThoroughfare
            view.text = text
        }
    } else {
        view.text = ""
    }
}

@BindingAdapter("address")
fun formatAddress(view: TextView, value: Activities?) {
    val address = value ?: Activities()
    val text = "${address.streetName} \n ${address.city}"
    view.text = text
}

@BindingAdapter("listAddress")
fun setListAddress(view: TextView, value: Activities?) {
    val address = value ?: Activities()
    val text = "${address.streetName} - ${address.city}"
    view.text = text
}

@BindingAdapter("emptySpots")
fun setEmptySpots(view: TextView, value: Activities?) {
    val activities = value ?: Activities()
    val text = "${view.resources.getString(R.string.empty_spots)} ${activities.emptySeaterinos()} ${view.resources.getString(R.string.total_of)} ${activities.totalSeats}"
    view.text = text
}

@BindingAdapter("hideKeyboardOnLostFocus")
fun hideKeyboardOnLostFocus(view: EditText, enabled: Boolean) {
    if (!enabled) return
    view.setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            view.clearFocus()
            view.hideKeyboard()
        }
    }
}

