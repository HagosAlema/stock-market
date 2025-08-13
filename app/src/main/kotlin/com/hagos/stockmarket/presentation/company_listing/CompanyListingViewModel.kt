package com.hagos.stockmarket.presentation.company_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hagos.domain.usecase.CompanyListingsUseCase
import com.hagos.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingViewModel @Inject constructor(
    private val companyListingsUseCase: CompanyListingsUseCase
): ViewModel(){

    var state by mutableStateOf(CompanyListingsState())

    private var searchJob: Job?= null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingEvent){
        when(event) {
            is CompanyListingEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings(state.searchQuery)
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            companyListingsUseCase.invoke(Pair(fetchFromRemote, query))
                .collectLatest { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let {listings->
                                state = state.copy(
                                    companies = listings
                                )
                            }
                        }
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }

        }
    }
}