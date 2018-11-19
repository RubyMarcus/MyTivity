package com.apia22018.sportactivities.screens.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.databinding.LoginActivityBinding
import com.apia22018.sportactivities.screens.containers.DashboardContainerActivity
import com.apia22018.sportactivities.screens.forgotpassword.ForgotPasswordActivity
import com.apia22018.sportactivities.screens.signup.SignUpActivity
import com.apia22018.sportactivities.utils.showSnackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        val binding: LoginActivityBinding = DataBindingUtil.setContentView(this, R.layout.login_activity)
        binding.viewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.executePendingBindings()


        errorObserver()

        initialise()

        viewModel.isComplete.observe(this, Observer { it ->
            it?.let {
                if (it) {
                    DashboardContainerActivity.start(this)
                    finish()
                } else {
                    tv_forgot_password.showSnackbar(getString(R.string.wrong_email_password), Snackbar.LENGTH_SHORT)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {
            if (user.isEmailVerified) {
                DashboardContainerActivity.start(this)
                finish()
            } else {
                tv_forgot_password.showSnackbar(getString(R.string.verify_email), Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun initialise() {
        tv_forgot_password
                .setOnClickListener { ForgotPasswordActivity.start(this) }

        btn_register_account
                .setOnClickListener { SignUpActivity.start(this) }

        btn_login
                .setOnClickListener { loginUser() }
    }

    private fun loginUser() {
        val email = et_email?.text.toString()
        val password = et_password?.text.toString()
        viewModel.errorCheck(email, password)
    }

    private fun errorObserver() {
        viewModel.emailError.observe(this, Observer {
            if (it != null) {
                et_email.error = getString(it)
            }else{
                et_email.error = null
            }
        })
        viewModel.passwordError.observe(this, Observer {
            if (it != null) {
                et_password.error = getString(it)
            }else{
                et_password.error = null
            }
        })
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            context.startActivity(intent)
        }
    }
}