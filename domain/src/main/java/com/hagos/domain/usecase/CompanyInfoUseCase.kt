package com.hagos.domain.usecase

import com.hagos.domain.model.CompanyInfo
import com.hagos.domain.repository.StockRepository
import javax.inject.Inject

class CompanyInfoUseCase @Inject constructor(
    private val stockRepository: StockRepository
): BaseUseCase<String, CompanyInfo>() {
    override suspend fun implement(input: String): CompanyInfo {
        return stockRepository.getCompanyInfo(input)
    }

}