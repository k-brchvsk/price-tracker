package dev.kigya.pricely.ui.mapper

import dev.kigya.pricely.domain.logic.PriceQuoteMerge
import dev.kigya.pricely.data.catalog.SymbolCatalog
import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.ui.model.SymbolDetailsUiState
import dev.kigya.pricely.util.formatPercent
import dev.kigya.pricely.util.formatPrice

fun PriceSessionState.toSymbolDetailsUiState(symbol: String): SymbolDetailsUiState {
    val meta = SymbolCatalog.metaOrNull(symbol)
    val unknown = symbol.isNotBlank() && meta == null
    val quote = quotesBySymbol[symbol]
    val isConnected = isStreamConnected
    val pct = quote?.let {
        PriceQuoteMerge.percentChange(it.previousPrice, it.currentPrice)
    }

    return SymbolDetailsUiState(
        symbol = symbol,
        companyName = meta?.displayName,
        description = meta?.description,
        formattedPrice = quote?.let { formatPrice(it.currentPrice) } ?: "N/A",
        formattedPercentChange = formatPercent(pct),
        trend = quote?.trend ?: Trend.Neutral,
        showTrendIndicators = isConnected && quote != null,
        isConnected = isConnected,
        unknownSymbol = unknown,
        isLoading = quote == null && !unknown,
    )
}

fun String.toInitialSymbolDetailsUiState(): SymbolDetailsUiState {
    val meta = SymbolCatalog.metaOrNull(this)
    return SymbolDetailsUiState(
        symbol = this,
        companyName = meta?.displayName,
        description = meta?.description,
        formattedPrice = "N/A",
        formattedPercentChange = "—",
        trend = Trend.Neutral,
        showTrendIndicators = false,
        isConnected = false,
        unknownSymbol = isNotBlank() && meta == null,
        isLoading = meta != null,
    )
}
