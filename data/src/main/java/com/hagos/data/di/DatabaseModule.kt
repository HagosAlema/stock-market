package com.hagos.data.di

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hagos.data.local.StockDao
import com.hagos.data.local.StockDatabase
import dagger.Module
import dagger.Provides
import dagger.assisted.Assisted
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesStockDatabase(
        @ApplicationContext context: Context
    ): StockDatabase = Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            "stock_db"
        ).build()

    @Provides
    @Singleton
    fun providesStockDao(database: StockDatabase): StockDao = database.dao

}