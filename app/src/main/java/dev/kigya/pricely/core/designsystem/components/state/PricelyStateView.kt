package dev.kigya.pricely.core.designsystem.components.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import dev.kigya.pricely.R
import dev.kigya.pricely.core.designsystem.components.icon.PricelyIcon
import dev.kigya.pricely.core.designsystem.components.text.PricelyText
import dev.kigya.pricely.core.designsystem.preview.PricelyPreview
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import androidx.compose.ui.res.painterResource

@Composable
fun PricelyStateView(
    icon: Painter,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppTheme.dimens.space24),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        PricelyIcon(
            painter = icon,
            contentDescription = null,
            size = AppTheme.dimens.space48,
            tint = AppTheme.colors.primary,
        )

        Spacer(modifier = Modifier.height(AppTheme.dimens.space24))

        PricelyText(
            text = title,
            style = AppTheme.typography.headingLarge,
            color = AppTheme.colors.textPrimary,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(AppTheme.dimens.space8))

        PricelyText(
            text = description,
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colors.textSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun StateViewPreview() {
    PricelyStateView(
        icon = painterResource(R.drawable.ic_sync),
        title = stringResource(R.string.preview_state_title),
        description = stringResource(R.string.preview_state_description),
    )
}

@Preview(showBackground = true)
@Composable
private fun StateView_Light() {
    PricelyPreview(isDark = false) {
        StateViewPreview()
    }
}

@Preview(showBackground = true)
@Composable
private fun StateView_Dark() {
    PricelyPreview(isDark = true) {
        StateViewPreview()
    }
}
