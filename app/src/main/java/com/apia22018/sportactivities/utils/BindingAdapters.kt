package com.apia22018.sportactivities.utils

import android.content.Context
import android.databinding.BindingAdapter
import android.location.Address
import android.support.design.widget.TextInputEditText
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.apia22018.sportactivities.data.activities.Activities
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("day")
fun setDayAttr(view: TextView, value: Long?) {
    val sdf = SimpleDateFormat("dd", Locale.UK)
    val cal = Calendar.getInstance().apply { timeInMillis = value ?: 1539255728 }
    view.text = sdf.format(cal.time)
}

@BindingAdapter("month")
fun setMonthAttr(view: TextView, value: Long?) {
    val sdf = SimpleDateFormat("MMM", Locale.UK)
    val cal = Calendar.getInstance().apply { timeInMillis = value ?: 1539255728 }
    view.text = sdf.format(cal.time)
}

@BindingAdapter("dayAndTime")
fun setDayAndTimeAttr(view: TextView, value: Long?) {
    val sdf = SimpleDateFormat("EEE - HH:mm", Locale.UK)
    val cal = Calendar.getInstance().apply { timeInMillis = value ?: 1539255728 }
    view.text = sdf.format(cal.time)
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) View.VISIBLE else View.GONE
}

@BindingAdapter("place")
fun setPlaceValue(view: TextView, value: List<Address>?) {
    if (value != null) {
        if (value[0].locality == null || value[0].thoroughfare == null) {
            view.text = "Invalid address"
        } else {
            view.text = value[0].locality + " " + value[0].thoroughfare + " " + value[0].subThoroughfare
        }
    } else {
        view.text = ""
    }
}

@BindingAdapter("address")
fun formatAddress(view: TextView, value: Activities?) {
    val address = value ?: Activities()
    view.text = "${address.streetName} \n ${address.city}"
}

@BindingAdapter("listAddress")
fun setListAddress(view: TextView, value: Activities?) {
    val address = value ?: Activities()
    view.text = "${address.streetName} - ${address.city}"
}

@BindingAdapter("emptySpots")
fun setEmptySpots(view: TextView, value: Activities?) {
    val activities = value ?: Activities()
    view.text = "Empty spots: ${activities.emptySeaterinos()}"
}