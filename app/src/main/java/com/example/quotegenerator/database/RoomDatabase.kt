package com.example.quotegenerator.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/** Defines the database configuration and serves as the app's main access point to the persisted data.*/

/** DB holder class*/
@Database (entities = [QuoteDB::class], version = 1)
abstract class QuoteRoomDatabase : RoomDatabase() {
    abstract val dao: Dao
}
/** Create an instance of the RoomDB with the method getDB*/

private lateinit var INSTANCE: RoomDatabase

fun getDB(context: Context): RoomDatabase {
    synchronized(RoomDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room
                .databaseBuilder(context.applicationContext, RoomDatabase::class.java, "quotes")
                .build()
        }
    }
    return INSTANCE
}