package dev.kigya.pricely.core.designsystem.components.branding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.kigya.pricely.core.designsystem.R
import dev.kigya.pricely.core.designsystem.shape.LocalShapes
import dev.kigya.pricely.core.designsystem.theme.AppTheme

@Composable
fun PricelyAppMark(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = LocalShapes.current.small,
        color = AppTheme.colors.surface,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_icon_logo),
                contentDescription = stringResource(R.string.design_system_cd_app_logo),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.dimens.space4),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(AppTheme.colors.primary),
            )
        }
    }
}
