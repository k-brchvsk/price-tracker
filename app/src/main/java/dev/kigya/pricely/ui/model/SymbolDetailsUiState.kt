package dev.kigya.pricely.ui.model

import dev.kigya.pricely.domain.model.Trend

data class SymbolDetailsUiState(
    val symbol: String,
    val companyName: String?,
    val description: String?,
    val formattedPrice: String,
    val formattedPercentChange: String,
    val trend: Trend,
    val showTrendIndicators: Boolean,
    val isConnected: Boolean,
    val unknownSymbol: Boolean,
    val isLoading: Boolean,
)
