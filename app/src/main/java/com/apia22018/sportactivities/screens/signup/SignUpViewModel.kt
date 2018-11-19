package com.apia22018.sportactivities.screens.signup

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.utils.SingleLiveEvent
import com.apia22018.sportactivities.utils.isEmailValid
import com.google.firebase.auth.FirebaseAuth

class SignUpViewModel : ViewModel() {

    val emailError = MutableLiveData<Int>()
    val passwordError = MutableLiveData<Int>()
    val passwordRepeat = MutableLiveData<Int>()

    var isLoading = ObservableBoolean(false)
    var isComplete = SingleLiveEvent<Boolean>()
    private var sendEmailVerificationComplete = SingleLiveEvent<Boolean>()

    private var fbAuth = FirebaseAuth.getInstance()

    fun isValid(email: String, password: String, repeatPassword: String) {
        if (email.isEmpty()) {
            emailError.value = R.string.enter_email
        } else if (!isEmailValid(email)) {
            emailError.value = R.string.email_invalid
        }

        if (password.isEmpty()) {
            passwordError.value = R.string.enter_password
        }
        if (repeatPassword.isEmpty()) {
            passwordRepeat.value = R.string.enter_password
            return
        }

        if (password != repeatPassword && (!password.isEmpty() || !repeatPassword.isEmpty())) {
            passwordError.value = R.string.password_no_match
            passwordRepeat.value = R.string.password_no_match
            return
        } else if (isEmailValid(email)) {
            isLoading.set(true)
            createNewAccount(email, password)
        }
    }

    private fun createNewAccount(email: String, password: String) {
        fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isComplete) {
                        sendEmailVerification()
                        isComplete.value = it.isComplete
                    } else {
                        isComplete.value = false
                    }
                }
    }

    private fun sendEmailVerification() {
        val user = fbAuth.currentUser
        user?.let {
            it.sendEmailVerification()
                    .addOnCompleteListener { task ->
                        if(task.isComplete) {
                            sendEmailVerificationComplete.value = task.isComplete
                        } else {
                            sendEmailVerificationComplete.value = false
                        }
                    }
        }
    }
}