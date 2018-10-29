package com.apia22018.sportactivities.screens.signUp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.screens.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUpActivity : AppCompatActivity() {

    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null

    private val TAG = "CreateAccountActivity"
//global variables

    private var email: String? = null
    private var password: String? = null

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
        btnCreateAccount = findViewById<View>(R.id.signUpButton) as Button
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }


    private fun createNewAccount() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mAuth!!
                    .createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val userId = mAuth!!.currentUser!!.uid
                            //Verifiera email
                            verifyEmail()
                            //updatera username
                            /*
                            val emailForUsername = email
                             /
                            val index = emailForUsername!!.indexOf('@')
                            val username: String? = if (index == -1) null else emailForUsername.substringBefore('@', )
                            val currentUserDb = mDatabaseReference!!.child(userId)
                            currentUserDb.child("userName").setValue(userName)
                            */

                            LoginActivity.start(this)
                        } else {
                            // Signin fail meddelande
                            Log.w(TAG, "createUserWithEmail:failed", task.exception)
                            Toast.makeText(this@SignUpActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }


        } else {
            Toast.makeText(this, "Enter all details please.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@SignUpActivity,
                                "Verification email sent to " + mUser.getEmail(),
                                Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.exception)
                        Toast.makeText(this@SignUpActivity,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SignUpActivity::class.java))
        }
    }

}

