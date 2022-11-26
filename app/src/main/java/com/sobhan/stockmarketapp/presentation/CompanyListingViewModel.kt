package com.sobhan.stockmarketapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobhan.stockmarketapp.domain.Repository.StockRepository
import com.sobhan.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CompanyListingViewModel(
    val repository: StockRepository
) : ViewModel() {

    val state = mutableStateOf(CompanyListingState())

    fun event(event: CompanyListingEvent) {
        when (event) {
            is CompanyListingEvent.Refresh -> getCompanyListing(fetchFromRemote = true)
            is CompanyListingEvent.Search -> getCompanyListing(query = event.query)
        }
    }

    fun getCompanyListing(
        fetchFromRemote: Boolean = false,
        query: String = ""
    ) {
        viewModelScope.launch {
            repository
                .getCompanyListing(
                    query = query,
                    fetchFromRemote = fetchFromRemote
                ).collect { result ->
                    when (result) {
                        is Resource.Error -> Unit// do something
                        is Resource.Loading -> state.value = state.value.copy(isLoading = true)
                        is Resource.Success -> result.data?.let {
                            state.value = state.value.copy(company = it)
                        }
                    }
                }
        }

    }

}