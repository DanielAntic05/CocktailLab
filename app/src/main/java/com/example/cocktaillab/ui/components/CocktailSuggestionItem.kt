package com.example.cocktaillab.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.cocktaillab.api.model.Cocktail
//import com.example.cocktaillab.api.model.IngredientMeasure


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

/*
@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun CocktailSuggestionItemPreview() {
    MaterialTheme {
        val previewCocktail = Cocktail(
            id = "11007",
            name = "Margarita",
            category = "Cocktail",
            alcoholic = "Alcoholic",
            glass = "Cocktail glass",
            instructions = "Rub the rim...",
            imageUrl = "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg"
        ).apply {
            // 'val' cannot be reassigned
            ingredients = listOf(
                IngredientMeasure("Tequila", "1.5 oz"),
                IngredientMeasure("Triple sec", "0.5 oz"),
                IngredientMeasure("Lime juice", "1 oz")
            )
        }

        CocktailSuggestionItem(
            cocktail = previewCocktail,
            onClick = {}
        )
    }
}

 */