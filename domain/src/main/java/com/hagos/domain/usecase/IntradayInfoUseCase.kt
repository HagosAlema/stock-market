package com.hagos.domain.usecase

import com.hagos.domain.model.IntradayInfo
import com.hagos.domain.repository.StockRepository
import javax.inject.Inject

class IntradayInfoUseCase @Inject constructor(
    private val stockRepository: StockRepository
): BaseUseCase<String, List<IntradayInfo>>() {
    override suspend fun implement(input: String): List<IntradayInfo> {
        return stockRepository.getIntradayInfo(input)
    }
}