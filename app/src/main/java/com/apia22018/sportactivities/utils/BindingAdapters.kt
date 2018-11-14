package com.apia22018.sportactivities.utils

import android.databinding.BindingAdapter
import android.databinding.InverseBindingListener
import android.location.Address
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.apia22018.sportactivities.data.activities.Activities
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
@BindingAdapter(value = ["userAttrChanged"])
fun setListener(view: TextView, listener: InverseBindingListener) {
    view.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            view.error = ""
        }

        override fun afterTextChanged(editable: Editable?) {
            listener.onChange()
        }
    })
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

