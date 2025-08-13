package com.hagos.stockmarket.presentation.company_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hagos.domain.usecase.CompanyInfoUseCase
import com.hagos.domain.usecase.IntradayInfoUseCase
import com.hagos.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val companyInfoUseCase: CompanyInfoUseCase,
    private val intradayInfoUseCase: IntradayInfoUseCase
): ViewModel() {
    var state by mutableStateOf(CompanyInfoState())

    private fun fetchCompanyInfo(symbol: String, reload: Boolean = false){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val companyInfoResult = async {  companyInfoUseCase.invoke(symbol) }
            val intradayInfoResult = async {  intradayInfoUseCase.invoke(Pair(symbol, reload)) }

            companyInfoResult.await().collectLatest {
                when(it) {
                    is Resource.Success -> {
                        state = state.copy(
                            company = it.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            company = null,
                            error = it.message,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> Unit
                }
            }

            intradayInfoResult.await().collectLatest {
                when(it){
                    is Resource.Success -> {
                        state = state.copy(
                            stockInfos = it.data ?: emptyList(),
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            company = null,
                            error = it.message,
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> Unit
                }
            }

        }
    }

    fun onEvent(event: CompanyInfoEvent){
        when(event){
            is CompanyInfoEvent.FetchInfo -> {
                fetchCompanyInfo(event.symbol, event.reload)
            }
        }
    }
}