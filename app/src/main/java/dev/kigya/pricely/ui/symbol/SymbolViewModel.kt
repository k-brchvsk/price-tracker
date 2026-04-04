package dev.kigya.pricely.ui.symbol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kigya.pricely.domain.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.domain.usecase.TogglePriceFeedUseCase
import dev.kigya.pricely.ui.mapper.toFeedUiState
import dev.kigya.pricely.ui.model.FeedUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SymbolViewModel(
    observe: ObservePriceSessionUseCase,
    private val toggleFeed: TogglePriceFeedUseCase,
) : ViewModel() {

    val uiState: StateFlow<FeedUiState> = observe()
        .map { it.toFeedUiState() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_STOP_TIMEOUT_MS),
            initialValue = FeedUiState.Loading,
        )

    fun onToggleFeedClicked() {
        toggleFeed()
    }

    private companion object {
        const val WHILE_SUBSCRIBED_STOP_TIMEOUT_MS = 5_000L
    }
}
