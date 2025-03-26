package com.example.cocktaillab

import android.content.Context
import com.example.cocktaillab.ui.components.CocktailSuggestionItem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cocktaillab.api.CocktailApiService
import com.example.cocktaillab.repository.CocktailRepository
import com.example.cocktaillab.repository.FavoriteManager
import com.example.cocktaillab.ui.components.CocktailDetailsScreen
import com.example.cocktaillab.ui.components.CocktailSearchResultItem
import com.example.cocktaillab.viewmodel.SearchViewModel
import com.example.cocktaillab.viewmodel.SearchViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchAlcoholActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val favoriteManager = FavoriteManager(this)
        val apiService = Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CocktailApiService::class.java)

        val repository = CocktailRepository(apiService)
        val viewModelFactory = SearchViewModelFactory(repository, favoriteManager)
        val viewModel: SearchViewModel by viewModels { viewModelFactory }

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RealTimeSearchScreen(viewModel = viewModel)
                }
            }
        }
    }
}


@Composable
fun RealTimeSearchScreen(
    context: Context = LocalContext.current,
    viewModel: SearchViewModel = viewModel(
        factory = SearchViewModelFactory(
            repository = CocktailRepository(
                apiService = Retrofit.Builder()
                    .baseUrl("https://www.thecocktaildb.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CocktailApiService::class.java)
            ),
            favoriteManager = FavoriteManager(context)
        )
    )
) {
    val navController = rememberNavController()
    val searchResults by viewModel.searchResults.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "search"
    ) {
        composable("search") {
            SearchScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable("details/{cocktailId}") { backStackEntry ->
            val cocktailId = backStackEntry.arguments?.getString("cocktailId")
            val cocktail = searchResults.find { it.id == cocktailId }
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

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var showSingleDrinkDetails by remember { mutableStateOf(false) }
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading

    Column(modifier = Modifier.padding(16.dp)) {
        // Search Bar (Always visible)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                showSingleDrinkDetails = false // Show suggestions while typing
                viewModel.searchCocktails(it)
            },
            label = { Text("Search cocktails...") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = {
                    if (searchQuery.isNotEmpty()) {
                        showSingleDrinkDetails = true // Switch to detailed view
                        viewModel.searchCocktails(searchQuery)
                    }
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            singleLine = true,
            keyboardActions = KeyboardActions(onSearch = {
                if (searchQuery.isNotEmpty()) {
                    showSingleDrinkDetails = true // Switch to detailed view
                    viewModel.searchCocktails(searchQuery)
                }
            })
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (showSingleDrinkDetails) {
            // Detailed Card View (after Enter/Search click)

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(searchResults) { cocktail ->
                    CocktailSearchResultItem(
                        cocktail = cocktail,
                        isFavorite = viewModel.isFavorite(cocktail.id),
                        onFavoriteClick = {
                            if (viewModel.isFavorite(cocktail.id)) {
                                viewModel.removeFromFavorites(cocktail.id)
                            }
                            else {
                                viewModel.addToFavorites(cocktail)
                            }
                        },
                        onClick = {
                            navController.navigate("details/${cocktail.id}")
                        }
                    )
                }
            }
        }
        else {
            // Simple Suggestions (while typing)
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(searchResults) { cocktail ->
                    CocktailSuggestionItem(
                        cocktail = cocktail,
                        onClick = {
                            navController.navigate("details/${cocktail.id}")
                        }
                    )
                }
            }
        }
    }
}

