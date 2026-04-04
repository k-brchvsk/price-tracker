package dev.kigya.pricely.domain.usecase

import dev.kigya.pricely.domain.repository.PriceSessionController

class StartPriceSessionUseCase(
    private val controller: PriceSessionController,
) {
    operator fun invoke() = controller.startSession()
}
