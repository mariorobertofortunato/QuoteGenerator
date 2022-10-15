package com.example.quotegenerator.network

import retrofit2.http.GET

interface ApiService {
    @GET("random")
    suspend fun getQuote(): String

    @GET("all")
    suspend fun getPicture(): String

    @GET("/")
    suspend fun getQuoteNoValue(): String

}



