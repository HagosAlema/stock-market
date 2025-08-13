package com.hagos.domain.usecase

import com.hagos.domain.model.IntradayInfo
import com.hagos.domain.repository.StockRepository
import javax.inject.Inject

class IntradayInfoUseCase @Inject constructor(
    private val stockRepository: StockRepository
): BaseUseCase<Pair<String, Boolean>, List<IntradayInfo>>() {
    override suspend fun implement(input: Pair<String, Boolean>): List<IntradayInfo> {
        return stockRepository.getIntradayInfo(input.first, input.second)
    }
}