package com.apia22018.sportactivities.screens.newActivity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.AddActivityActivityBinding
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.add_activity_activity.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.support.design.widget.Snackbar
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.view.View
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.listActivities.Activities
import com.apia22018.sportactivities.data.location.Location
import com.apia22018.sportactivities.utils.InjectorUtils
import com.apia22018.sportactivities.utils.isNullOrEmpty
import com.apia22018.sportactivities.utils.showSnackbar


class AddActivityActivity : AppCompatActivity() {

    //Geocoder address
    var addresses: List<Address>? = null

    //Format
    val dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.getDefault())
    val timestampFormat = SimpleDateFormat("YYYYMMddHHmm", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    //Maps
    val PLACE_PICKER_REQUEST = 1

    //ViewModel
    lateinit var viewModel: AddActivityViewModel

    //Calendar
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
            setDisplayHomeAsUpEnabled(true)
            title =""
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
            createActivity(binding.root)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        val datepicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            //Set time to calendar
            timestampCalendar.set(Calendar.YEAR, year)
            timestampCalendar.set(Calendar.MONTH, month)
            timestampCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            //Convert to showable format
            val date = dateFormat.format(timestampCalendar.time)
            date_activity_btn.text = date.toString()
        },
                //Get time from calendar
                timestampCalendar.get(Calendar.YEAR), timestampCalendar.get(Calendar.MONTH), timestampCalendar.get(Calendar.DAY_OF_MONTH))
        datepicker.show()
    }

    private fun timePickerDialog() {
        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

            //Set time to calendar
            timestampCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            timestampCalendar.set(Calendar.MINUTE, minute)

            //Convert to showable format
            val time = timeFormat.format(timestampCalendar.time)
            time_activity_btn.text = time.toString()

        },
                //Get time from calendar
                timestampCalendar.get(Calendar.HOUR), timestampCalendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data!!, this)
                val gcd: Geocoder = Geocoder(this, Locale.getDefault())

                try {
                    addresses = gcd.getFromLocation(place.latLng.latitude, place.latLng.longitude, 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                if (addresses!!.isNotEmpty()) {
                    addresses.let {
                        add_location_btn.text = it!![0].locality + " " + it[0].thoroughfare + " " + it[0].subThoroughfare
                    }
                }
            }
        }
    }

    private fun createActivity(view: View) {
        var title: String
        var totalSeats: Int
        var city: String = ""
        var streetname: String = ""
        var timestamp: Long


        if (isNullOrEmpty(name_activty.text.toString())) {
            showSnackbar(view, "Fill in name!")
            return
        } else {
            title = name_activty.text.toString()
        }

        if (add_location_btn.text != "Pick location") {
            city = addresses!![0].locality
            streetname = addresses!![0].thoroughfare + " " + addresses!![0].subThoroughfare
        } else {
            showSnackbar(view, "Pick a location!")
            return
        }

        if (date_activity_btn.text != "Pick date") {
            if (time_activity_btn.text != "Pick time") {
                timestamp = timestampFormat.format(timestampCalendar.time).toLong()
            } else {
                showSnackbar(view, "Pick a time!")
                return
            }
        } else {
            showSnackbar(view, "Pick a date!")
            return
        }

        if (isNullOrEmpty(total_people.text.toString())) {
            showSnackbar(view, "Fill in max people!")
            return
        } else {
            totalSeats = total_people.text.toString().toInt()
        }

        val description = description_activity.text.toString()
        val occupiedSeats = 1
        val createdby = "UID" // Get current logged in user

        viewModel.insertActivity(Activities(title, description, totalSeats, occupiedSeats, timestamp, city, streetname, createdby))
        viewModel.insertLocation(Location(addresses!![0].latitude, addresses!![0].longitude))
        viewModel.insertAttendee(Attendee("TestUser")) // Add UID as well

        finish()
    }
}
