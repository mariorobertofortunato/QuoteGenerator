package com.example.quotegenerator.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [QuoteDB::class], version = 1, exportSchema = false)
abstract class QuoteRoomDatabase : RoomDatabase() {

    abstract val dao: QuoteDao

}

