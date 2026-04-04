package dev.kigya.pricely.domain.repository

interface PriceSessionController {
    fun startSession()

    fun toggleFeed()
}
