package com.example.quotegenerator.di

import android.content.Context
import androidx.room.Room
import com.example.quotegenerator.database.QuoteRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): QuoteRoomDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            QuoteRoomDatabase::class.java, "quotes"
        )
            .build()

}


