package com.apia22018.sportactivities.screens.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.screens.containers.DashboardContainerActivity
import com.apia22018.sportactivities.screens.forgotpassword.ForgotPasswordActivity
import com.apia22018.sportactivities.screens.signUp.SignUpActivity
import com.apia22018.sportactivities.utils.showSnackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_activity.*

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    private val TAG = "LoginActivity"

    //Firebase references
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

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
            DashboardContainerActivity.start(this)
            finish()
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
            emailSomething.error = it
        })
        viewModel.passwordError.observe(this, Observer {
            passwordSomething.error = it
        })
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}
