package dev.kigya.pricely.domain.usecase

import dev.kigya.pricely.domain.repository.PriceSessionController

class TogglePriceFeedUseCase(
    private val controller: PriceSessionController,
) {
    operator fun invoke() = controller.toggleFeed()
}
