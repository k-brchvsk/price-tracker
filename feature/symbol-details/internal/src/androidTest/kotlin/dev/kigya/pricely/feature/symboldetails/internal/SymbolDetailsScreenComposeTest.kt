package dev.kigya.pricely.feature.symboldetails.internal

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.kigya.pricely.core.designsystem.theme.PricelyTheme
import dev.kigya.pricely.domain.model.Trend
import dev.kigya.pricely.feature.symboldetails.internal.model.SymbolDetailsUiState
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SymbolDetailsScreenComposeTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun symbolDetails_showsLoadingForLoadingState() {
        composeRule.setContent {
            PricelyTheme {
                SymbolDetailsScreenContent(
                    state = SymbolDetailsUiState(
                        symbol = "AAPL",
                        companyName = "Apple Inc.",
                        description = null,
                        formattedPrice = null,
                        formattedPercentChange = null,
                        trend = Trend.Neutral,
                        shouldShowTrendIndicators = false,
                        isConnected = false,
                        isUnknownSymbol = false,
                        isLoading = true,
                    ),
                    onBackClick = {},
                )
            }
        }
        composeRule.onNodeWithTag("symbol_details_loading").assertIsDisplayed()
    }

    @Test
    fun symbolDetails_backInvokesCallback() {
        var back = false
        composeRule.setContent {
            PricelyTheme {
                SymbolDetailsScreenContent(
                    state = SymbolDetailsUiState(
                        symbol = "AAPL",
                        companyName = "Apple Inc.",
                        description = "Test",
                        formattedPrice = "$10.00",
                        formattedPercentChange = "+ 1.00%",
                        trend = Trend.Up,
                        shouldShowTrendIndicators = true,
                        isConnected = true,
                        isUnknownSymbol = false,
                        isLoading = false,
                    ),
                    onBackClick = { back = true },
                )
            }
        }
        composeRule.onNodeWithTag("symbol_details_back").performClick()
        back.shouldBeTrue()
    }

    @Test
    fun symbolDetails_showsPriceWhenLoaded() {
        composeRule.setContent {
            PricelyTheme {
                SymbolDetailsScreenContent(
                    state = SymbolDetailsUiState(
                        symbol = "NVDA",
                        companyName = "NVIDIA Corporation",
                        description = null,
                        formattedPrice = "$123.45",
                        formattedPercentChange = null,
                        trend = Trend.Neutral,
                        shouldShowTrendIndicators = false,
                        isConnected = false,
                        isUnknownSymbol = false,
                        isLoading = false,
                    ),
                    onBackClick = {},
                )
            }
        }
        composeRule.onNodeWithTag("symbol_details_price").assertIsDisplayed()
    }
}
