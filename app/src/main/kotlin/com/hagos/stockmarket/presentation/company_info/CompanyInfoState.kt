package com.hagos.stockmarket.presentation.company_info

import com.hagos.domain.model.CompanyInfo
import com.hagos.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)