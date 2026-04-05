package dev.kigya.pricely.domain.model

data class Quote(
    val symbol: String,
    val currentPrice: Double,
    val previousPrice: Double,
    val trend: Trend,
    val lastSequenceNumber: Long = 0L,
)
