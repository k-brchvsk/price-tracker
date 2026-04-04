package dev.kigya.pricely.domain.usecase

import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.repository.PriceSessionStateSource
import kotlinx.coroutines.flow.StateFlow

class ObservePriceSessionUseCase(
    private val stateSource: PriceSessionStateSource,
) {
    operator fun invoke(): StateFlow<PriceSessionState> = stateSource.state
}
