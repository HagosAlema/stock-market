package com.hagos.domain.repository

import com.hagos.domain.model.CompanyInfo
import com.hagos.domain.model.CompanyListing
import com.hagos.domain.model.IntradayInfo
import com.hagos.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(fetchFromRemote: Boolean, query: String): List<CompanyListing>

    suspend fun getIntradayInfo(symbol: String): List<IntradayInfo>

    suspend fun getCompanyInfo(symbol: String): CompanyInfo
}