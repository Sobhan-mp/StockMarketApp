package com.sobhan.stockmarketapp.presentation.company_listing



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination

@Composable
@Destination(start = true)
fun CompanyListingScreen(
    viewModel: CompanyListingViewModel = hiltViewModel()
) {
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.onEvent(CompanyListingEvent.Refresh) }) {

        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(viewModel.state.company.size){ i ->
                val company = viewModel.state.company[i]
                CompanyItem(company = company)

            }

        }
    }
}
