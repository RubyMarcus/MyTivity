package com.apia22018.sportactivities.screens.login

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.screens.containers.DashboardContainerActivity
import com.apia22018.sportactivities.screens.forgotpassword.ForgotPasswordActivity
import com.apia22018.sportactivities.screens.signUp.SignUpActivity
import com.apia22018.sportactivities.utils.showSnackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //global variables
    private var email: String? = null
    private var password: String? = null

    //UI elements
    private var tvForgotPassword: TextView? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var btnCreateAccount: TextView? = null
    private var mProgressBar: ProgressDialog? = null

    //Firebase references
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        initialise()
    }

    override fun onStart() {
        super.onStart()
        val user = mAuth.currentUser
        if (user != null) {
            if (user.isEmailVerified) {
                DashboardContainerActivity.start(this)
                finish()
            } else {
                tvForgotPassword?.showSnackbar("Please verify email.", Snackbar.LENGTH_SHORT)
            }
        }
    }



    private fun initialise() {
        tvForgotPassword = findViewById<View>(R.id.tv_forgot_password) as TextView
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnLogin = findViewById<View>(R.id.btn_login) as Button
        btnCreateAccount = findViewById<View>(R.id.btn_register_account) as TextView
        mProgressBar = ProgressDialog(this)

        tvForgotPassword!!
                 .setOnClickListener { ForgotPasswordActivity.start(this)}

        btnCreateAccount!!
                 .setOnClickListener { SignUpActivity.start(this)}

        btnLogin!!.setOnClickListener { loginUser() }
    }
    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgressBar!!.setMessage("Logging in....")
            mProgressBar!!.show()
            Log.d(TAG, "Logging in user.")
            mAuth.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->
                        mProgressBar!!.hide()
                        if (task.isSuccessful) {
                            // Sign in success, update UI with signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            if(mAuth.currentUser!!.isEmailVerified) {
                                DashboardContainerActivity.start(this)
                                finish()
                            } else {
                                tvForgotPassword?.showSnackbar("Please verify email.", Snackbar.LENGTH_SHORT)
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithEmail:failure", task.exception)
                                tvForgotPassword?.showSnackbar(getString(R.string.wrong_email_password), Snackbar.LENGTH_SHORT)
                        }
                    }
        } else {
            tvForgotPassword?.showSnackbar(getString(R.string.enter_email_password),Snackbar.LENGTH_SHORT)
        }
    }


    companion object {
        fun start (context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}