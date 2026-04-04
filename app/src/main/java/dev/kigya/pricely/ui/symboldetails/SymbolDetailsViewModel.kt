package dev.kigya.pricely.ui.symboldetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kigya.pricely.domain.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.navigation.destinations.SymbolDetailsDestination
import dev.kigya.pricely.ui.mapper.toInitialSymbolDetailsUiState
import dev.kigya.pricely.ui.mapper.toSymbolDetailsUiState
import dev.kigya.pricely.ui.model.SymbolDetailsUiState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SymbolDetailsViewModel(
    observe: ObservePriceSessionUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val symbol: String =
        savedStateHandle.get<String>(SymbolDetailsDestination.ARG_SYMBOL).orEmpty()

    val uiState: StateFlow<SymbolDetailsUiState> = observe()
        .map { it.toSymbolDetailsUiState(symbol) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(WHILE_SUBSCRIBED_STOP_TIMEOUT_MS),
            initialValue = symbol.toInitialSymbolDetailsUiState(),
        )

    private companion object {
        const val WHILE_SUBSCRIBED_STOP_TIMEOUT_MS = 5_000L
    }
}
