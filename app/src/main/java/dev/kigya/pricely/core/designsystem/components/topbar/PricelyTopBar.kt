package dev.kigya.pricely.core.designsystem.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.components.button.PricelyActionButton
import dev.kigya.pricely.core.designsystem.components.button.PricelyActionState
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIcon
import dev.kigya.pricely.core.designsystem.components.status.PricelyConnectionState
import dev.kigya.pricely.core.designsystem.components.status.PricelyConnectionStatus
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.preview.PricelyPreview
import dev.kigya.pricely.core.designsystem.theme.AppTheme

@Composable
fun PricelyTopBar(
    modifier: Modifier = Modifier,
    startContent: (@Composable () -> Unit)? = null,
    centerContent: (@Composable () -> Unit)? = null,
    endContent: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(
                horizontal = AppTheme.dimens.space16,
                vertical = AppTheme.dimens.space12,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            startContent?.invoke()
        }

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            centerContent?.invoke()
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
        ) {
            endContent?.invoke()
        }
    }
}

@Composable
private fun TopBarFeedPreview(state: PricelyConnectionState) {
    PricelyTopBar(
        startContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.space8),
            ) {
                PricelyIcon(
                    painter = painterResource(R.drawable.ic_icon_logo),
                    tint = AppTheme.colors.primary,
                    contentDescription = null,
                )

                PricelyText(
                    text = stringResource(R.string.topbar_app_name),
                    style = AppTheme.typography.headingMedium,
                )
            }
        },
        endContent = {
            PricelyConnectionStatus(state)

            val isConnected = state == PricelyConnectionState.CONNECTED
            PricelyActionButton(
                state = when (state) {
                    PricelyConnectionState.CONNECTED -> PricelyActionState.CONNECTED
                    PricelyConnectionState.DISCONNECTED -> PricelyActionState.DISCONNECTED
                    PricelyConnectionState.LOADING -> PricelyActionState.LOADING
                },
                icon = painterResource(
                    if (isConnected) R.drawable.ic_stop else R.drawable.ic_play,
                ),
                contentDescription = stringResource(
                    if (isConnected) R.string.topbar_stop_feed
                    else R.string.topbar_start_feed,
                ),
                onClick = {},
            )
        },
    )
}

@Composable
private fun TopBarFeedShowcase() {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space16),
    ) {
        TopBarFeedPreview(PricelyConnectionState.CONNECTED)
        TopBarFeedPreview(PricelyConnectionState.DISCONNECTED)
        TopBarFeedPreview(PricelyConnectionState.LOADING)
    }
}

@Composable
private fun TopBarDetailsPreview() {
    PricelyTopBar(
        startContent = {
            Box(
                modifier = Modifier
                    .clip(AppTheme.shapes.circle)
                    .clickable(onClick = {}),
            ) {
                PricelyIcon(
                    painter = painterResource(R.drawable.ic_chevron_back),
                    contentDescription = stringResource(R.string.topbar_back),
                )
            }
        },
        centerContent = {
            PricelyText(
                text = stringResource(R.string.topbar_symbol_details),
                style = AppTheme.typography.headingMedium,
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun TopBar_Light() {
    PricelyPreview(isDark = false) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space24),
            modifier = Modifier
                .background(AppTheme.colors.background)
                .padding(AppTheme.dimens.space16),
        ) {
            TopBarFeedShowcase()
            TopBarDetailsPreview()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TopBar_Dark() {
    PricelyPreview(isDark = true) {
        Column(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.space24),
            modifier = Modifier
                .background(AppTheme.colors.background)
                .padding(AppTheme.dimens.space16),
        ) {
            TopBarFeedShowcase()
            TopBarDetailsPreview()
        }
    }
}
