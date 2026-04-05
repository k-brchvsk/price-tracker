package dev.kigya.pricely.core.designsystem

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.kigya.pricely.core.designsystem.components.button.PricelyActionButton
import dev.kigya.pricely.core.designsystem.components.button.PricelyActionState
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrend
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrendCircleIcon
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrendCircleStyle
import dev.kigya.pricely.core.designsystem.components.stock.PricelyTrendCircleVariant
import dev.kigya.pricely.core.designsystem.theme.PricelyTheme
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.util.compose.PricelyFlashingPriceText
import io.kotest.matchers.booleans.shouldBeTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DesignSystemComposeTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun pricelyActionButton_isDisplayedAndClickableWhenDisconnected() {
        var clicked = false
        composeRule.setContent {
            PricelyTheme {
                PricelyActionButton(
                    state = PricelyActionState.DISCONNECTED,
                    icon = Icons.Filled.Sync,
                    contentDescription = "sync",
                    onClick = { clicked = true },
                )
            }
        }
        composeRule.onNodeWithTag("pricely_action_button").assertIsDisplayed()
        composeRule.onNodeWithTag("pricely_action_button").performClick()
        clicked.shouldBeTrue()
    }

    @Test
    fun pricelyTrendCircleIcon_rendersForUpTrend() {
        composeRule.setContent {
            PricelyTheme {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .testTag("trend_icon_host"),
                ) {
                    PricelyTrendCircleIcon(
                        trend = PricelyTrend.UP,
                        style = PricelyTrendCircleStyle.VERTICAL_ARROWS,
                        variant = PricelyTrendCircleVariant.SOFT_BORDERED,
                    )
                }
            }
        }
        composeRule.onNodeWithTag("trend_icon_host").assertIsDisplayed()
    }

    @Test
    fun pricelyFlashingPriceText_displaysText() {
        composeRule.setContent {
            PricelyTheme {
                PricelyFlashingPriceText(
                    text = "$42.00",
                    trend = PricelyTrend.NEUTRAL,
                    emphasisColor = AppTheme.colors.textPrimary,
                    style = AppTheme.typography.bodyEmphasized,
                )
            }
        }
        composeRule.onNodeWithText("$42.00").assertIsDisplayed()
    }
}
