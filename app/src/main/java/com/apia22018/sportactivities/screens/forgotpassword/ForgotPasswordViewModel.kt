package com.apia22018.sportactivities.screens.forgotpassword

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.apia22018.sportactivities.utils.isEmailValid
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel : ViewModel () {

    private val user = FirebaseAuth.getInstance()
    val emailError = MutableLiveData<String>()
    val isComplete = MutableLiveData<Boolean>()

    fun isValid (email : String) {
        if (email.isEmpty()) {
            emailError.value = "Enter email."
        } else if (!isEmailValid(email)) {
            emailError.value = "Invalid email."
        } else {
            emailError.value = null
            sendPasswordReset(email)
        }
    }

    private fun sendPasswordReset (email: String) {
        user.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            isComplete.value = task.isComplete
        }
    }
}