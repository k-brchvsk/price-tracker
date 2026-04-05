package dev.kigya.pricely.domain.logic

import dev.kigya.pricely.domain.model.PriceWirePayload
import dev.kigya.pricely.domain.model.Quote
import dev.kigya.pricely.domain.model.Trend
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class PriceQuoteMergeSpec : FunSpec({
    context("trendFrom") {
        test("detects up when price rises beyond epsilon") {
            PriceQuoteMerge.trendFrom(100.0, 100.1) shouldBe Trend.Up
        }

        test("detects down when price falls beyond epsilon") {
            PriceQuoteMerge.trendFrom(100.0, 99.9) shouldBe Trend.Down
        }

        test("returns neutral for tiny moves") {
            PriceQuoteMerge.trendFrom(100.0, 100.0000000001) shouldBe Trend.Neutral
        }
    }

    context("mergeEcho") {
        val baseQuote = Quote(
            symbol = "AAPL",
            currentPrice = 100.0,
            previousPrice = 99.0,
            trend = Trend.Neutral,
            lastSequenceNumber = 5L,
        )
        val quotes = mapOf("AAPL" to baseQuote)

        test("returns null when symbol is missing") {
            PriceQuoteMerge.mergeEcho(quotes, "MISSING", 1.0, 10L).shouldBeNull()
        }

        test("returns null when sequence is not newer") {
            PriceQuoteMerge.mergeEcho(quotes, "AAPL", 101.0, 5L).shouldBeNull()
            PriceQuoteMerge.mergeEcho(quotes, "AAPL", 101.0, 4L).shouldBeNull()
        }

        test("merges echo and updates trend and sequence") {
            val merged = PriceQuoteMerge.mergeEcho(
                quotes,
                PriceWirePayload(symbol = "AAPL", price = 110.0, sequenceNumber = 6L),
            )!!
            val updated = merged["AAPL"]!!
            updated.currentPrice shouldBe 110.0
            updated.previousPrice shouldBe 100.0
            updated.lastSequenceNumber shouldBe 6L
            updated.trend shouldBe Trend.Up
        }
    }

    context("sortQuotes") {
        test("sorts by current price descending then symbol ascending") {
            val q1 = Quote("B", 10.0, 10.0, Trend.Neutral, 0L)
            val q2 = Quote("A", 10.0, 10.0, Trend.Neutral, 0L)
            val q3 = Quote("C", 20.0, 20.0, Trend.Neutral, 0L)
            val sorted = PriceQuoteMerge.sortQuotes(mapOf("B" to q1, "A" to q2, "C" to q3))
            sorted.map { it.symbol } shouldBe listOf("C", "A", "B")
        }
    }
})
