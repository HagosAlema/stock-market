package com.hagos.stockmarket.presentation.company_info

sealed class CompanyInfoEvent {
    data class FetchInfo(val symbol: String, val reload: Boolean = false): CompanyInfoEvent()
}