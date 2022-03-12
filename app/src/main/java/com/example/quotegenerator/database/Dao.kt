package com.example.quotegenerator.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.quotegenerator.model.Quote

/** Defines the methods for accessing the DB aka
 * provides the methods that the rest of the app uses to interact with data in the quote_db table.*/
@Dao
interface Dao {

    @Query("SELECT * FROM quotes_db")
    fun getAll(): List<Quote>

    @Insert
    fun insertQuote (quote: Quote)

    @Delete
    fun deleteQuote (quote: Quote)

}