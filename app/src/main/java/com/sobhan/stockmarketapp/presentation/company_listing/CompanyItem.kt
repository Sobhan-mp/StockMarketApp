package com.sobhan.stockmarketapp.presentation.company_listing


import android.icu.lang.UCharacter.LineBreak
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.sobhan.stockmarketapp.domain.model.CompanyListing

@Composable
fun CompanyItem(
    company: CompanyListing
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = company.name,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
            Text(
                text = company.exchange,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    color = Color.LightGray
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "(${company.symbol})",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

        }

        Divider(
            color = Color.Gray,
            thickness = 2.dp
        )

    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Column() {
    }

}
