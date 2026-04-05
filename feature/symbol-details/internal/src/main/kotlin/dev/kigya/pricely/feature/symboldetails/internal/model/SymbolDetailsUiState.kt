package dev.kigya.pricely.feature.symboldetails.internal.model

import androidx.compose.runtime.Immutable
import dev.kigya.pricely.domain.model.Trend

@Immutable
data class SymbolDetailsUiState(
    val symbol: String,
    val companyName: String?,
    val description: String?,
    val formattedPrice: String?,
    val formattedPercentChange: String?,
    val trend: Trend,
    val shouldShowTrendIndicators: Boolean,
    val isConnected: Boolean,
    val isUnknownSymbol: Boolean,
    val isLoading: Boolean,
)
