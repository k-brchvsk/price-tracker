package dev.kigya.pricely.core.designsystem.components.status

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.shape.LocalShapes
import dev.kigya.pricely.core.designsystem.theme.AppTheme

enum class PricelyConnectionState {
    CONNECTED,
    DISCONNECTED,
    LOADING,
}

@Composable
fun PricelyConnectionStatus(
    @StringRes labelResourceId: Int,
    state: PricelyConnectionState,
    modifier: Modifier = Modifier,
    labelColorOverride: Color? = null,
) {
    val dotColor = when (state) {
        PricelyConnectionState.CONNECTED -> AppTheme.colors.success
        PricelyConnectionState.DISCONNECTED -> AppTheme.colors.error
        PricelyConnectionState.LOADING -> AppTheme.colors.success
    }
    val labelColor = labelColorOverride ?: when (state) {
        PricelyConnectionState.CONNECTED -> AppTheme.colors.success
        PricelyConnectionState.DISCONNECTED -> AppTheme.colors.error
        PricelyConnectionState.LOADING -> AppTheme.colors.textPrimary
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space4),
    ) {
        Box(
            modifier = Modifier
                .size(AppTheme.dimens.statusDot)
                .background(dotColor, LocalShapes.current.circle),
        )
        PricelyText(
            text = stringResource(labelResourceId),
            style = AppTheme.typography.labelSmall,
            color = labelColor,
        )
    }
}
