package com.apia22018.sportactivities.screens.forgotpassword

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.apia22018.sportactivities.utils.SingleLiveEvent
import android.provider.Settings.Global.getString
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.utils.isEmailValid
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ForgotPasswordViewModel : ViewModel () {

    private val user = FirebaseAuth.getInstance()
    val isLoading = ObservableBoolean(false)
    val emailError = MutableLiveData<String>()
    val isComplete = SingleLiveEvent<Boolean>()

    fun isValid (email : String) {
        if (email.isEmpty()) {
            emailError.value = R.string.enter_email
        } else if (!isEmailValid(email)) {
            emailError.value = R.string.email_invalid
        } else {
            emailError.value = null
            isLoading.set(true)
            sendPasswordReset(email)
        }
    }

    private fun sendPasswordReset (email: String) {
        user.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if(task.isComplete) {
                isComplete.value = task.isComplete
            }
        }
    }
}