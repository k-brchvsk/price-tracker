package dev.kigya.pricely.core.designsystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import dev.kigya.pricely.core.designsystem.R
import dev.kigya.pricely.core.designsystem.theme.AppTheme

enum class PricelyActionState {
    CONNECTED,
    DISCONNECTED,
    LOADING,
}

@Composable
fun PricelyActionButton(
    state: PricelyActionState,
    icon: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val defaultActionContentDescription = stringResource(R.string.design_system_cd_action_button)
    val iconContentDescription = contentDescription ?: defaultActionContentDescription
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
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(
                enabled = state != PricelyActionState.LOADING,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (state == PricelyActionState.LOADING) {
            CircularProgressIndicator(
                strokeWidth = AppTheme.dimens.borderThin,
                modifier = Modifier.size(AppTheme.dimens.actionIconSize),
                color = iconColor,
            )
        } else {
            Icon(
                imageVector = icon,
                contentDescription = iconContentDescription,
                modifier = Modifier.size(AppTheme.dimens.actionIconSize),
                tint = iconColor,
            )
        }
    }
}
