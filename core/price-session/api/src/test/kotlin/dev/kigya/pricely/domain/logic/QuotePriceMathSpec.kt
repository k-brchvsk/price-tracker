package dev.kigya.pricely.domain.logic

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class QuotePriceMathSpec : FunSpec({
    context("percentChange") {
        test("returns null when previous price is effectively zero") {
            QuotePriceMath.percentChange(previousPrice = 0.0, currentPrice = 10.0).shouldBeNull()
            QuotePriceMath.percentChange(previousPrice = 1e-10, currentPrice = 2e-10).shouldBeNull()
        }

        test("computes positive percent change") {
            val pct = QuotePriceMath.percentChange(previousPrice = 100.0, currentPrice = 110.0)
            pct shouldBe (10.0 plusOrMinus 1e-9)
        }

        test("computes negative percent change") {
            val pct = QuotePriceMath.percentChange(previousPrice = 100.0, currentPrice = 90.0)
            pct shouldBe (-10.0 plusOrMinus 1e-9)
        }

        withData(
            nameFn = { "from ${it.first} to ${it.second}" },
            Triple(50.0, 75.0, 50.0),
            Triple(200.0, 100.0, -50.0),
            Triple(1.0, 1.0, 0.0),
        ) { (prev, cur, expectedPct) ->
            val pct = QuotePriceMath.percentChange(previousPrice = prev, currentPrice = cur)
            pct shouldBe (expectedPct plusOrMinus 1e-9)
        }
    }
})
