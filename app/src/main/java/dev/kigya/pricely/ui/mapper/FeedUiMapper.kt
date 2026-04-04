package dev.kigya.pricely.ui.mapper

import dev.kigya.pricely.domain.logic.PriceQuoteMerge
import dev.kigya.pricely.domain.catalog.SymbolCatalog
import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.ui.model.FeedUiState
import dev.kigya.pricely.ui.model.QuoteUiModel
import dev.kigya.pricely.util.formatPercent
import dev.kigya.pricely.util.formatPrice

fun PriceSessionState.toFeedUiState(): FeedUiState {
    val isLoading = !initialConnectionSettled ||
        (!hasEverReceivedValidEcho &&
            isStreamConnected &&
            !isInitialConnectFailure)

    val isError = initialConnectionSettled &&
        !hasEverReceivedValidEcho &&
        isInitialConnectFailure

    return when {
        isLoading -> FeedUiState.Loading
        isError -> FeedUiState.Error
        else -> FeedUiState.Content(
            quotes = sortedQuotes.map { quote ->
                val pct = PriceQuoteMerge.percentChange(
                    quote.previousPrice,
                    quote.currentPrice,
                )
                QuoteUiModel(
                    symbol = quote.symbol,
                    companyName = SymbolCatalog.metaOrNull(quote.symbol)
                        ?.displayName
                        .orEmpty(),
                    formattedPrice = formatPrice(quote.currentPrice),
                    formattedPercentChange = formatPercent(pct),
                    trend = quote.trend,
                )
            },
            isConnected = isStreamConnected,
            connectionLabel = if (isStreamConnected) "Connected" else "Disconnected",
        )
    }
}
