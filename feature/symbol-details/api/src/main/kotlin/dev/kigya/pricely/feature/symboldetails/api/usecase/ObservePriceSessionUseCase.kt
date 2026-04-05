package dev.kigya.pricely.feature.symboldetails.api.usecase

import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.repository.PriceSessionStateRepository
import kotlinx.coroutines.flow.StateFlow

class ObservePriceSessionUseCase(
    private val stateRepository: PriceSessionStateRepository,
) {
    operator fun invoke(): StateFlow<PriceSessionState> = stateRepository.state
}
