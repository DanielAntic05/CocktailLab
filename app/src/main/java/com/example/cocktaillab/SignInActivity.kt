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


class SignInActivity : ComponentActivity() {
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


                // TODO
                //  Add Password the eye when writing the password to see/hide it.
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Password") })


                Button(onClick = {
                    authViewModel.signIn(email, password) { success ->
                        if (success) {
                            Toast.makeText(context, "Sign In Successful", Toast.LENGTH_SHORT).show()
                            context.startActivity(Intent(context, HomeActivity::class.java))
                            finish()
                        }
                        else {
                            Toast.makeText(context, "Sign In Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text("Sign In")
                }

                Button(onClick = {
                    context.startActivity(Intent(context, SignUpActivity::class.java))
                }) {
                    Text("Go to Sign Up")
                }
            }
        }
    }
}
