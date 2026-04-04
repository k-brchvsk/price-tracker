package dev.kigya.pricely.core.designsystem.components.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.preview.PricelyPreview
import dev.kigya.pricely.core.designsystem.theme.AppTheme

enum class PricelyIconVariant {
    PLAIN,
    CIRCLE_BACKGROUND,
    CIRCLE_BORDER,
    CIRCLE_BORDER_FILLED,
}

@Composable
fun PricelyIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    variant: PricelyIconVariant = PricelyIconVariant.PLAIN,
    tint: Color? = AppTheme.colors.textPrimary,
    size: Dp = AppTheme.dimens.icon,
    containerColor: Color? = null,
    borderColor: Color? = null,
) {
    val resolvedTint = when (variant) {
        PricelyIconVariant.CIRCLE_BACKGROUND -> Color.Unspecified
        PricelyIconVariant.CIRCLE_BORDER_FILLED -> tint ?: AppTheme.colors.success
        else -> tint ?: AppTheme.colors.textPrimary
    }

    when (variant) {
        PricelyIconVariant.PLAIN -> {
            Icon(
                painter = painter,
                contentDescription = contentDescription,
                modifier = modifier.size(size),
                tint = resolvedTint,
            )
        }

        PricelyIconVariant.CIRCLE_BACKGROUND -> {
            Box(
                modifier = modifier
                    .size(AppTheme.dimens.iconContainer)
                    .clip(CircleShape)
                    .background(containerColor ?: AppTheme.colors.iconBackground),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(size),
                    tint = resolvedTint,
                )
            }
        }

        PricelyIconVariant.CIRCLE_BORDER -> {
            Box(
                modifier = modifier
                    .size(AppTheme.dimens.icon)
                    .clip(CircleShape)
                    .border(
                        width = AppTheme.dimens.borderThin,
                        color = borderColor ?: AppTheme.colors.primary,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(size),
                    tint = resolvedTint,
                )
            }
        }

        PricelyIconVariant.CIRCLE_BORDER_FILLED -> {
            val bg = containerColor ?: resolvedTint.copy(alpha = 0.1f)

            Box(
                modifier = modifier
                    .size(AppTheme.dimens.icon)
                    .clip(CircleShape)
                    .background(bg)
                    .border(
                        width = AppTheme.dimens.borderThin,
                        color = borderColor ?: resolvedTint,
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painter,
                    contentDescription = contentDescription,
                    modifier = Modifier.size(size),
                    tint = resolvedTint,
                )
            }
        }
    }
}

@Composable
private fun IconShowcase() {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space24),
        modifier = Modifier.padding(AppTheme.dimens.space16)
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16)) {
            PricelyIcon(
                painter = painterResource(R.drawable.ic_arrow_up),
                contentDescription = null,
            )
            PricelyIcon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16)) {
            PricelyIcon(
                painter = painterResource(R.drawable.ic_apple),
                contentDescription = null,
                variant = PricelyIconVariant.CIRCLE_BACKGROUND,
            )
            PricelyIcon(
                painter = painterResource(R.drawable.ic_netflix),
                contentDescription = null,
                variant = PricelyIconVariant.CIRCLE_BACKGROUND,
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16)) {
            PricelyIcon(
                painter = painterResource(R.drawable.ic_arrow_up),
                contentDescription = null,
                variant = PricelyIconVariant.CIRCLE_BORDER,
            )
            PricelyIcon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                variant = PricelyIconVariant.CIRCLE_BORDER,
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16)) {
            PricelyIcon(
                painter = painterResource(R.drawable.ic_arrow_up),
                contentDescription = null,
                variant = PricelyIconVariant.CIRCLE_BORDER_FILLED,
                tint = AppTheme.colors.success,
            )
            PricelyIcon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                variant = PricelyIconVariant.CIRCLE_BORDER_FILLED,
                tint = AppTheme.colors.error,
            )
        }
    }
}

@Preview
@Composable
private fun IconShowcase_Light() {
    PricelyPreview(isDark = false) {
        IconShowcase()
    }
}

@Preview
@Composable
private fun IconShowcase_Dark() {
    PricelyPreview(isDark = true) {
        IconShowcase()
    }
}
