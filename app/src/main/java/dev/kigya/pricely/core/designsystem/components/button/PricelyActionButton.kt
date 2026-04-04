package dev.kigya.pricely.core.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIcon
import dev.kigya.pricely.core.designsystem.preview.PricelyPreview
import dev.kigya.pricely.core.designsystem.theme.AppTheme

enum class PricelyActionState {
    CONNECTED,
    DISCONNECTED,
    LOADING,
}

@Composable
fun PricelyActionButton(
    state: PricelyActionState,
    icon: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val colors = AppTheme.colors

    val backgroundColor = when (state) {
        PricelyActionState.CONNECTED -> colors.actionButtonConnectedBackground
        PricelyActionState.DISCONNECTED,
        PricelyActionState.LOADING -> colors.actionButtonDisconnectedBackground
    }

    val iconColor = when (state) {
        PricelyActionState.CONNECTED -> colors.actionButtonIconConnected
        PricelyActionState.DISCONNECTED,
        PricelyActionState.LOADING -> colors.actionButtonIconDisconnected
    }

    Box(
        modifier = modifier
            .size(AppTheme.dimens.actionButtonSize)
            .background(backgroundColor, CircleShape)
            .clickable(
                enabled = state != PricelyActionState.LOADING,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            PricelyActionState.LOADING -> {
                CircularProgressIndicator(
                    strokeWidth = AppTheme.dimens.borderThin,
                    modifier = Modifier.size(AppTheme.dimens.actionIconSize),
                    color = iconColor,
                )
            }

            else -> {
                PricelyIcon(
                    painter = icon,
                    contentDescription = contentDescription,
                    size = AppTheme.dimens.actionIconSize,
                    tint = iconColor,
                )
            }
        }
    }
}

@Composable
private fun ActionButtonShowcase() {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16),
        modifier = Modifier.padding(AppTheme.dimens.space16),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16)) {
            PricelyActionButton(
                state = PricelyActionState.CONNECTED,
                icon = painterResource(R.drawable.ic_stop),
                contentDescription = null,
                onClick = {},
            )

            PricelyActionButton(
                state = PricelyActionState.DISCONNECTED,
                icon = painterResource(R.drawable.ic_play),
                contentDescription = null,
                onClick = {},
            )

            PricelyActionButton(
                state = PricelyActionState.LOADING,
                icon = painterResource(R.drawable.ic_play),
                contentDescription = null,
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun ActionButton_Light() {
    PricelyPreview(isDark = false) {
        ActionButtonShowcase()
    }
}

@Preview
@Composable
private fun ActionButton_Dark() {
    PricelyPreview(isDark = true) {
        ActionButtonShowcase()
    }
}
