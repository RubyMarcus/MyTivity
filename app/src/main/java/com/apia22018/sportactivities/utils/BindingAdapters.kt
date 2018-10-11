package com.apia22018.sportactivities.utils

import android.databinding.BindingAdapter
import android.widget.TextView
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("date")
fun setDateAttr(view: TextView, value: Long?) {
    val sdf = SimpleDateFormat("EEEE '\n' HH:mm", Locale.UK)
    val cal = Calendar.getInstance().apply { timeInMillis = value ?: 1539255728 }
    view.text =  sdf.format(cal.time)
}
