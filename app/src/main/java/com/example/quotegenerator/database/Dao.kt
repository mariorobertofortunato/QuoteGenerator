package com.example.quotegenerator.database

import androidx.room.*
import com.example.quotegenerator.model.Quote
import kotlinx.coroutines.flow.Flow

/** Defines the methods for accessing the DB aka
 * provides the methods that the rest of the app uses to interact with data in the quote_db table.*/
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