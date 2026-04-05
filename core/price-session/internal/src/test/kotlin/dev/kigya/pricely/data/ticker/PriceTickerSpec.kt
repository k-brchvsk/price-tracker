package dev.kigya.pricely.data.ticker

import dev.kigya.pricely.data.dto.PriceWireDto
import dev.kigya.pricely.domain.catalog.SymbolCatalog
import dev.kigya.pricely.domain.model.Quote
import dev.kigya.pricely.domain.model.Trend
import io.kotest.core.spec.style.FunSpec
import kotlinx.coroutines.ExperimentalCoroutinesApi
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class PriceTickerSpec : FunSpec({
    val json = Json { ignoreUnknownKeys = true }

    test("buildTickPayloads returns encoded messages for quoted symbols") {
        runTest {
            val fixedRandom = Random(42)
            val ticker = PriceTicker(backgroundScope, json, fixedRandom)
            val quotes = mapOf(
                "AAPL" to Quote("AAPL", 100.0, 100.0, Trend.Neutral, 0L),
                "GOOGL" to Quote("GOOGL", 200.0, 200.0, Trend.Neutral, 0L),
            )
            val payloads = ticker.buildTickPayloads(quotes)
            payloads.size shouldBe 2
            val symbols = payloads.map { json.decodeFromString<PriceWireDto>(it).symbol }.toSet()
            symbols shouldContain "AAPL"
            symbols shouldContain "GOOGL"
        }
    }

    test("buildTickPayloads returns empty list when quotes map is empty") {
        runTest {
            val ticker = PriceTicker(backgroundScope, json, Random(0))
            ticker.buildTickPayloads(emptyMap()).shouldHaveSize(0)
        }
    }

    test("seedQuotes seeds every catalog ticker") {
        val seeded = PriceTicker.seedQuotes()
        seeded.size shouldBe SymbolCatalog.entries.size
    }

    test("start invokes onTick after interval") {
        runTest {
            val ticker = PriceTicker(backgroundScope, json, Random(0))
            var ticks = 0
            ticker.start { ticks++ }
            advanceTimeBy(2_100L)
            ticks shouldBe 1
            ticker.stop()
        }
    }
})
