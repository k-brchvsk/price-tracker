package dev.kigya.pricely.domain.usecase

import dev.kigya.pricely.domain.repository.PriceSessionController

class ReconnectPriceSessionUseCase(
    private val controller: PriceSessionController,
) {
    operator fun invoke() = controller.reconnect()
}
