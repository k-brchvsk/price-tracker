package dev.kigya.pricely.domain.model

internal data class PriceWirePayload(
    val symbol: String,
    val price: Double,
    val sequenceNumber: Long,
)
