package com.apia22018.sportactivities.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import com.apia22018.sportactivities.R
import com.google.firebase.auth.FirebaseAuth


fun isNullOrEmpty(str: String?): Boolean {
    if (str != null && !str.isEmpty())
        return false
    return true
}

fun AppCompatActivity.loadFragment(fragment: Fragment) {
    supportFragmentManager
            .beginTransaction()
            .replace(
                    R.id.container,
                    fragment
            )
            .commit()
}

fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}

//Useful extensions
//http://kotlinextensions.com