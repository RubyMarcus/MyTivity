package com.apia22018.sportactivities.screens.add

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.apia22018.sportactivities.R
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.add_activity.*
import java.util.*
import android.content.Intent
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.TextView
import com.apia22018.sportactivities.databinding.AddActivityBinding
import com.apia22018.sportactivities.utils.InjectorUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuInflater


class AddActivity : DialogFragment() {

    val PLACE_PICKER_REQUEST = 1

    lateinit var viewModel: AddViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val factory: AddViewModelFactory = InjectorUtils.provideAddActivityViewModelFactory()

        viewModel = ViewModelProviders.of(this, factory)
                .get(AddViewModel::class.java)

        val binding: AddActivityBinding = AddActivityBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()

        val toolbar = binding.toolbarAddActivity
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        (activity as AppCompatActivity).supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        setHasOptionsMenu(true)

        toolbar.apply {
            title = "New activity"
            setTitleTextColor(android.graphics.Color.WHITE)
        }

//        description_add_edittext.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE)
//        description_add_edittext.setRawInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES)

        textErrorObservers()
        dialogObservers()

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_activity_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {
            println("KLICK KLICK")
            createNewActivity()
            true
        }

        android.R.id.home -> {
            // Respond to the action bar's Up/Home button
            //showCreateDialog("Are you sure you want to remove the activity")
            println("KLICK KLICK KLICK")
            dismiss()
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
        startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST)
    }

    private fun datePickerDialog() {
        val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            viewModel.setDate(year, month, dayOfMonth)

        },
                viewModel.timestampCalendar.get(Calendar.YEAR), viewModel.timestampCalendar.get(Calendar.MONTH), viewModel.timestampCalendar.get(Calendar.DAY_OF_MONTH))

        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
        datePicker.show()
    }

    private fun timePickerDialog() {
        val timePicker = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            viewModel.setTime(hourOfDay, minute)
        },
                viewModel.timestampCalendar.get(Calendar.HOUR_OF_DAY), viewModel.timestampCalendar.get(Calendar.MINUTE), true)
        timePicker.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val place = PlacePicker.getPlace(requireContext(), data)
                    viewModel.setPLace(requireContext(), place)
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
            dismiss()
        }
    }

    private fun showCreateDialog(textInfo: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Remove Activity")
        val view = layoutInflater.inflate(R.layout.dialog, null)
        builder.setView(view)
        val firstNameView = view.findViewById(R.id.dialogInfo) as TextView
        firstNameView.text = textInfo
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            dismiss()
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ -> }
        builder.show()
    }

    companion object {
        fun newInstance() = AddActivity()
    }
}
