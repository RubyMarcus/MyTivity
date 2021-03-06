package com.apia22018.sportactivities.screens.signup

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.EditText
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.SignUpActivityBinding
import com.apia22018.sportactivities.screens.login.LoginActivity
import com.apia22018.sportactivities.utils.showSnackbar
import com.apia22018.sportactivities.utils.userTouchDisabled
import com.apia22018.sportactivities.utils.userTouchEnabled
import kotlinx.android.synthetic.main.sign_up_activity.*

class SignUpActivity : AppCompatActivity() {

    lateinit var viewModel : SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        val binding : SignUpActivityBinding = DataBindingUtil.setContentView(this, R.layout.sign_up_activity)
        binding.viewModel = viewModel
        binding.executePendingBindings()
        binding.setLifecycleOwner(this)

        errorObservers()

        signUpButton?.setOnClickListener {
            createAccount()
        }

        viewModel.isComplete.observe(this, Observer {
            it?.let { value ->
               if(value) {
                   viewModel.isLoading.set(false)
                   userTouchEnabled(window)
                   LoginActivity.start(this)
                   finish()
               } else {
                   viewModel.isLoading.set(false)
                   userTouchEnabled(window)
                   binding.root.showSnackbar(viewModel.errorMessage, Snackbar.LENGTH_LONG)
               }
            }
        })
    }

    private fun errorObservers () {
        viewModel.emailError.observe(this, Observer {
            if (it != null) {
                emailField.error = getString(it)
            }
            userTouchEnabled(window)
        })

        viewModel.passwordError.observe(this, Observer {
            if (it != null) {
                passwordField.error = getString(it)
            }
            userTouchEnabled(window)
        })

        viewModel.passwordRepeatError.observe(this, Observer {
            if (it != null) {
                repeatPasswordField.error = getString(it)
            }
            userTouchEnabled(window)
        })
    }

    //When should you use trim()? always?
    private fun createAccount () {
        userTouchDisabled(window)

        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val repeatPassword = repeatPasswordField.text.toString().trim()

        viewModel.isValid(email, password, repeatPassword)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent)
        }
    }
}