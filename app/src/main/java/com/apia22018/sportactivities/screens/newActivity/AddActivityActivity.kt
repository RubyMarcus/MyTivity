package com.apia22018.sportactivities.screens.newActivity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.AddActivityActivityBinding
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.add_activity_activity.*
import java.lang.Exception
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.PersistableBundle
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.utils.InjectorUtils
import kotlinx.android.synthetic.main.activities_list_item.*
import java.time.Month


class AddActivityActivity : AppCompatActivity() {

    //Geocoder address
    var addresses : List<Address>? = null

    //Format
    val formate = SimpleDateFormat("dd MMM, YYYY", Locale.ENGLISH)
    val timestampFormat = SimpleDateFormat("YYYYMMddhhmm", Locale.ENGLISH)
    val timeFormat = SimpleDateFormat("hh:mm", Locale.ENGLISH)

    //Maps
    val PLACE_PICKER_REQUEST = 1

    //ViewModel
    lateinit var viewModel: AddActivityViewModel

    //Timestamp
    lateinit var timestamp: Timestamp

    //Calendar
    val date = Calendar.getInstance()
    val timestampCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory: AddActivityViewModelFactory = InjectorUtils.provideAddActivityViewModelFactorty()

        viewModel = ViewModelProviders.of(this, factory
        ).get(AddActivityViewModel::class.java)

        val binding: AddActivityActivityBinding = DataBindingUtil.setContentView(this, R.layout.add_activity_activity)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()

        //Toolbar
        val toolbar = binding.toolbar1
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Add Activity"
            setDisplayHomeAsUpEnabled(true)
        }

        viewModel.showPlacePickerDialog.observe(this, android.arch.lifecycle.Observer { showDialog ->
            showDialog?.let {
                if (it) {
                    placePickerDialog()
                }
            }
        })

        viewModel.showDatePickerDialog.observe(this, android.arch.lifecycle.Observer { showDialog ->
            showDialog?.let {
                if (it) {
                    datePickerDialog()
                }
            }
        })

        viewModel.showTimePickerDialog.observe(this, android.arch.lifecycle.Observer { showDialog ->
            showDialog?.let {
                if (it) {
                    timePickerDialog()
                }
            }
        })

        floatingActionButton3.setOnClickListener {
            createActivity()
        }

        if (savedInstanceState != null) {
            val placeValue = savedInstanceState.getString("place")
            val dateValue = savedInstanceState.getString("date")
            val timeValue = savedInstanceState.getString("time")

            add_location_btn.text = placeValue
            date_activity_btn.text = dateValue
            time_activity_btn.text = timeValue
        }

        //AIzaSyAN4KfG_eN5vicoK0lOl5jsF7fJVCiArhM old key
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString("place", add_location_btn.text.toString())
        outState?.putString("date", date_activity_btn.text.toString())
        outState?.putString("time", time_activity_btn.text.toString())
    }


    private fun placePickerDialog() {
        val builder = PlacePicker.IntentBuilder()
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
    }

    private fun datePickerDialog() {
        // Add current selected date?

        val datepicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val selectedDate = Calendar.getInstance()
            selectedDate.set(Calendar.YEAR, year)
            selectedDate.set(Calendar.MONTH, month)
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            timestampCalendar.set(Calendar.YEAR, year)
            timestampCalendar.set(Calendar.MONTH, month)
            timestampCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            timestampCalendar

            val date = formate.format(selectedDate.time)
            date_activity_btn.text = date.toString()
        },
                date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH))
        datepicker.show()
    }

    private fun timePickerDialog() {
        time_activity_btn.setOnClickListener {
            try {
                if (time_activity_btn.text != "Pick time") {
                    val date = timeFormat.parse(time_activity_btn.text.toString())
                    //date.time = date
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                timestampCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                timestampCalendar.set(Calendar.MINUTE, minute)

                val time = timeFormat.format(selectedTime.time)
                time_activity_btn.text = time.toString()
            },
                    date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), true)
            timePicker.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data!!, this)
                val gcd : Geocoder = Geocoder(this, Locale.getDefault())

                try {
                    addresses = gcd.getFromLocation(place.latLng.latitude, place.latLng.longitude, 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (addresses!!.isNotEmpty()) {
                    addresses.let {
                        add_location_btn.text = it!![0].locality + " " + it!![0].thoroughfare + " " + it!![0].subThoroughfare
                    }
                }
            }
        }
    }

    private fun createActivity() {
        val name = name_activty.text.toString()
        val description = description_activity.text.toString()

        val timestamp : Long = timestampFormat.format(timestampCalendar.time).toLong()

        val city = addresses!![0].locality
        val streetname = addresses!![0].thoroughfare + " " + addresses!![0].subThoroughfare

        val totalSeats = total_people.text.toString().toInt()
        val occupiedSeats = 0
        val createdby = "UID"

        viewModel.insertActivity(Activities(name, description, totalSeats, occupiedSeats, timestamp, city, streetname, createdby))
    }
}
