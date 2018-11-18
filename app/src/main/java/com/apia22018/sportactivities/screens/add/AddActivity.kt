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
import java.util.*
import android.content.Intent
import android.location.Geocoder
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.apia22018.sportactivities.databinding.AddActivityBinding
import com.apia22018.sportactivities.utils.InjectorUtils

class AddActivity : AppCompatActivity() {

    val PLACE_PICKER_REQUEST = 1

    lateinit var viewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory: AddViewModelFactory = InjectorUtils.provideAddActivityViewModelFactory()

        viewModel = ViewModelProviders.of(this, factory
        ).get(AddViewModel::class.java)

        val binding: AddActivityBinding = DataBindingUtil.setContentView(this, R.layout.add_activity)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()

        val toolbar = binding.toolbarAddActivity
        setSupportActionBar(toolbar)

        toolbar.setTitleTextColor(android.graphics.Color.WHITE)

        supportActionBar?.apply {
            title = "New activity"
        }

        description_add_edittext.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
        description_add_edittext.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

        textErrorObservers()
        dialogObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {
            createNewActivity()
            true
        }

        android.R.id.home -> {
            // Respond to the action bar's Up/Home button
            showCreateDialog("Are you sure you want to remove the activity")
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun textErrorObservers() {
        viewModel.eventNameError.observe(this, android.arch.lifecycle.Observer {
            eventname_textInputLayout.error = it
        })

        viewModel.descriptionError.observe(this, android.arch.lifecycle.Observer {
            description_textInputLayout.error = it
        })

        viewModel.locationError.observe(this, android.arch.lifecycle.Observer {
            location_textInputLayout.error = it
        })

        viewModel.dateError.observe(this, android.arch.lifecycle.Observer {
            date_textInputLayout.error = it
        })

        viewModel.timeError.observe(this, android.arch.lifecycle.Observer {
            time_textInputLayout.error = it
        })

        viewModel.emptySpotsError.observe(this, android.arch.lifecycle.Observer {
            spots_textInputLayout.error = it
        })
    }

    private fun dialogObservers() {
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

    private fun placePickerDialog() {
        val builder = PlacePicker.IntentBuilder()
        startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST)
    }

    private fun datePickerDialog() {
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.setDate(year, month, dayOfMonth)

        },
                viewModel.timestampCalendar.get(Calendar.YEAR), viewModel.timestampCalendar.get(Calendar.MONTH), viewModel.timestampCalendar.get(Calendar.DAY_OF_MONTH))

        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.show()
    }

    private fun timePickerDialog() {
        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.setTime(hourOfDay, minute)
        },
                viewModel.timestampCalendar.get(Calendar.HOUR_OF_DAY), viewModel.timestampCalendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val place = PlacePicker.getPlace(this, data)
            if (place != null) {
                Geocoder(this, Locale.getDefault()).run {
                    this.getFromLocation(place.latLng.latitude, place.latLng.longitude, 1).mapNotNull {
                        if (it != null) {
                            viewModel.setPLace(it)
                        }

                    }
                }
            }
        }
    }

    private fun createNewActivity() {
        val name = eventname_add_edittext.text.toString()
        val description = description_add_edittext.text.toString()
        val location = location_add_edittext.text.toString()
        val date = date_add_edittext.text.toString()
        val time = time_add_edittext.text.toString()
        val emptySpots = spots_add_edittext.text.toString()

        val success = viewModel.createActivity(name, description, location, date, time, emptySpots)

        if (success) {
            finish()
        }
    }

    private fun showCreateDialog(textInfo: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Remove Activity")
        val view = layoutInflater.inflate(R.layout.dialog, null)
        builder.setView(view)
        val firstNameView = view.findViewById(R.id.dialogInfo) as TextView
        firstNameView.text = textInfo
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            finish()
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
        builder.show()
    }

    override fun onBackPressed() {
        showCreateDialog("Are you sure you want to remove the activity")
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AddActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent)
        }
    }
}
