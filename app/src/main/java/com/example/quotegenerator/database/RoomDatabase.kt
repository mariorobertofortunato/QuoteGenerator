package com.example.quotegenerator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

/** Defines the database configuration and serves as the app's main access point to the persisted data.*/

/** DB holder class*/
@Database (entities = [QuoteDB::class], version = 1, exportSchema = false)
abstract class QuoteRoomDatabase : RoomDatabase() {
    abstract val quoteDao: QuoteDao
}
/** Create an instance of the RoomDB*/

private lateinit var INSTANCE: QuoteRoomDatabase

fun getDB(context: Context): QuoteRoomDatabase {
    synchronized(RoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(context.applicationContext, QuoteRoomDatabase::class.java, "quotes")
                .build()
        }
    }
    return INSTANCE
}