package com.example.cocktaillab.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AuthViewModel : ViewModel() {
    private val db = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    fun signIn(email: String, password: String, onResult: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onResult(task.isSuccessful)
            }
    }

    fun signUp(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        saveUserData(userId, email)  // Save user data to Firebase Database
                    }
                    onResult(true, null) // Success, no error message
                }
                else {
                    onResult(false, task.exception?.message) // Failure, send error message
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
