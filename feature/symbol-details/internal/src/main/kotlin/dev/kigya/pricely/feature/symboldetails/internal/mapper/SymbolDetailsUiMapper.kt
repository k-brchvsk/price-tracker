package dev.kigya.pricely.feature.symboldetails.internal.mapper

import dev.kigya.pricely.domain.catalog.SymbolCatalog
import dev.kigya.pricely.domain.logic.QuotePriceMath
import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.feature.symboldetails.internal.model.SymbolDetailsUiState
import dev.kigya.pricely.util.formatPercent
import dev.kigya.pricely.util.formatPrice

internal fun PriceSessionState.toSymbolDetailsUiState(symbol: String): SymbolDetailsUiState {
    val symbolMetadata = SymbolCatalog.metaOrNull(symbol)
    val unknown = symbol.isNotBlank() && symbolMetadata == null
    val quote = quotesBySymbol[symbol]
    val isConnected = isStreamConnected
    val percentChange = quote?.let {
        QuotePriceMath.percentChange(it.previousPrice, it.currentPrice)
    }

    return SymbolDetailsUiState(
        symbol = symbol,
        companyName = symbolMetadata?.displayName,
        description = symbolMetadata?.description,
        formattedPrice = quote?.let { formatPrice(it.currentPrice) },
        formattedPercentChange = formatPercent(percentChange),
        trend = quote?.trend ?: Trend.Neutral,
        shouldShowTrendIndicators = isConnected && quote != null,
        isConnected = isConnected,
        isUnknownSymbol = unknown,
        isLoading = quote == null && !unknown,
    )
}

internal fun String.toInitialSymbolDetailsUiState(): SymbolDetailsUiState {
    val symbolMetadata = SymbolCatalog.metaOrNull(this)
    return SymbolDetailsUiState(
        symbol = this,
        companyName = symbolMetadata?.displayName,
        description = symbolMetadata?.description,
        formattedPrice = null,
        formattedPercentChange = formatPercent(null),
        trend = Trend.Neutral,
        shouldShowTrendIndicators = false,
        isConnected = false,
        isUnknownSymbol = isNotBlank() && symbolMetadata == null,
        isLoading = symbolMetadata != null,
    )
}
