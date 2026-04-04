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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn

class SymbolDetailsViewModel(
    observe: ObservePriceSessionUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val symbol: String =
        savedStateHandle.get<String>(SymbolDetailsDestination.ARG_SYMBOL).orEmpty()

    private val initialUiState = symbol.toInitialSymbolDetailsUiState()

    val uiState: StateFlow<SymbolDetailsUiState> = observe()
        .map { it.toSymbolDetailsUiState(symbol) }
        .scan(initialUiState) { previousState, newState ->
            when {
                newState.unknownSymbol && previousState.companyName != null -> {
                    previousState
                }
                newState.isLoading && previousState.companyName != null && !previousState.isLoading -> {
                    previousState
                }
                else -> newState
            }
        }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = initialUiState,
        )

    private companion object {
        const val WHILE_SUBSCRIBED_STOP_TIMEOUT_MS = 5_000L
    }
}
