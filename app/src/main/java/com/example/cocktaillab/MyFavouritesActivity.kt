package com.example.cocktaillab

import com.example.cocktaillab.ui.components.CocktailDetailsScreen
import com.example.cocktaillab.ui.components.CocktailSuggestionItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktaillab.api.CocktailApiService
import com.example.cocktaillab.repository.CocktailRepository
import com.example.cocktaillab.viewmodel.SearchViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.cocktaillab.viewmodel.SearchViewModel

class MyFavouritesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FavoritesScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(
            repository = CocktailRepository(
                apiService = Retrofit.Builder()
                    .baseUrl("https://www.thecocktaildb.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CocktailApiService::class.java)
            )
        )
    )
) {
    val navController = rememberNavController()
    val favorites by viewModel.favorites.collectAsState()

    NavHost(navController = navController, startDestination = "favoritesList") {
        composable("favoritesList") {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("My Favorites") }
                    )
                }
            ) { padding ->
                if (favorites.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No favorites yet")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .padding(padding)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(favorites) { cocktail ->
                            CocktailSuggestionItem(
                                cocktail = cocktail,
                                onClick = {
                                    navController.navigate("favoriteDetails/${cocktail.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
        composable("favoriteDetails/{cocktailId}") { backStackEntry ->
            val cocktailId = backStackEntry.arguments?.getString("cocktailId")
            val cocktail = favorites.find { it.id == cocktailId }
            if (cocktail != null) {
                CocktailDetailsScreen(
                    navController = navController,
                    cocktail = cocktail,
                    viewModel = viewModel
                )
            }
        }
    }
}
