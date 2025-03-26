package com.example.cocktaillab.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktaillab.repository.FavoriteManager
import com.example.cocktaillab.api.model.Cocktail
import com.example.cocktaillab.repository.CocktailRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


class SearchViewModel(
    private val repository: CocktailRepository,
    private val favoriteManager: FavoriteManager
) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Cocktail>>(emptyList())
    val searchResults: StateFlow<List<Cocktail>> = _searchResults.asStateFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    private var searchJob: Job? = null

    fun searchCocktails(query: String) {
        searchJob?.cancel()

        if (query.length < 2) {
            _searchResults.value = emptyList()
            return
        }

        _isLoading.value = true


        searchJob = viewModelScope.launch {
            delay(300)
            try {
                val results = repository.searchCocktails(query)
                Log.d("CocktailSearch", "Search successful. Results count: ${results.size}")
                _searchResults.value = results
            } catch (e: Exception) {
                Log.e("CocktailSearch", "Search failed for query: '$query'", e)
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _favorites = mutableStateListOf<Cocktail>()
    private val _favoritesFlow = MutableStateFlow<List<Cocktail>>(emptyList())
    val favorites: StateFlow<List<Cocktail>> = _favoritesFlow.asStateFlow()


    fun isFavorite(cocktailId: String): Boolean {
        return _favorites.any { it.id == cocktailId }
    }

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _favorites.addAll(favoriteManager.getFavorites())
            _favoritesFlow.value = _favorites.toList()
        }
    }

    fun addToFavorites(cocktail: Cocktail) {
        if (!_favorites.any { it.id == cocktail.id }) {
            _favorites.add(cocktail)
            _favoritesFlow.value = _favorites.toList()
            favoriteManager.saveFavorites(_favorites.toList())
        }
    }

    fun removeFromFavorites(cocktailId: String) {
        _favorites.removeAll { it.id == cocktailId }
        _favoritesFlow.value = _favorites.toList()
        favoriteManager.saveFavorites(_favorites.toList())
    }
}

