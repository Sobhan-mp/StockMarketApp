package com.sobhan.stockmarketapp.domain.Repository

import com.sobhan.stockmarketapp.domain.model.CompanyListing
import com.sobhan.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {


    suspend fun getCompanyListing(
        query: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<CompanyListing>>>
}