package dev.kigya.pricely.core.designsystem.components.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import dev.kigya.pricely.core.designsystem.R
import dev.kigya.pricely.core.designsystem.symbol.SymbolBrandIconUrls
import dev.kigya.pricely.core.designsystem.theme.AppTheme

@Composable
fun PricelySymbolIcon(
    ticker: String,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    val defaultSymbolContentDescription = stringResource(R.string.design_system_cd_symbol_logo, ticker)
    val resolvedDescription = contentDescription ?: defaultSymbolContentDescription
    val brandSvgUrl = SymbolBrandIconUrls.svgUrlForTicker(ticker)
    val fallbackPainter = painterResource(R.drawable.ic_icon_logo)
    Surface(
        modifier = modifier.size(AppTheme.dimens.iconContainer),
        shape = CircleShape,
        color = AppTheme.colors.surface,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            val imageModifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.dimens.space4)
            if (brandSvgUrl == null) {
                Image(
                    painter = fallbackPainter,
                    contentDescription = resolvedDescription,
                    modifier = imageModifier,
                    contentScale = ContentScale.Fit,
                )
            } else {
                AsyncImage(
                    model = brandSvgUrl,
                    placeholder = fallbackPainter,
                    error = fallbackPainter,
                    contentDescription = resolvedDescription,
                    modifier = imageModifier,
                    contentScale = ContentScale.Fit,
                )
            }
        }
    }
}
