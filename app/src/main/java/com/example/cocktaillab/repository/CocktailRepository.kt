package com.example.cocktaillab.repository

import android.util.Log
import com.example.cocktaillab.api.CocktailApiService
import com.example.cocktaillab.api.model.Cocktail


class CocktailRepository(private val apiService: CocktailApiService) {
    // In CocktailRepository.kt
    suspend fun searchCocktails(query: String): List<Cocktail> {
        return try {
            val response = apiService.searchCocktailsByName(query)
            Log.d("API", "Raw response: ${response.raw()}")
            response.body()?.cocktails ?: emptyList() // Handles null drinks
        }
        catch (e: Exception) {
            Log.e("API", "Search failed", e)
            emptyList()
        }
    }
}