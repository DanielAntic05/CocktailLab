package com.example.cocktaillab

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(
                onSearchAlcoholClick = { startActivity(Intent(this, SearchAlcoholActivity::class.java)) },
                //onSearchRandomClick = { startActivity(Intent(this, SearchRandomAlcoholActivity::class.java)) },
                onMyFavoritesClick = { startActivity(Intent(this, MyFavouritesActivity::class.java)) },
                onLogoutClick = {
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignInActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
                    finish()
                }
            )
        }
    }
}

@Composable
fun HomeScreen(
    onSearchAlcoholClick: () -> Unit,
    /*onSearchRandomClick: () -> Unit,*/
    onMyFavoritesClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MainButton(text = "Search Alcohol", onClick = onSearchAlcoholClick)
            Spacer(modifier = Modifier.height(16.dp))
            //MainButton(text = "Random Alcohol", onClick = onSearchRandomClick)
            Spacer(modifier = Modifier.height(16.dp))
            MainButton(text = "My Favorites", onClick = onMyFavoritesClick)
            Spacer(modifier = Modifier.height(16.dp))
            MainButton(text = "Log Out", onClick = onLogoutClick, backgroundColor = Color.Red, textColor = Color.White)
        }
    }
}

@Composable
fun MainButton(text: String, onClick: () -> Unit, backgroundColor: Color = Color(0xFF1CF7FF), textColor: Color = Color.Black) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        modifier = Modifier
            .height(50.dp)
            .width(270.dp)
    ) {
        Text(text = text, fontSize = 18.sp, color = textColor)
    }
}
