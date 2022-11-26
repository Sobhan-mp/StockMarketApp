package com.sobhan.stockmarketapp.presentation

sealed class CompanyListingEvent(){
    object Refresh: CompanyListingEvent()
    class Search(val query: String): CompanyListingEvent()
}
