package com.apia22018.sportactivities.utils

import android.databinding.BindingAdapter
import android.location.Address
import android.view.View
import android.widget.TextView
import com.apia22018.sportactivities.data.activities.Activities
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("date")
fun setDateAttr(view: TextView, value: Long?) {
    val sdf = SimpleDateFormat("EEEE '\n' HH:mm", Locale.UK)
    val cal = Calendar.getInstance().apply { timeInMillis = value ?: 1539255728 }
    view.text = sdf.format(cal.time)
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
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
