package com.example.cocktaillab.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktaillab.api.model.Cocktail


// Quick suggestions while typing.
@Composable
fun CocktailSuggestionItem(
    cocktail: Cocktail,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = cocktail.imageUrl,
                contentDescription = cocktail.name,
                modifier = Modifier.size(64.dp)
            )
            Column {
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                /*
                Text(
                    text = cocktail.category ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                */
            }
        }
    }
}
