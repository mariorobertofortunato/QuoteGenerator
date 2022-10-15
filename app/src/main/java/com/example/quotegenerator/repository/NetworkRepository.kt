package com.example.quotegenerator.repository

import com.example.quotegenerator.network.ApiService
import javax.inject.Inject
import javax.inject.Named


class NetworkRepository @Inject constructor(@Named("ServiceQuoteValue1") private val apiServiceQuoteValue1: ApiService,
                                            @Named("ServiceQuoteValue2") private val apiServiceQuoteValue2: ApiService,
                                            @Named("ServiceQuoteNoValue") private val apiServiceQuoteNoValue: ApiService,
                                            @Named("ServicePicture") private val apiServicePicture: ApiService) {

    suspend fun getQuoteValue1(): String = apiServiceQuoteValue1.getQuote()

    suspend fun getQuoteValue2(): String = apiServiceQuoteValue2.getQuote()

    suspend fun getQuoteNoValue(): String = apiServiceQuoteNoValue.getQuoteNoValue()

    suspend fun getPicture(): String = apiServicePicture.getPicture()

}
