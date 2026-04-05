package dev.kigya.pricely.domain.repository

interface PriceSessionControllerRepository {
    fun startSession()

    fun toggleFeed()
}
