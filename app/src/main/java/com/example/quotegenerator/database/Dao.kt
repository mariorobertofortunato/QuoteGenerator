package com.example.quotegenerator.database

import androidx.room.*
import com.example.quotegenerator.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Query("SELECT * FROM quotes_db")
    suspend fun getAll(): List<QuoteDB>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuote (quoteDB: QuoteDB)

    @Query("delete from quotes_db")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteQuote (quoteDB: QuoteDB)

}