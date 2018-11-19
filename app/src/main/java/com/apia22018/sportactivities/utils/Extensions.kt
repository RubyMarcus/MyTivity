package com.apia22018.sportactivities.utils

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.support.v4.app.Fragment
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.apia22018.sportactivities.R
import java.util.regex.Pattern

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

fun isEmailValid(email: String): Boolean {
    return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
    ).matcher(email).matches()
}

fun userTouchDisabled (window : Window) {
    window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun userTouchEnabled (window : Window) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
}

//Useful extensions
//http://kotlinextensions.com