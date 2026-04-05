package dev.kigya.pricely.feature.feed.internal.mapper

import dev.kigya.pricely.core.designsystem.components.button.PricelyActionState
import dev.kigya.pricely.core.designsystem.components.status.PricelyConnectionState
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrend
import dev.kigya.pricely.domain.catalog.SymbolCatalog
import dev.kigya.pricely.domain.logic.QuotePriceMath
import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.feature.feed.internal.model.FeedUiState
import dev.kigya.pricely.feature.feed.internal.model.QuoteUiModel
import dev.kigya.pricely.util.formatPercent
import dev.kigya.pricely.util.formatPrice

fun PriceSessionState.toFeedUiState(): FeedUiState {
    val isLoading = !isInitialConnectionSettled ||
        (!hasEverReceivedValidEcho &&
            isStreamConnected &&
            !isInitialConnectFailure)

    val isError = isInitialConnectionSettled &&
        !hasEverReceivedValidEcho &&
        isInitialConnectFailure

    return when {
        isLoading -> FeedUiState.Loading
        isError -> FeedUiState.Error
        else -> FeedUiState.Content(
            quotes = sortedQuotes.map { quote ->
                val percentChange = QuotePriceMath.percentChange(
                    quote.previousPrice,
                    quote.currentPrice,
                )
                QuoteUiModel(
                    symbol = quote.symbol,
                    companyName = SymbolCatalog.metaOrNull(quote.symbol)?.displayName.orEmpty(),
                    formattedPrice = formatPrice(quote.currentPrice),
                    formattedPercentChange = formatPercent(percentChange),
                    trend = quote.trend,
                )
            },
            isConnected = isStreamConnected,
        )
    }
}

fun FeedUiState.toPricelyConnectionState(): PricelyConnectionState = when (this) {
    is FeedUiState.Loading -> PricelyConnectionState.LOADING
    is FeedUiState.Error -> PricelyConnectionState.DISCONNECTED
    is FeedUiState.Content ->
        if (isConnected) PricelyConnectionState.CONNECTED
        else PricelyConnectionState.DISCONNECTED
}

fun FeedUiState.toPricelyActionState(): PricelyActionState = when (this) {
    is FeedUiState.Loading -> PricelyActionState.LOADING
    is FeedUiState.Error -> PricelyActionState.DISCONNECTED
    is FeedUiState.Content ->
        if (isConnected) PricelyActionState.CONNECTED
        else PricelyActionState.DISCONNECTED
}

fun Trend.toPricelyTrend(): PricelyTrend = when (this) {
    Trend.Up -> PricelyTrend.UP
    Trend.Down -> PricelyTrend.DOWN
    Trend.Neutral -> PricelyTrend.NEUTRAL
}
