package com.apia22018.sportactivities.screens.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.text.TextUtils
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {

    val isLoading = ObservableBoolean(false)
    val isComplete = SingleLiveEvent<Boolean>()
    val isVerified = SingleLiveEvent<Boolean>()
    val emailError = MutableLiveData<Int>()
    val passwordError = MutableLiveData<Int>()
    var errorMessage: String = ""

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun loginUser(email: String, password: String) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener { task ->
                val user = mAuth.currentUser
                user?.let {
                    if (!it.isEmailVerified) {
                        isVerified.value = false
                    } else {
                        isComplete.value = true
                    }
                }
            }
                    .addOnFailureListener {
                        errorMessage = it.localizedMessage
                        isComplete.value = false
                    }
        }
    }

    fun errorCheck(email: String, password: String) {
        if (email.isEmpty()) emailError.value = R.string.enter_email
        if (password.isEmpty()) passwordError.value = R.string.enter_password
        if (email.isNotEmpty() && password.isNotEmpty()) {
            isLoading.set(true)
            loginUser(email, password)
        }
    }
}

