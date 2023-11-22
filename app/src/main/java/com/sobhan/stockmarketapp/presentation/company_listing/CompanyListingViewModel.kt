package com.sobhan.stockmarketapp.presentation.company_listing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sobhan.stockmarketapp.domain.Repository.StockRepository
import com.sobhan.stockmarketapp.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CompanyListingViewModel(
    val repository: StockRepository
) : ViewModel() {

    var state by mutableStateOf(CompanyListingState())

    //val job: Job? = null

    init {
        getCompanyListing()
    }

    fun onEvent(event: CompanyListingEvent) {
        when (event) {
            is CompanyListingEvent.Refresh -> getCompanyListing(fetchFromRemote = true)
            is CompanyListingEvent.Search -> {
                state = state.copy(query = event.query)
                viewModelScope.launch {
                    delay(100)
                    getCompanyListing(query = event.query)
                }

            }
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
                        is Resource.Loading -> state = state.copy(isLoading = true)
                        is Resource.Success -> result.data?.let {
                            state = state.copy(company = it)
                        }
                    }
                }
        }

    }

}