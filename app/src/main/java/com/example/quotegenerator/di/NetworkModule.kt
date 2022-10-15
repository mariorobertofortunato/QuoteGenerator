package com.example.quotegenerator.di

import com.example.quotegenerator.network.*
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

const val BASE_URL_0 = "https://zenquotes.io/api/"
const val BASE_URL_1 = "https://api.kanye.rest"
const val BASE_URL_2 = "https://api.fisenko.net/v1/quotes/en/"
const val PICTURE_URL = "https://loremflickr.com/json/g/320/240/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("ServiceQuoteValue1")
    fun provideRetrofit0() : ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL_0)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .create()))
        .build().create(ApiService::class.java)

    @Provides
    @Singleton
    @Named("ServiceQuoteNoValue")
    fun provideRetrofit1() : ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL_1)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()))
        .build().create(ApiService::class.java)

    @Provides
    @Singleton
    @Named("ServiceQuoteValue2")
    fun provideRetrofit2() : ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL_2)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()))
        .build().create(ApiService::class.java)

    @Provides
    @Singleton
    @Named("ServicePicture")
    fun provideRetrofitImage() : ApiService = Retrofit.Builder()
        .baseUrl(PICTURE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()))
        .build().create(ApiService::class.java)






}