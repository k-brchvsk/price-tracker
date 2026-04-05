package dev.kigya.pricely.core.designsystem.theme

import androidx.compose.runtime.Composable
import dev.kigya.pricely.core.designsystem.color.LocalPricelyColors
import dev.kigya.pricely.core.designsystem.color.PricelyColorScheme
import dev.kigya.pricely.core.designsystem.dimension.LocalDimens
import dev.kigya.pricely.core.designsystem.dimension.PricelyDimens
import dev.kigya.pricely.core.designsystem.shape.LocalShapes
import dev.kigya.pricely.core.designsystem.shape.PricelyShapes
import dev.kigya.pricely.core.designsystem.typography.LocalTypography
import dev.kigya.pricely.core.designsystem.typography.PricelyTypography

object AppTheme {
    val colors: PricelyColorScheme
        @Composable get() = LocalPricelyColors.current

    val typography: PricelyTypography
        @Composable get() = LocalTypography.current

    val dimens: PricelyDimens
        @Composable get() = LocalDimens.current

    val shapes: PricelyShapes
        @Composable get() = LocalShapes.current
}
