package com.apia22018.sportactivities.screens.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.text.TextUtils
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    val isComplete = SingleLiveEvent<Boolean>()
    val emailError = MutableLiveData<Int>()
    val passwordError = MutableLiveData<Int>()

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun loginUser(email: String, password: String) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    isComplete.value = task.isSuccessful
                } else {
                    isComplete.value = false
                }
            }
        }
    }

    fun errorCheck(email: String, password: String) {
        if (email.isEmpty()) {
            emailError.value = R.string.enter_email
        } else {
            emailError.value = null
        }
        if (password.isEmpty()) {
            passwordError.value = R.string.enter_password
        } else {
            passwordError.value = null
            loginUser(email, password)
        }
    }
}
