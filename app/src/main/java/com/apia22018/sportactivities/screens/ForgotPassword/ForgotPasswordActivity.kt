package com.apia22018.sportactivities.screens.ForgotPassword

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.apia22018.sportactivities.R
import com.apia22018.sportactivities.screens.login.LoginActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var mEmail : EditText
    lateinit var mForgetBtn : Button

    lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_activity)

        mEmail = findViewById(R.id.et_email)
        mForgetBtn = findViewById(R.id.btn_submit)

        mAuth = FirebaseAuth.getInstance()

        mForgetBtn.setOnClickListener {

            val email = mEmail.text.toString().trim()

            if(email.isEmpty()){
                mEmail.error = "Enter email"
                return@setOnClickListener
            }

            forGetPassword(email)
        }
    }

    private fun forGetPassword(email: String) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener { task: Task<Void> ->
            if(task.isComplete){
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)

                Toast.makeText(applicationContext, "Please check email to reset password", Toast.LENGTH_LONG).show()

            }
        }
    }
}