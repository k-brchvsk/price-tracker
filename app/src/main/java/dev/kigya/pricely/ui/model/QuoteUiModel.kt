package dev.kigya.pricely.ui.model

import dev.kigya.pricely.domain.model.Trend

data class QuoteUiModel(
    val symbol: String,
    val companyName: String,
    val formattedPrice: String,
    val formattedPercentChange: String,
    val trend: Trend,
)
