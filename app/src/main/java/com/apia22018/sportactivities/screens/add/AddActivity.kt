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
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
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
            finish()
            true
        }

        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun textErrorObservers () {
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
        datePicker.show()
    }

    private fun timePickerDialog() {
        val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            viewModel.setTime(hourOfDay, minute)
        },
                viewModel.timestampCalendar.get(Calendar.HOUR), viewModel.timestampCalendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val place = PlacePicker.getPlace(this, data)
                    viewModel.setPLace(this, place)
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

        if (success) { finish() }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AddActivity::class.java))
        }
    }
}
