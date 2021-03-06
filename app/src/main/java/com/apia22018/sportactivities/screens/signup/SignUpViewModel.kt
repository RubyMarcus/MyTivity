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
    val passwordRepeatError = MutableLiveData<Int>()

    var isLoading = ObservableBoolean(false)
    var isComplete = SingleLiveEvent<Boolean>()
    var errorMessage : String = ""
    private var sendEmailVerificationComplete = SingleLiveEvent<Boolean>()

    private var fbAuth = FirebaseAuth.getInstance()

    fun isValid(email: String, password: String, repeatPassword: String) {
        var isValid = true

        if (email.isEmpty()) {
            emailError.value = R.string.enter_email
            isValid = false
        } else if (!isEmailValid(email)) {
            emailError.value = R.string.email_invalid
            isValid = false
        }

        if (password.isEmpty()) {
            isValid = false
            passwordError.value = R.string.enter_password
        }
        if (repeatPassword.isEmpty()) {
            isValid = false
            passwordRepeatError.value = R.string.enter_password
        }

        if (password != repeatPassword && (!password.isEmpty() || !repeatPassword.isEmpty())) {
            passwordError.value = R.string.password_no_match
            passwordRepeatError.value = R.string.password_no_match
        } else if(isValid) {
            isLoading.set(true)
            createNewAccount(email, password)
        }
    }

    private fun createNewAccount(email: String, password: String) {
        fbAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    sendEmailVerification()
                    isComplete.value = true
                }.addOnFailureListener {
                    errorMessage = it.localizedMessage
                    isComplete.value = false
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