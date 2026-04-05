package dev.kigya.pricely.feature.feed.internal.mapper

import dev.kigya.pricely.core.designsystem.components.button.PricelyActionState
import dev.kigya.pricely.core.designsystem.components.status.PricelyConnectionState
import dev.kigya.pricely.domain.model.PriceSessionState
import dev.kigya.pricely.domain.model.Quote
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.feature.feed.internal.model.FeedUiState
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class FeedUiMapperSpec : FunSpec({
    test("maps to Loading when initial connection not settled") {
        val state = PriceSessionState(isInitialConnectionSettled = false)
        state.toFeedUiState() shouldBe FeedUiState.Loading
    }

    test("maps to Loading when connected but no echo yet") {
        val state = PriceSessionState(
            isInitialConnectionSettled = true,
            isStreamConnected = true,
            hasEverReceivedValidEcho = false,
            isInitialConnectFailure = false,
        )
        state.toFeedUiState() shouldBe FeedUiState.Loading
    }

    test("maps to Error when settled, never echoed, and connect failed") {
        val state = PriceSessionState(
            isInitialConnectionSettled = true,
            hasEverReceivedValidEcho = false,
            isInitialConnectFailure = true,
        )
        state.toFeedUiState() shouldBe FeedUiState.Error
    }

    test("maps to Content with sorted quotes when session is ready") {
        val q = Quote("AAPL", 10.0, 10.0, Trend.Neutral, 0L)
        val state = PriceSessionState(
            isInitialConnectionSettled = true,
            isStreamConnected = true,
            hasEverReceivedValidEcho = true,
            isInitialConnectFailure = false,
            quotesBySymbol = mapOf("AAPL" to q),
            sortedQuotes = listOf(q),
        )
        val ui = state.toFeedUiState()
        ui.shouldBeInstanceOf<FeedUiState.Content>()
        ui.quotes.single().symbol shouldBe "AAPL"
        ui.isConnected shouldBe true
    }

    test("toPricelyConnectionState maps loading error and connected content") {
        FeedUiState.Loading.toPricelyConnectionState() shouldBe PricelyConnectionState.LOADING
        FeedUiState.Error.toPricelyConnectionState() shouldBe PricelyConnectionState.DISCONNECTED
        FeedUiState.Content(emptyList(), isConnected = true).toPricelyConnectionState() shouldBe
            PricelyConnectionState.CONNECTED
        FeedUiState.Content(emptyList(), isConnected = false).toPricelyConnectionState() shouldBe
            PricelyConnectionState.DISCONNECTED
    }

    test("toPricelyActionState mirrors connection mapping") {
        FeedUiState.Loading.toPricelyActionState() shouldBe PricelyActionState.LOADING
        FeedUiState.Error.toPricelyActionState() shouldBe PricelyActionState.DISCONNECTED
        FeedUiState.Content(emptyList(), true).toPricelyActionState() shouldBe PricelyActionState.CONNECTED
        FeedUiState.Content(emptyList(), false).toPricelyActionState() shouldBe PricelyActionState.DISCONNECTED
    }
})
