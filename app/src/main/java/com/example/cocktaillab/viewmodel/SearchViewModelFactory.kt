package com.example.cocktaillab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktaillab.repository.CocktailRepository
import com.example.cocktaillab.repository.FavoriteManager


class SearchViewModelFactory(
    private val repository: CocktailRepository,
    private val favoriteManager: FavoriteManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository, favoriteManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}