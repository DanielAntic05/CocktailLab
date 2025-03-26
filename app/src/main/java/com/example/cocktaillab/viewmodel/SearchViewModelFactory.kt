// Create this in a new file: viewmodel/SearchViewModelFactory.kt
/*
package com.example.cocktaillab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktaillab.repository.CocktailRepository

class SearchViewModelFactory(
    private val repository: CocktailRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
*/

package com.example.cocktaillab.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktaillab.repository.CocktailRepository
import com.example.cocktaillab.repository.FavoriteManager

class SearchViewModelFactory(
    private val repository: CocktailRepository,
    private val favoriteManager: FavoriteManager  // Add this parameter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository, favoriteManager) as T  // Pass both dependencies
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}