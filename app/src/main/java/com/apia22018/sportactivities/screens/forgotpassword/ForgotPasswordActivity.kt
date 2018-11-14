package com.apia22018.sportactivities.screens.forgotpassword

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.screens.login.LoginActivity
import kotlinx.android.synthetic.main.forgot_password_activity.*

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var viewModel : ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_activity)
        viewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)

        btn_submit.setOnClickListener {
            val email = et_email.text.toString().trim()
            viewModel.isValid(email)
        }

        viewModel.isComplete.observe(this, Observer {
            it?.let {
                if (it) {
                    finish()
                    LoginActivity.start(this)
                }
            }
        })

        viewModel.emailError.observe(this, Observer {
            passwordToEmail.error = it
        })
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ForgotPasswordActivity::class.java))
        }
    }
}