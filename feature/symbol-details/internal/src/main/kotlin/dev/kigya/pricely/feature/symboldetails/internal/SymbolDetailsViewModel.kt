package dev.kigya.pricely.feature.symboldetails.internal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dev.kigya.pricely.feature.symboldetails.api.SymbolDetailsRoute
import dev.kigya.pricely.feature.symboldetails.api.usecase.ObservePriceSessionUseCase
import dev.kigya.pricely.feature.symboldetails.internal.mapper.toInitialSymbolDetailsUiState
import dev.kigya.pricely.feature.symboldetails.internal.mapper.toSymbolDetailsUiState
import dev.kigya.pricely.feature.symboldetails.internal.model.SymbolDetailsUiState
import dev.kigya.pricely.navigation.api.NavigatorContract
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn

class SymbolDetailsViewModel(
    observePriceSession: ObservePriceSessionUseCase,
    savedStateHandle: SavedStateHandle,
    private val navigator: NavigatorContract,
) : ViewModel() {

    private val symbol: String = savedStateHandle.toRoute<SymbolDetailsRoute>().symbol

    private val initialUiState = symbol.toInitialSymbolDetailsUiState()

    val uiState: StateFlow<SymbolDetailsUiState> = observePriceSession()
        .map { it.toSymbolDetailsUiState(symbol) }
        .scan(initialUiState) { previousState, newState ->
            when {
                newState.isUnknownSymbol && previousState.companyName != null -> previousState
                newState.isLoading && previousState.companyName != null && !previousState.isLoading -> previousState
                else -> newState
            }
        }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = initialUiState,
        )

    fun onBackClicked() {
        navigator.navigateBack()
    }
}
