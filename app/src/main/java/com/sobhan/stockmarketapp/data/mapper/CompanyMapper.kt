package com.sobhan.stockmarketapp.data.mapper

import com.sobhan.stockmarketapp.data.local.CompanyListingEntity
import com.sobhan.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(name = this.name, exchange = this.exchange, symbol = this.symbol)
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(name = this.name, exchange = this.exchange, symbol = this.symbol)
}