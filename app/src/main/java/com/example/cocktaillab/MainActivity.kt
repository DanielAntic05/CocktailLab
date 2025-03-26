package com.example.cocktaillab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cocktaillab.ui.theme.CocktailLabTheme

import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktaillab.ui.theme.CocktailLabTheme
import com.example.cocktaillab.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // TODO
        //  1) Change the app icon;
        //  2) Change the splash icon;
        //  3) Display Password min and max number of characters;
        //  4) Maybe some general handling and outputs of email and password (@, delete enter, delete space, ...);
        //  5) ...;

        setContent {
            val context = LocalContext.current
            val authViewModel: AuthViewModel = viewModel()

            LaunchedEffect(Unit) {
                if (authViewModel.isUserLoggedIn()) {
                    context.startActivity(Intent(context, HomeActivity::class.java))
                }
                else {
                    context.startActivity(Intent(context, SignInActivity::class.java))
                }
                finish()
            }
        }
   }
}

