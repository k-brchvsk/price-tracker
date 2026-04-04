package dev.kigya.pricely.core.designsystem.components.status

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.preview.PricelyPreview
import dev.kigya.pricely.core.designsystem.shape.LocalShapes
import dev.kigya.pricely.core.designsystem.theme.AppTheme

enum class PricelyConnectionState {
    CONNECTED,
    DISCONNECTED,
    LOADING,
}

@Composable
fun PricelyConnectionStatus(
    state: PricelyConnectionState,
    modifier: Modifier = Modifier,
) {
    val colors = AppTheme.colors

    val (textRes, dotColor) = when (state) {
        PricelyConnectionState.CONNECTED -> R.string.connection_status_connected to colors.success
        PricelyConnectionState.DISCONNECTED -> R.string.connection_status_disconnected to colors.error
        PricelyConnectionState.LOADING -> R.string.connection_status_loading to colors.primary
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
            text = stringResource(textRes),
            style = AppTheme.typography.labelSmall,
            color = AppTheme.colors.textPrimary,
        )
    }
}

@Composable
private fun ConnectionStatusShowcase() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16),
    ) {
        PricelyConnectionStatus(PricelyConnectionState.CONNECTED)
        PricelyConnectionStatus(PricelyConnectionState.DISCONNECTED)
        PricelyConnectionStatus(PricelyConnectionState.LOADING)
    }
}

@Preview
@Composable
private fun ConnectionStatus_Light() {
    PricelyPreview(isDark = false) {
        ConnectionStatusShowcase()
    }
}

@Preview
@Composable
private fun ConnectionStatus_Dark() {
    PricelyPreview(isDark = true) {
        ConnectionStatusShowcase()
    }
}
