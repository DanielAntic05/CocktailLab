package com.example.cocktaillab.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AuthViewModel : ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()


    fun signIn(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            onResult(false, "Please fill in all fields")
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onResult(true, null)
                } else {
                    onResult(false, task.exception?.message)
                }
            }
    }

    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        when {
            email.isBlank() || password.isBlank() -> {
                onResult(false, "Please fill in all fields")
                return
            }
            password.length < 6 -> {
                onResult(false, "Password must be at least 6 characters")
                return
            }
            else -> {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            if (userId != null) {
                                saveUserData(userId, email)
                            }
                            onResult(true, null)
                        } else {
                            onResult(false, task.exception?.message)
                        }
                    }
            }
        }
    }


    private fun saveUserData(userId: String, email: String) {
        val user = mapOf(
            "email" to email
        )
        db.child("users").child(userId).setValue(user)
            .addOnSuccessListener {
                Log.d("Firebase", "User data saved successfully!")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Failed to save user data", e)
            }
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
