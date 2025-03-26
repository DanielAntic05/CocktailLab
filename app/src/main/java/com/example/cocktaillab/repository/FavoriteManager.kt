package com.example.cocktaillab.repository

import android.content.Context
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.cocktaillab.api.model.Cocktail


class FavoriteManager(context: Context) {
    private val prefs = context.getSharedPreferences("COCKTAIL_FAVORITES", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val auth = FirebaseAuth.getInstance()

    // Key format: "user_email_favorites"
    private fun getUserKey(): String {
        return "${auth.currentUser?.email ?: "guest"}_favorites"
    }

    fun saveFavorites(cocktails: List<Cocktail>) {
        prefs.edit {
            putString(getUserKey(), gson.toJson(cocktails))
        }
    }

    fun getFavorites(): List<Cocktail> {
        val json = prefs.getString(getUserKey(), null) ?: return emptyList()
        val type = object : TypeToken<List<Cocktail>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }
}