package dev.kigya.pricely.data.mapper

import dev.kigya.pricely.data.dto.PriceWireDto
import dev.kigya.pricely.domain.model.PriceWirePayload
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PriceWireMapperSpec : FunSpec({
    test("maps DTO to domain payload") {
        val dto = PriceWireDto(symbol = "NVDA", price = 12.34, sequenceNumber = 99L)
        val domain = dto.toDomain()
        domain.symbol shouldBe "NVDA"
        domain.price shouldBe 12.34
        domain.sequenceNumber shouldBe 99L
    }

    test("maps domain payload to wire DTO") {
        val payload = PriceWirePayload(symbol = "META", price = 1.0, sequenceNumber = 1L)
        val dto = payload.toWireDto()
        dto.symbol shouldBe "META"
        dto.price shouldBe 1.0
        dto.sequenceNumber shouldBe 1L
    }
})
