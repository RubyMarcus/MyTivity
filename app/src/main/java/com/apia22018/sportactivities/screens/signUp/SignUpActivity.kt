package com.apia22018.sportactivities.screens.signUp

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
import android.widget.Toast
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.screens.login.LoginActivity
import com.apia22018.sportactivities.utils.showSnackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpActivity : AppCompatActivity() {

    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etRepeatPassword: EditText? = null
    private var btnCreateAccount: Button? = null

    private val TAG = "CreateAccountActivity"
//global variables

    private var email: String? = null
    private var password: String? = null
    private var repeatpassword: String? = null

    //Firebase-referenser
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_activity)


        initialise()
    }

    private fun initialise() {
        etEmail = findViewById<View>(R.id.emailField) as EditText
        etPassword = findViewById<View>(R.id.passwordField) as EditText
        etRepeatPassword = findViewById<View>(R.id.repeatPasswordField) as EditText
        btnCreateAccount = findViewById<View>(R.id.signUpButton) as Button
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }


    private fun validateForm(): Boolean {
        var valid = true
        val email = etEmail?.text.toString()
        if (TextUtils.isEmpty(email)) {
            etEmail?.error = "Required."
            valid = false
        } else {
            etEmail?.error = null
        }
        val password = etPassword?.text.toString()
        if (TextUtils.isEmpty(password)) {
            etPassword?.error = "Required."
            valid = false
        } else {
            etPassword?.error = null
        }
        val cPassword = etRepeatPassword?.text.toString()
        if (TextUtils.isEmpty(cPassword)) {
            etRepeatPassword?.error = "Required"
            valid = false
        } else {
            etRepeatPassword?.error = null
        }
        if (password != cPassword) {
            valid = false
            etPassword?.error = "Password does not match"
            etRepeatPassword?.error = "Password does not match"
        } else {
            etPassword?.error = null
            etRepeatPassword?.error = null
        }

        return valid
    }

    private fun createNewAccount() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (validateForm()){

            mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val userId = mAuth!!.currentUser!!.uid
                            //Verifiera email
                            verifyEmail()
                            //uppdatera username
                            /*
                            val emailForUsername = email

                            val index = emailForUsername!!.indexOf('@')
                            val username: String? = if(index == -1) null else emailForUsername.substringBefore('@', )
                            val currentUserDb = mDatabaseReference!!.child(userId)
                            currentUserDb.child("userName").setValue(username)
                            */
                            LoginActivity.start(this)
                        } else {
                            // Signin fail meddelande
                            Log.w(TAG, "createUserWithEmail:failed", task.exception)
                            etEmail?.showSnackbar(getString(R.string.not_registered),Snackbar.LENGTH_SHORT)
                        }
                    }

        } else {
            etEmail?.showSnackbar(getString(R.string.enter_email_password),Snackbar.LENGTH_SHORT)
        }
finish()
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()

                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        etEmail?.showSnackbar(getString(R.string.repeat_password),Snackbar.LENGTH_SHORT)
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.exception)
                        etEmail?.showSnackbar(getString(R.string.enter_email),Snackbar.LENGTH_SHORT)
                    }
                }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }
    }
}

