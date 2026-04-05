package dev.kigya.pricely.feature.feed.internal.model

sealed interface FeedUiState {
    val isConnected: Boolean

    data object Loading : FeedUiState {
        override val isConnected: Boolean = false
    }

    data object Error : FeedUiState {
        override val isConnected: Boolean = false
    }

    data class Content(
        val quotes: List<QuoteUiModel>,
        override val isConnected: Boolean,
    ) : FeedUiState
}
