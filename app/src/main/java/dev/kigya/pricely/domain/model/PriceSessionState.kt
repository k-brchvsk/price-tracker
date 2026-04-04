package dev.kigya.pricely.domain.model

data class PriceSessionState(
    val initialConnectionSettled: Boolean = false,
    val isStreamConnected: Boolean = false,
    val hasEverReceivedValidEcho: Boolean = false,
    val isInitialConnectFailure: Boolean = false,
    val quotesBySymbol: Map<String, Quote> = emptyMap(),
    val sortedQuotes: List<Quote> = emptyList(),
)
