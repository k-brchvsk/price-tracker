package dev.kigya.pricely.core.designsystem.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.kigya.pricely.core.designsystem.theme.AppTheme
import dev.kigya.pricely.core.designsystem.theme.PricelyTheme

@Composable
fun PricelyPreview(
    isDark: Boolean,
    content: @Composable () -> Unit,
) {
    PricelyTheme(isDark = isDark) {
        Box(
            modifier = Modifier
                .background(AppTheme.colors.background)
        ) {
            content()
        }
    }
}
