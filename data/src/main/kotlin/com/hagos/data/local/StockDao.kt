package com.hagos.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(companyListingEntity: List<CompanyListingEntity>)

    @Query("DELETE FROM CompanyListingEntity")
    suspend fun clearCompanyListings()

    @Query("""
        SELECT * 
        FROM CompanyListingEntity
        WHERE LOWER(name) LIKE '%' || LOWER(:query)||'%' OR
        UPPER(:query) == symbol
    """)
    suspend fun searchCompanyListing(query: String) : List<CompanyListingEntity>

    @Upsert
    suspend fun insertCompanyInfo(companyInfoEntity: CompanyInfoEntity)

    @Query("SELECT * FROM CompanyInfoEntity WHERE symbol=:symbol LIMIT 1")
    suspend fun getCompanyInfo(symbol: String): CompanyInfoEntity?

    @Query("""
        DELETE FROM IntradayInfoEntity WHERE symbol=:symbol
    """)
    suspend fun clearIntradayInfo(symbol: String)
    @Insert
    suspend fun insertIntradayInfo(intradayInfoEntity: IntradayInfoEntity)

    @Query("SELECT * FROM IntradayInfoEntity WHERE symbol=:symbol")
    suspend fun getIntradayInfo(symbol: String): List<IntradayInfoEntity>
}