package com.apia22018.sportactivities.utils

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.support.v4.app.Fragment
import com.apia22018.sportactivities.R

fun AppCompatActivity.showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

fun AppCompatActivity.isNullOrEmpty(str: String?): Boolean {
    if (str != null && !str.isEmpty())
        return false
    return true

fun AppCompatActivity.loadFragment(fragment: Fragment) {
    supportFragmentManager
            .beginTransaction()
            .replace(
                    R.id.container,
                    fragment
            )
            .commit()
}