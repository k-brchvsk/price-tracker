package dev.kigya.pricely.feature.symboldetails.internal.mapper

import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.model.Quote
import dev.kigya.pricely.domain.model.Trend
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class SymbolDetailsUiMapperSpec : FunSpec({
    test("toInitialSymbolDetailsUiState marks known symbol as loading") {
        val state = "AAPL".toInitialSymbolDetailsUiState()
        state.symbol shouldBe "AAPL"
        state.companyName shouldBe "Apple Inc."
        state.isLoading.shouldBeTrue()
        state.isUnknownSymbol.shouldBeFalse()
    }

    test("toInitialSymbolDetailsUiState marks unknown symbol") {
        val state = "ZZZZ".toInitialSymbolDetailsUiState()
        state.isUnknownSymbol.shouldBeTrue()
        state.isLoading.shouldBeFalse()
    }

    test("toSymbolDetailsUiState maps quote and connection for known symbol") {
        val q = Quote("GOOGL", 99.0, 98.0, Trend.Up, 1L)
        val session = PriceSessionState(
            isStreamConnected = true,
            quotesBySymbol = mapOf("GOOGL" to q),
            sortedQuotes = listOf(q),
        )
        val ui = session.toSymbolDetailsUiState("GOOGL")
        ui.formattedPrice shouldBe "$99.00"
        ui.isConnected.shouldBeTrue()
        ui.shouldShowTrendIndicators.shouldBeTrue()
        ui.trend shouldBe Trend.Up
    }

    test("toSymbolDetailsUiState treats missing quote as loading for known symbol") {
        val session = PriceSessionState(
            isStreamConnected = true,
            quotesBySymbol = emptyMap(),
        )
        val ui = session.toSymbolDetailsUiState("META")
        ui.isLoading.shouldBeTrue()
        ui.formattedPrice.shouldBeNull()
    }
})
