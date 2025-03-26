package com.example.cocktaillab.api.model

import android.util.Log
import com.google.gson.annotations.SerializedName


data class Cocktail(
    @SerializedName("idDrink") val id: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val imageUrl: String?,
    @SerializedName("strCategory") val category: String?,
    @SerializedName("strGlass") val glass: String?,
    @SerializedName("strAlcoholic") val alcoholic: String?,
    @SerializedName("strInstructions") val instructions: String?,

    @SerializedName("strIngredient1") val ingredient1: String?,
    @SerializedName("strIngredient2") val ingredient2: String?,
    @SerializedName("strIngredient3") val ingredient3: String?,
    @SerializedName("strMeasure1") val measure1: String?,
    @SerializedName("strMeasure2") val measure2: String?,
    @SerializedName("strMeasure3") val measure3: String?
) {
    companion object {
        private const val TAG = "CocktailDebug"
    }

    fun getIngredientsWithMeasures(): List<Pair<String, String>> {
        val ingredients = listOfNotNull(
            ingredient1?.let { it to (measure1 ?: "") },
            ingredient2?.let { it to (measure2 ?: "") },
            ingredient3?.let { it to (measure3 ?: "") }
        ).filter { it.first.isNotBlank() }

        // Debug output
        Log.d(TAG, "Processing ingredients for cocktail: $name")
        Log.d(TAG, "Raw ingredients data:")
        Log.d(TAG, "Ingredient1: $ingredient1 | Measure1: $measure1")
        Log.d(TAG, "Ingredient2: $ingredient2 | Measure2: $measure2")
        Log.d(TAG, "Ingredient3: $ingredient3 | Measure3: $measure3")
        Log.d(TAG, "Filtered result: $ingredients")

        return ingredients
    }
}

data class CocktailResponse(
    @SerializedName("drinks") val cocktails: List<Cocktail>?
)
