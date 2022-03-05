package com.example.quotegenerator.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


const val BASE_URL = "https://zenquotes.io/api/"

// This interface represent the group of requests that I can operate with the Api.
interface ApiService {
    @GET("random")
     suspend fun getQuote() : String
}

//The object Network instantiate the retrofit service "containing" the ApiService
//in order to make it accessible to other classes in the project
object Network {

    private var gson = GsonBuilder()
        .setLenient()
        .create()
    //Build the Retrofit object for the quote
    private val retrofitQuote = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val retrofitService : ApiService by lazy { retrofitQuote.create(ApiService::class.java) }

}

