package com.sobhan.stockmarketapp.presentation.company_listing

sealed class CompanyListingEvent(){
    object Refresh: CompanyListingEvent()
    class Search(val query: String): CompanyListingEvent()
}
