package com.apia22018.sportactivities.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseHelper {
    companion object {
        private val auth: FirebaseAuth by lazy {
            FirebaseAuth.getInstance()
        }

        fun getFirebaseInstance(): FirebaseAuth? {
            return auth
        }

        fun getCurrentUser(): FirebaseUser? {
            return getFirebaseInstance()?.currentUser
        }
    }
}