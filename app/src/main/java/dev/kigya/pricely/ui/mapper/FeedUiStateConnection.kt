package dev.kigya.pricely.ui.mapper

import dev.kigya.pricely.core.designsystem.components.button.PricelyActionState
import dev.kigya.pricely.core.designsystem.components.status.PricelyConnectionState
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrend
import dev.kigya.pricely.ui.model.FeedUiState

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
