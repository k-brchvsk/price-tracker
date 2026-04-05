package dev.kigya.pricely.feature.feed.internal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kigya.pricely.feature.feed.api.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.feature.feed.api.usecase.TogglePriceFeedUseCase
import dev.kigya.pricely.feature.feed.internal.mapper.toFeedUiState
import dev.kigya.pricely.feature.feed.internal.model.FeedUiState
import dev.kigya.pricely.feature.symboldetails.api.SymbolDetailsRoute
import dev.kigya.pricely.navigation.api.NavigatorContract
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class FeedViewModel(
    observePriceSession: ObservePriceSessionUseCase,
    private val togglePriceFeed: TogglePriceFeedUseCase,
    private val navigator: NavigatorContract,
) : ViewModel() {

    val uiState: StateFlow<FeedUiState> = observePriceSession()
        .map { it.toFeedUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = FeedUiState.Loading,
        )

    fun onToggleFeedClicked() {
        togglePriceFeed()
    }

    fun onSymbolClicked(symbol: String) {
        navigator.navigateTo(SymbolDetailsRoute(symbol = symbol))
    }
}
