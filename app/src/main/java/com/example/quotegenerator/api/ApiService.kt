package com.example.quotegenerator.api

import com.example.quotegenerator.model.Picture
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


const val BASE_URL_0 = "https://zenquotes.io/api/"
const val BASE_URL_1 = "https://api.kanye.rest"
const val BASE_URL_2 = "https://api.fisenko.net/v1/quotes/en/"
const val PICTURE_URL =  "https://picsum.photos/200"

// This interface represent the group of requests that I can operate with the Api.
interface ApiService {
    @GET("random")
    suspend fun getQuote(): String

    @GET("/")
    suspend fun getQuoteNoValue(): String

    @GET("/")
    suspend fun getPicture(): Picture
}

//The object Network instantiate the retrofit service "containing" the ApiService
//in order to make it accessible to other classes in the project
object NetworkId0 {
    private var gson = GsonBuilder()
        .setLenient()
        .create()
    //Build the Retrofit object for the quote
    private val retrofitQuote = Retrofit.Builder()
        .baseUrl(BASE_URL_0)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitServiceId0 : ApiService by lazy { retrofitQuote.create(ApiService::class.java) }
}

object NetworkId1 {
    private var gson = GsonBuilder()
        .setLenient()
        .create()
    //Build the Retrofit object for the quote
    private val retrofitQuote = Retrofit.Builder()
        .baseUrl(BASE_URL_1)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitServiceId1 : ApiService by lazy { retrofitQuote.create(ApiService::class.java) }
}

object NetworkId2 {
    private var gson = GsonBuilder()
        .setLenient()
        .create()
    //Build the Retrofit object for the quote
    private val retrofitQuote = Retrofit.Builder()
        .baseUrl(BASE_URL_2)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    val retrofitServiceId2 : ApiService by lazy { retrofitQuote.create(ApiService::class.java) }
}

object PictureNetwork {
    private val retrofitPicture = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(PICTURE_URL)
        .build()
    val retrofitServicePicture : ApiService by lazy { retrofitPicture.create(ApiService::class.java)}
}

//Build the Moshi converter used by Retrofit (KotlinJsonAdapterFactory = from JSON object to Kotlin object)
//This is used for the "Image of the day"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

