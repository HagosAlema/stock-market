package com.hagos.data.di

import com.hagos.data.csv.CSVParser
import com.hagos.data.csv.CompanyListingParser
import com.hagos.data.csv.IntradayInfoParser
import com.hagos.data.repository.StockRepositoryImpl
import com.hagos.domain.model.CompanyListing
import com.hagos.domain.model.IntradayInfo
import com.hagos.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindCompanyListParser(
        csvParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayParser(
        intradayParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

}