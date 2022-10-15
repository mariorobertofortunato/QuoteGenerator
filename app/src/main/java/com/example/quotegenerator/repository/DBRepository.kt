package com.example.quotegenerator.repository

import com.example.quotegenerator.database.QuoteDB
import com.example.quotegenerator.database.QuoteRoomDatabase
import javax.inject.Inject

class DBRepository @Inject constructor(private val database: QuoteRoomDatabase) {

    suspend fun getAll(): List<QuoteDB> = database.dao.getAll()

    suspend fun insertQuote(quoteDB: QuoteDB) = database.dao.insertQuote(quoteDB)

    suspend fun deleteAll() = database.dao.deleteAll()

    suspend fun deleteQuote(quoteDB: QuoteDB) = database.dao.deleteQuote(quoteDB)
}