package com.hagos.domain.usecase

import com.hagos.domain.model.CompanyListing
import com.hagos.domain.repository.StockRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingsUseCase @Inject constructor(
    private val stockRepository: StockRepository
): BaseUseCase<Pair<Boolean, String>, List<CompanyListing>>(){
    override suspend fun implement(input: Pair<Boolean, String>): List<CompanyListing> {
        return stockRepository.getCompanyListings(input.first, input.second)
    }
}