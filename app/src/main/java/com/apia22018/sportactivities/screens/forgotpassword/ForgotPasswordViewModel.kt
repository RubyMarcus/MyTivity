package com.apia22018.sportactivities.screens.forgotpassword

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
<<<<<<< HEAD
import com.apia22018.sportactivities.utils.SingleLiveEvent
=======
import android.provider.Settings.Global.getString
import com.apia22018.sportactivities.R
>>>>>>> något
import com.apia22018.sportactivities.utils.isEmailValid
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : ViewModel () {

    private val user = FirebaseAuth.getInstance()
<<<<<<< HEAD
    val emailError = MutableLiveData<String>()
    val isComplete = SingleLiveEvent<Boolean>()
=======
    val emailError = MutableLiveData<Int>()
    val isComplete = MutableLiveData<Boolean>()
>>>>>>> något

    fun isValid (email : String) {
        if (email.isEmpty()) {
            emailError.value = R.string.enter_email
        } else if (!isEmailValid(email)) {
            emailError.value = R.string.email_invalid
        } else {
            emailError.value = null
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