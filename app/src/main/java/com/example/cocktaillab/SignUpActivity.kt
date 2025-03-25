package com.example.cocktaillab

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktaillab.viewmodel.AuthViewModel


class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val authViewModel: AuthViewModel = viewModel()
            val context = LocalContext.current
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })

                Button(onClick = {
                    authViewModel.signUp(email, password) { success, errorMessage ->
                        if (success) {
                            Toast.makeText(context, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                            context.startActivity(Intent(context, HomeActivity::class.java))
                            finish()
                        }
                        else {
                            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text("Sign Up")
                }

                Button(onClick = {
                    context.startActivity(Intent(context, SignInActivity::class.java))
                }) {
                    Text("Go to Sign In")
                }
            }
        }
    }
}
