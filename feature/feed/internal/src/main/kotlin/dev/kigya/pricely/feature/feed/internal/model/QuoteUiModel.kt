package dev.kigya.pricely.feature.feed.internal.model

import dev.kigya.pricely.domain.model.Trend

data class QuoteUiModel(
    val symbol: String,
    val companyName: String,
    val formattedPrice: String,
    val formattedPercentChange: String?,
    val trend: Trend,
)
