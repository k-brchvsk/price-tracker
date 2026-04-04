package dev.kigya.pricely.ui.model

sealed interface FeedUiState {
    val connectionLabel: String
    val isConnected: Boolean

    data object Loading : FeedUiState {
        override val connectionLabel: String = "Loading…"
        override val isConnected: Boolean = false
    }

    data object Error : FeedUiState {
        override val connectionLabel: String = "Disconnected"
        override val isConnected: Boolean = false
    }

    data class Content(
        val quotes: List<QuoteUiModel>,
        override val isConnected: Boolean,
        override val connectionLabel: String,
    ) : FeedUiState
}
