package com.example.cocktaillab.api.model

import com.google.gson.annotations.SerializedName
import com.google.firebase.firestore.Exclude


data class Cocktail(
    @SerializedName("idDrink") val id: String,
    @SerializedName("strDrink") val name: String,
    @SerializedName("strDrinkThumb") val imageUrl: String,
)

data class CocktailResponse(
    @SerializedName("drinks") val cocktails: List<Cocktail>? // Make nullable
)
