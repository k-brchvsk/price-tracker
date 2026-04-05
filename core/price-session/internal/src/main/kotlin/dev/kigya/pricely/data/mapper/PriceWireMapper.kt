package dev.kigya.pricely.data.mapper

import dev.kigya.pricely.data.dto.PriceWireDto
import dev.kigya.pricely.domain.model.PriceWirePayload

fun PriceWireDto.toDomain(): PriceWirePayload = PriceWirePayload(
    symbol = symbol,
    price = price,
    sequenceNumber = sequenceNumber,
)

fun PriceWirePayload.toWireDto(): PriceWireDto = PriceWireDto(
    symbol = symbol,
    price = price,
    sequenceNumber = sequenceNumber,
)
