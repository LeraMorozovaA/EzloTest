package com.example.ezlotest.api

import com.example.ezlotest.api.dto.ItemsResponse
import retrofit2.http.GET

interface ApiService {

    @GET("items.test")
    suspend fun getItemsList(): ItemsResponse

}