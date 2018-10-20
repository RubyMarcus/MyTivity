package com.apia22018.sportactivities.utils

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View

fun AppCompatActivity.showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

fun AppCompatActivity.isNullOrEmpty(str: String?): Boolean {
    if (str != null && !str.isEmpty())
        return false
    return true
}