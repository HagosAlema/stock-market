package com.hagos.data.repository

import android.util.Log
import com.hagos.data.csv.CSVParser
import com.hagos.data.local.StockDao
import com.hagos.data.mapper.toCompanyInfo
import com.hagos.data.mapper.toCompanyListing
import com.hagos.data.mapper.toCompanyListingEntity
import com.hagos.data.remote.StockApi
import com.hagos.domain.model.CompanyInfo
import com.hagos.domain.model.CompanyListing
import com.hagos.domain.model.IntradayInfo
import com.hagos.domain.repository.StockRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val dao: StockDao,
    private val csvParser: CSVParser<CompanyListing>,
    private val intradayParser: CSVParser<IntradayInfo>
) : StockRepository {
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): List<CompanyListing> {
        try {
            val localListings = dao.searchCompanyListing(query)
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            return if (shouldJustLoadFromCache) {
                localListings.map { it.toCompanyListing() }
            } else {
                val remoteListings = api.getListings()
                val companyListing = csvParser.parse(remoteListings.byteStream())
                if (companyListing.isNotEmpty()) {
                    dao.clearCompanyListings()
                    dao.insertCompanyListings(companyListing.map { it.toCompanyListingEntity() })
                }
                dao.searchCompanyListing(query)
                    .map { it.toCompanyListing() } //Always return data from DB cuz of single source of truth
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            throw e
        }
    }

    override suspend fun getIntradayInfo(symbol: String): List<IntradayInfo> {
        try {
            val response = api.getIntradayInfo(symbol)
            return intradayParser.parse(response.byteStream())
        } catch (t: Throwable) {
            t.printStackTrace()
            throw t
        }
    }

    override suspend fun getCompanyInfo(symbol: String): CompanyInfo {
        try {
            val result = api.getCompanyInfo(symbol)
            Log.e("RESULT: ", result.toString())
            return result.toCompanyInfo()
        } catch (t: Throwable) {
            t.printStackTrace()
            throw t
        }
    }
}