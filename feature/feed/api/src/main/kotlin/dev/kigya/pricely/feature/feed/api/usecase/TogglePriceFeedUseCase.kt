package dev.kigya.pricely.feature.feed.api.usecase

import dev.kigya.pricely.domain.repository.PriceSessionControllerRepository

class TogglePriceFeedUseCase(
    private val repository: PriceSessionControllerRepository,
) {
    operator fun invoke() = repository.toggleFeed()
}
