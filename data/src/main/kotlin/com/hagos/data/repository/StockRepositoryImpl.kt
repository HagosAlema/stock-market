package com.hagos.data.repository

import com.hagos.data.csv.CSVParser
import com.hagos.data.local.IntradayInfoEntity
import com.hagos.data.local.StockDao
import com.hagos.data.mapper.toCompanyInfo
import com.hagos.data.mapper.toCompanyInfoEntity
import com.hagos.data.mapper.toCompanyListing
import com.hagos.data.mapper.toCompanyListingEntity
import com.hagos.data.mapper.toIntradayInfo
import com.hagos.data.mapper.toIntradayInfoEntity
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

    override suspend fun getIntradayInfo(symbol: String, reload: Boolean): List<IntradayInfo> {
        try {
            return if (reload) {
                val parsedData = getAndParseIntradayInfo(symbol)
                parsedData.forEach {
                    dao.insertIntradayInfo(it)
                }

                val dbResult = dao.getIntradayInfo(symbol)
                dbResult.map { it.toIntradayInfo() }
            } else {
                val dbResult = dao.getIntradayInfo(symbol)
                if (dbResult.isEmpty()) {
                    val parsedData = getAndParseIntradayInfo(symbol)
                    if (parsedData.isNotEmpty()) dao.clearIntradayInfo(symbol)
                    parsedData.forEach {
                        dao.insertIntradayInfo(it)
                    }
                    /**
                     * TODO: if you want to implement a true SST, fetch data again from db
                     * LIKE: dao.getIntradayInfo(symbol)
                    **/
                    parsedData.map { it.toIntradayInfo() }
                } else {
                    dbResult.map { it.toIntradayInfo() }
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            throw t
        }
    }


    private suspend fun getAndParseIntradayInfo(symbol: String): List<IntradayInfoEntity> {
        val response = api.getIntradayInfo(symbol)
        val intradayInfoList = intradayParser.parse(response.byteStream())
        return intradayInfoList.map { it.toIntradayInfoEntity(symbol) }
    }

    override suspend fun getCompanyInfo(symbol: String): CompanyInfo {
        return try {
            val dbResult = dao.getCompanyInfo(symbol)
            if (dbResult != null) {
                dbResult.toCompanyInfo()
            } else {
                val result = api.getCompanyInfo(symbol)
                dao.insertCompanyInfo(result.toCompanyInfoEntity())
                val companyInfoEntity = dao.getCompanyInfo(symbol)
                companyInfoEntity?.toCompanyInfo() ?: CompanyInfo("", "", "", "", "")
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            throw t
        }
    }
}