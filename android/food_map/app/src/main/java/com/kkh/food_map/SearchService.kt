package com.kkh.food_map

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("v1/search/local.json")
    fun getGoodRestaurant(
        @Query("query") query: String,
        @Query("display") display: Int = 5,
    ): Call<SearchResult>
}