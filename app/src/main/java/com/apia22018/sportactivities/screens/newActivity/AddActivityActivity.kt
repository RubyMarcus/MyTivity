package com.apia22018.sportactivities.screens.newActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.AddActivityActivityBinding
import kotlinx.android.synthetic.main.add_activity_activity.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddActivityActivity : AppCompatActivity() {

    val formate = SimpleDateFormat("dd MMM, YYYY", Locale.ENGLISH)
    val timeFormat = SimpleDateFormat("hh:mm", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity_activity)

        val binding: AddActivityActivityBinding = DataBindingUtil.setContentView(this, R.layout.add_activity_activity)

        val toolbar = binding.toolbar1
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Add Activity"
            setDisplayHomeAsUpEnabled(true)
        }

        val now = Calendar.getInstance()

        date_activity_btn.setOnClickListener {
            // Add current selected date?

            val datepicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view, year, month, dayOfMonth ->

                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val date = formate.format(selectedDate.time)
                date_activity_btn.text = date.toString()
            },
                    now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
            datepicker.show()
        }

        time_activity_btn.setOnClickListener {
            try {
                if(time_activity_btn.text != "Pick time") {
                    val date = timeFormat.parse(time_activity_btn.text.toString())
                    now.time = date
                }
            } catch (e:Exception) {
                e.printStackTrace()
            }

            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener{view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                val time = timeFormat.format(selectedTime.time)
                time_activity_btn.text = time.toString()

            },
                    now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
            timePicker.show()
        }


    }

    fun createActivity () {

        val name = name_activty.text as String
        val description = description_activity as String


    }
}
