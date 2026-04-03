package dev.kigya.pricely.core.designsystem.shape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data object PricelyShapes {
    val small = RoundedCornerShape(8.dp)
    val medium = RoundedCornerShape(12.dp)
    val large = RoundedCornerShape(16.dp)
}

val LocalShapes = staticCompositionLocalOf<PricelyShapes> {
    error("No PricelyShapes provided")
}
