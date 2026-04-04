package dev.kigya.pricely.ui.symboldetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kigya.pricely.domain.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.navigation.destinations.SymbolDetailsDestination
import dev.kigya.pricely.ui.mapper.SymbolDetailsUiMapper
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
        .map { session -> SymbolDetailsUiMapper.map(session, symbol) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SymbolDetailsUiMapper.initial(symbol),
        )
}
