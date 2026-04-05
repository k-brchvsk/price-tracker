package dev.kigya.pricely.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PriceWireDto(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("price")
    val price: Double,
    @SerialName("seq")
    val sequenceNumber: Long,
)
