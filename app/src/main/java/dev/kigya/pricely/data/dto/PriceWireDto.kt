package dev.kigya.pricely.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PriceWireDto(
    val symbol: String,
    val price: Double,
    val seq: Long,
)
