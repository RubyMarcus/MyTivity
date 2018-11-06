package com.apia22018.sportactivities.screens.alertDialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Message
import android.support.v4.app.DialogFragment
import com.apia22018.sportactivities.R

class alertFragment : DialogFragment() {

    var confirmation : Boolean = false
    var message = R.string.stand_in

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(message)
                    .setPositiveButton(R.string.yes
                    ) { dialog, id ->
                        confirmation = true
                    }
                    .setNegativeButton(R.string.cancel
                    ) { dialog, id ->
                        confirmation = false
                    }
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}