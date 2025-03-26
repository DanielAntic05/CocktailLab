package com.example.cocktaillab.api

import com.example.cocktaillab.api.model.CocktailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CocktailApiService {
    @GET("api/json/v1/1/search.php")
    suspend fun searchCocktailsByName(
        @Query("s") query: String
    ): Response<CocktailResponse>
}
