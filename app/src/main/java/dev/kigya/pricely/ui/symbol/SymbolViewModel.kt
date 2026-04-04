package dev.kigya.pricely.ui.symbol

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kigya.pricely.domain.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.domain.usecase.ReconnectPriceSessionUseCase
import dev.kigya.pricely.ui.mapper.FeedUiMapper
import dev.kigya.pricely.ui.model.FeedUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SymbolViewModel(
    observe: ObservePriceSessionUseCase,
    private val reconnect: ReconnectPriceSessionUseCase,
) : ViewModel() {

    val uiState: StateFlow<FeedUiState> = observe()
        .map(FeedUiMapper::map)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FeedUiState.Loading,
        )

    fun onReconnectClicked() {
        reconnect()
    }
}
