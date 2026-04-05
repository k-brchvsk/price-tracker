package dev.kigya.pricely.core.designsystem.components.stock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.SouthEast
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import dev.kigya.pricely.core.designsystem.R
import dev.kigya.pricely.core.designsystem.theme.AppTheme

enum class PricelyTrendCircleStyle {
    DIAGONAL_ARROWS,
    VERTICAL_ARROWS,
}

enum class PricelyTrendCircleVariant {
    /** Solid trend color + white glyph. */
    SOLID,
    /** Soft fill + trend-colored glyph (e.g. feed list). */
    SOFT_BORDERED,
}

@Composable
fun PricelyTrendCircleIcon(
    trend: PricelyTrend,
    modifier: Modifier = Modifier,
    style: PricelyTrendCircleStyle = PricelyTrendCircleStyle.DIAGONAL_ARROWS,
    variant: PricelyTrendCircleVariant = PricelyTrendCircleVariant.SOLID,
    circleSize: Dp = AppTheme.dimens.trendCircleSize,
    iconSize: Dp = AppTheme.dimens.trendCircleIconSize,
) {
    if (trend == PricelyTrend.NEUTRAL) return
    val trendColor = when (trend) {
        PricelyTrend.UP -> AppTheme.colors.success
        PricelyTrend.DOWN -> AppTheme.colors.error
        PricelyTrend.NEUTRAL -> return
    }
    val icon: ImageVector = when (style) {
        PricelyTrendCircleStyle.DIAGONAL_ARROWS -> when (trend) {
            PricelyTrend.UP -> Icons.Filled.NorthEast
            PricelyTrend.DOWN -> Icons.Filled.SouthEast
            PricelyTrend.NEUTRAL -> error("neutral")
        }
        PricelyTrendCircleStyle.VERTICAL_ARROWS -> when (trend) {
            PricelyTrend.UP -> Icons.Filled.ArrowUpward
            PricelyTrend.DOWN -> Icons.Filled.ArrowDownward
            PricelyTrend.NEUTRAL -> error("neutral")
        }
    }
    val description = stringResource(
        when (trend) {
            PricelyTrend.UP -> R.string.design_system_cd_trend_up
            PricelyTrend.DOWN -> R.string.design_system_cd_trend_down
            PricelyTrend.NEUTRAL -> error("neutral")
        },
    )
    val boxModifier = when (variant) {
        PricelyTrendCircleVariant.SOLID ->
            modifier
                .size(circleSize)
                .clip(CircleShape)
                .background(trendColor)
        PricelyTrendCircleVariant.SOFT_BORDERED ->
            modifier
                .size(circleSize)
                .clip(CircleShape)
                .background(trendColor.copy(alpha = 0.12f))
    }
    val iconTint = when (variant) {
        PricelyTrendCircleVariant.SOLID -> Color.White
        PricelyTrendCircleVariant.SOFT_BORDERED -> trendColor
    }
    Box(
        modifier = boxModifier,
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = iconTint,
            modifier = Modifier.size(iconSize),
        )
    }
}
