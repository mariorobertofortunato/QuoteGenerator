package com.example.quotegenerator.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.quotegenerator.model.Quote

/** Defines the schema of the database, aka the fields of every entity in the DB
 * Each instance of QuoteDB represents a row in a quote table in the app's database.*/
@Entity(tableName = "quotes_db")
data class QuoteDB (
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name="q") val q: String,
    @ColumnInfo(name="a") val a: String)

/** Map Database Quotes to domain entities = convert QuoteDB objects into domain objects.*/
fun List<QuoteDB>.asDomainModel(): List<Quote> {
    return map {
        Quote(
            id = it.id,
            q = it.q,
            a = it.a
        )
    }
}

/**VICEVERSA*/
/*
fun ArrayList<Quote>.asDBModel(): List<QuoteDB> {
    return map {
        QuoteDB(
            q = it.q,
            a = it.a,
            h = it.h,
        )
    }
}*/

fun Quote.asDBModel(): QuoteDB {
    return QuoteDB(id, q, a)
}
