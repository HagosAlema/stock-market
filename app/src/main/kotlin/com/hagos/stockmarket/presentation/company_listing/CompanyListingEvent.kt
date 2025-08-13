package com.hagos.stockmarket.presentation.company_listing

sealed class CompanyListingEvent {
    data object Refresh: CompanyListingEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingEvent()
}