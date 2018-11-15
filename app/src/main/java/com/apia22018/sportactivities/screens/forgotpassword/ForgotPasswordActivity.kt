package com.apia22018.sportactivities.screens.forgotpassword

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.AddActivityBinding
import com.apia22018.sportactivities.databinding.ForgotPasswordActivityBinding
import com.apia22018.sportactivities.screens.login.LoginActivity
import kotlinx.android.synthetic.main.forgot_password_activity.*

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForgotPasswordViewModel::class.java)

        val binding: ForgotPasswordActivityBinding = DataBindingUtil.setContentView(this, R.layout.forgot_password_activity)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()

        btn_submit.setOnClickListener {
            val email = et_email.text.toString().trim()
            viewModel.isValid(email)
        }

        viewModel.isComplete.observe(this, Observer { it ->
            it?.let {
                if (it) {
                    LoginActivity.start(this)
                    finish()
                }
            }
        })

        viewModel.emailError.observe(this, Observer {
            passwordToEmail.error = it
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ForgotPasswordActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent)
        }
    }
}