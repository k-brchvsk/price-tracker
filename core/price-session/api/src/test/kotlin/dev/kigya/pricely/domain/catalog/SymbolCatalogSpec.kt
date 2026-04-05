package dev.kigya.pricely.domain.catalog

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class SymbolCatalogSpec : FunSpec({
    test("exposes exactly 25 catalog entries") {
        SymbolCatalog.entries.size shouldBe 25
    }

    test("metaOrNull returns display data for known tickers") {
        SymbolCatalog.metaOrNull("AAPL")?.displayName shouldBe "Apple Inc."
        SymbolCatalog.metaOrNull("NVDA")?.ticker shouldBe "NVDA"
    }

    test("metaOrNull returns null for unknown tickers") {
        SymbolCatalog.metaOrNull("ZZZZ").shouldBeNull()
    }

    test("isKnownTicker reflects catalog membership") {
        SymbolCatalog.isKnownTicker("META").shouldBeTrue()
        SymbolCatalog.isKnownTicker("UNKNOWN").shouldBeFalse()
    }
})
