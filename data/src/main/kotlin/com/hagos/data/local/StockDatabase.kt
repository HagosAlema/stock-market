package com.hagos.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        CompanyListingEntity::class,
        CompanyInfoEntity::class,
        IntradayInfoEntity::class
    ],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
abstract class StockDatabase : RoomDatabase() {
    abstract val dao: StockDao
}

private class DatabaseAutoMigrate: AutoMigrationSpec
