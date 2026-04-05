package dev.kigya.pricely.util

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class NumberFormattingSpec : FunSpec({
    test("formatPrice uses US locale with two decimals and dollar prefix") {
        formatPrice(12.3) shouldBe "$12.30"
        formatPrice(1000.456) shouldBe "$1000.46"
    }

    test("formatPercent returns null for null input") {
        formatPercent(null).shouldBeNull()
    }

    test("formatPercent uses plus prefix for non-negative values") {
        formatPercent(0.0) shouldBe "+ 0.00%"
        formatPercent(1.25) shouldBe "+ 1.25%"
    }

    test("formatPercent uses minus prefix for negative values") {
        formatPercent(-2.5) shouldBe "- 2.50%"
    }
})
