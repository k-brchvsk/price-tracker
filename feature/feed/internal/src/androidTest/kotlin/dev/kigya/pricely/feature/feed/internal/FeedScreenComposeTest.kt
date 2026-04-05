package dev.kigya.pricely.feature.feed.internal

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.kigya.pricely.core.designsystem.theme.PricelyTheme
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.feature.feed.internal.model.FeedUiState
import dev.kigya.pricely.feature.feed.internal.model.QuoteUiModel
import io.kotest.matchers.shouldBe
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeedScreenComposeTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun feedScreen_showsLoadingIndicatorForLoadingState() {
        composeRule.setContent {
            PricelyTheme {
                FeedScreenContent(
                    state = FeedUiState.Loading,
                    onSymbolClick = {},
                    onToggleFeedClick = {},
                )
            }
        }
        composeRule.onNodeWithTag("feed_loading_indicator").assertIsDisplayed()
    }

    @Test
    fun feedScreen_showsErrorCardForErrorState() {
        composeRule.setContent {
            PricelyTheme {
                FeedScreenContent(
                    state = FeedUiState.Error,
                    onSymbolClick = {},
                    onToggleFeedClick = {},
                )
            }
        }
        composeRule.onNodeWithTag("feed_error_title").assertIsDisplayed()
    }

    @Test
    fun feedScreen_invokesSymbolClick() {
        var clicked: String? = null
        val quote = QuoteUiModel(
            symbol = "AAPL",
            companyName = "Apple Inc.",
            formattedPrice = "$1.00",
            formattedPercentChange = "+ 0.00%",
            trend = Trend.Neutral,
        )
        composeRule.setContent {
            PricelyTheme {
                FeedScreenContent(
                    state = FeedUiState.Content(
                        quotes = listOf(quote),
                        isConnected = true,
                    ),
                    onSymbolClick = { clicked = it },
                    onToggleFeedClick = {},
                )
            }
        }
        composeRule.onNodeWithTag("feed_quote_AAPL").performClick()
        clicked shouldBe "AAPL"
    }
}
