package dev.kigya.pricely.core.designsystem.components.stock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIcon
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIconVariant
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.preview.PricelyPreview
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.util.compose.PricelyFlashingPriceText

enum class PricelyTrend {
    UP,
    DOWN,
    NEUTRAL,
}

@Composable
fun PricelyStockItem(
    icon: Painter,
    ticker: String,
    name: String,
    price: String,
    changePercent: String,
    trend: PricelyTrend,
    modifier: Modifier = Modifier,
    isMuted: Boolean = false,
    onClick: () -> Unit,
) {
    val trendColor = when (trend) {
        PricelyTrend.UP -> AppTheme.colors.success
        PricelyTrend.DOWN -> AppTheme.colors.error
        PricelyTrend.NEUTRAL -> AppTheme.colors.textSecondary
    }

    val trendContentDescription = when (trend) {
        PricelyTrend.UP -> stringResource(R.string.cd_trend_up)
        PricelyTrend.DOWN -> stringResource(R.string.cd_trend_down)
        PricelyTrend.NEUTRAL -> null
    }

    val emphasis = if (isMuted) AppTheme.colors.textSecondary else AppTheme.colors.textPrimary
    val secondaryEmphasis = if (isMuted) AppTheme.colors.textSecondary.copy(alpha = 0.85f) else AppTheme.colors.textSecondary

    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isMuted) 0.72f else 1f)
            .clickable(onClick = onClick)
            .padding(
                horizontal = AppTheme.dimens.space16,
                vertical = AppTheme.dimens.space12,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space12),
        ) {
            PricelyIcon(
                painter = icon,
                contentDescription = stringResource(R.string.cd_stock_icon),
                variant = PricelyIconVariant.CIRCLE_BACKGROUND,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
            ) {
                PricelyText(
                    text = ticker,
                    style = AppTheme.typography.headingMedium,
                    color = emphasis,
                )

                PricelyText(
                    text = name,
                    style = AppTheme.typography.bodySmall,
                    color = secondaryEmphasis,
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
        ) {
            PricelyFlashingPriceText(
                text = price,
                trend = trend,
                emphasisColor = emphasis,
                style = AppTheme.typography.headingMedium,
            )

            when (trend) {
                PricelyTrend.NEUTRAL -> {
                    PricelyText(
                        text = changePercent,
                        style = AppTheme.typography.bodySmall,
                        color = trendColor,
                    )
                }

                PricelyTrend.UP -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
                    ) {
                        PricelyText(
                            text = changePercent,
                            style = AppTheme.typography.bodySmall,
                            color = trendColor,
                        )

                        PricelyIcon(
                            painter = painterResource(R.drawable.ic_arrow_up),
                            contentDescription = trendContentDescription,
                            variant = PricelyIconVariant.CIRCLE_BORDER_FILLED,
                            tint = trendColor,
                        )
                    }
                }

                PricelyTrend.DOWN -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
                    ) {
                        PricelyText(
                            text = changePercent,
                            style = AppTheme.typography.bodySmall,
                            color = trendColor,
                        )

                        PricelyIcon(
                            painter = painterResource(R.drawable.ic_arrow_down),
                            contentDescription = trendContentDescription,
                            variant = PricelyIconVariant.CIRCLE_BORDER_FILLED,
                            tint = trendColor,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StockItem_Light() {
    PricelyPreview(isDark = false) {
        StockItemShowcase()
    }
}

@Preview(showBackground = true)
@Composable
private fun StockItem_Dark() {
    PricelyPreview(isDark = true) {
        StockItemShowcase()
    }
}

@Composable
private fun StockItemShowcase() {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
    ) {
        PricelyStockItem(
            icon = painterResource(R.drawable.ic_netflix),
            ticker = "NFLX",
            name = "Netflix, Inc.",
            price = "$188.91",
            changePercent = "+1.29%",
            trend = PricelyTrend.UP,
            onClick = {},
        )

        PricelyStockItem(
            icon = painterResource(R.drawable.ic_apple),
            ticker = "AAPL",
            name = "Apple Inc.",
            price = "$178.32",
            changePercent = "-0.87%",
            trend = PricelyTrend.DOWN,
            onClick = {},
        )
    }
}
