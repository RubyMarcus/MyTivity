package com.apia22018.sportactivities.screens.add

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.apia22018.sportactivities.R
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.add_activity.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent
import android.location.Geocoder
import android.view.MenuItem
import android.view.View
import com.apia22018.sportactivities.data.attendee.Attendee
import com.apia22018.sportactivities.data.activities.Activities
import com.apia22018.sportactivities.databinding.AddActivityBinding
import com.apia22018.sportactivities.utils.InjectorUtils
import com.apia22018.sportactivities.utils.isNullOrEmpty
import com.apia22018.sportactivities.utils.showSnackbar
import com.google.firebase.auth.FirebaseAuth


class AddActivity : AppCompatActivity() {

    //Format
    val dateFormat = SimpleDateFormat("dd MMM, YYYY", Locale.getDefault())
    val timestampFormat = SimpleDateFormat("YYYYMMddHHmm", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    //Maps
    val PLACE_PICKER_REQUEST = 1

    //ViewModel
    lateinit var viewModel: AddViewModel

    //Calendar
    val timestampCalendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory: AddViewModelFactory = InjectorUtils.provideAddActivityViewModelFactory()

        viewModel = ViewModelProviders.of(this, factory
        ).get(AddViewModel::class.java)

        val binding: AddActivityBinding = DataBindingUtil.setContentView(this, R.layout.add_activity)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()


        setSupportActionBar(toolbar_add_activity)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Add activity"
        }

        dialogObservers()
        onChangeObservers()

        floatingActionButton3.setOnClickListener {
            createActivity(binding.root)
        }
    }

    // Rotation
    private fun onChangeObservers () {
        viewModel.place.observe(this, android.arch.lifecycle.Observer {
            it?.let { adresses ->
                if (adresses.isNotEmpty()) {
                    add_location_btn.text = adresses[0].locality + " " + adresses[0].thoroughfare + " " + adresses[0].subThoroughfare
                }
            }
        })

        viewModel.date.observe(this, android.arch.lifecycle.Observer {
            it.let {
                date_activity_btn.text = it
            }
        })

        viewModel.time.observe(this, android.arch.lifecycle.Observer {
            it.let {
                time_activity_btn.text = it
            }
        })
    }

    private fun dialogObservers () {
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
            viewModel.date.value = dateFormat.format(timestampCalendar.time)
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
            viewModel.time.value = timeFormat.format(timestampCalendar.time)
        },
                //Get time from calendar
                timestampCalendar.get(Calendar.HOUR), timestampCalendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val place = PlacePicker.getPlace(this, data)
                    val gcd: Geocoder = Geocoder(this, Locale.getDefault())

                    viewModel.place.value = gcd.getFromLocation(place.latLng.latitude, place.latLng.longitude, 1)
                }
            }
        }
    }

    //Move to ViewModel?
    private fun createActivity(view: View) {
        val title: String
        val totalSeats: Int
        val timestamp: Long

        if (isNullOrEmpty(name_activty.text.toString())) {
            showSnackbar(view, "Fill in name!")
            return
        } else {
            title = name_activty.text.toString()
        }

        if (add_location_btn.text == "Pick location") {
            showSnackbar(view, "Pick a location!")
            return
        }

        if (date_activity_btn.text != "Pick date") {
            if (time_activity_btn.text != "Pick time") {
                timestamp = timestampCalendar.timeInMillis
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

        val user = FirebaseAuth.getInstance().currentUser

        val description = description_activity.text.toString()
        val occupiedSeats = 1
        val uid = user?.uid ?: ""
        val email = user?.email ?: ""

        viewModel.place.value?.let {
            viewModel.insertActivity(Activities(title, description, totalSeats,
                    occupiedSeats, timestamp, it[0].locality,
                    it[0].thoroughfare + " " + it[0].subThoroughfare, uid, it[0].latitude, it[0].longitude)).also {
                viewModel.insertAttendee(Attendee(uid, email))
            }
        }

        finish()
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AddActivity::class.java))
        }
    }
}
