package com.example.cocktaillab.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cocktaillab.api.model.Cocktail
import com.example.cocktaillab.viewmodel.SearchViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailDetailsScreen(
    navController: NavController,
    cocktail: Cocktail,
    viewModel: SearchViewModel
) {
    var isFavorite by remember { mutableStateOf(viewModel.isFavorite(cocktail.id)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(cocktail.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            viewModel.addToFavorites(cocktail)
                        } else {
                            viewModel.removeFromFavorites(cocktail.id)
                        }
                    }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = cocktail.imageUrl,
                contentDescription = cocktail.name,
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Basic Info
            Text("Category: ${cocktail.category ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
            Text("Glass: ${cocktail.glass ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)
            Text("Type: ${cocktail.alcoholic ?: "N/A"}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Ingredients Section
            Text("Ingredients:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Column(Modifier.padding(start = 8.dp)) {
                cocktail.getIngredientsWithMeasures().forEach { (ingredient, measure) ->
                    Text(
                        text = "• $ingredient: $measure",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Instructions Section
            Text("Instructions:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cocktail.instructions ?: "No instructions available",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun CocktailSearchResultItem(
    cocktail: Cocktail,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with name and favorite button
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Drink image
            AsyncImage(
                model = cocktail.imageUrl,
                contentDescription = cocktail.name,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // Basic info
            Text("Category: ${cocktail.category ?: "Unknown"}")
            //Text("Alcoholic: ${cocktail.alcoholic ?: "Unknown"}")
            Text("Glass: ${cocktail.glass ?: "Unknown"}")
        }
    }
}