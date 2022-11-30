package com.sobhan.stockmarketapp.presentation.company_listing

import com.sobhan.stockmarketapp.domain.model.CompanyListing

data class CompanyListingState(
    val isRefreshing: Boolean = false,
    val query: String = "",
    val company: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false
)