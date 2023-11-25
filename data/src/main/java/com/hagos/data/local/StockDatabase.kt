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
    version = 4,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = DatabaseAutoMigrate::class),
        AutoMigration(from = 2, to = 3, spec = DatabaseAutoMigrate::class),
        AutoMigration(from = 3, to = 4, spec = DatabaseAutoMigrate::class)
    ]
)
abstract class StockDatabase : RoomDatabase() {
    abstract val dao: StockDao
}

private class DatabaseAutoMigrate: AutoMigrationSpec
