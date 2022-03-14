package com.example.quotegenerator.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

const val BASE_URL_0 = "https://zenquotes.io/api/"
const val BASE_URL_1 = "https://api.kanye.rest"
const val BASE_URL_2 = "https://api.fisenko.net/v1/quotes/en/"
const val PICTURE_URL = "https://loremflickr.com/json/g/320/240/"

// This interface represent the group of requests that I can operate with the Api.
interface ApiService {
    @GET("random")
    suspend fun getQuote(): String

    @GET("/")
    suspend fun getQuoteNoValue(): String

    @GET("all")
    suspend fun getPicture(): String
}

private var gson = GsonBuilder()
    .setLenient()
    .create()

//The object Network instantiate the retrofit service "containing" the ApiService
//in order to make it accessible to other classes in the project
object NetworkId0 {
    private val retrofitQuote = Retrofit.Builder()
        .baseUrl(BASE_URL_0)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitServiceId0 : ApiService by lazy { retrofitQuote.create(ApiService::class.java) }
}

object NetworkId1 {
    private val retrofitQuote = Retrofit.Builder()
        .baseUrl(BASE_URL_1)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitServiceId1 : ApiService by lazy { retrofitQuote.create(ApiService::class.java) }
}

object NetworkId2 {
    private val retrofitQuote = Retrofit.Builder()
        .baseUrl(BASE_URL_2)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitServiceId2 : ApiService by lazy { retrofitQuote.create(ApiService::class.java) }
}

object PictureNetwork {
    private val retrofitPicture = Retrofit.Builder()
        .baseUrl(PICTURE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitServicePicture : ApiService by lazy { retrofitPicture.create(ApiService::class.java) }
}

