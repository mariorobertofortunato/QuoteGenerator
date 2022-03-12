package com.example.quotegenerator.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quotegenerator.model.Quote


/** Defines the schema of the database, aka the fields of every entity in the DB
 * Each instance of Quote represents a row in a quote table in the app's database.*/
@Entity (tableName = "quotes_db")
data class QuoteDB constructor(
    @PrimaryKey
    var q: String,
    var a: String,
    var h: String)

/** Map Database Quotes to domain entities =
 * convert QuoteDB objects into domain objects.
 */
fun List<QuoteDB>.asDomainModel(): List<Quote> {
    return map {
        Quote(
            q = it.q,
            a = it.a,
            h = it.h,
        )
    }
}
/**VICEVERSA*/
fun ArrayList<Quote>.asDBModel(): List<QuoteDB> {
    return map {
        QuoteDB(
            q = it.q,
            a = it.a,
            h = it.h,
        )
    }
}