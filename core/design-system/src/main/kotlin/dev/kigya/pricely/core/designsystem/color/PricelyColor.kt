package dev.kigya.pricely.core.designsystem.color

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class PricelyColor(
    val primary0: Color = Color(0xFFC5CBEC),
    val primary100: Color = Color(0xFF3E52C1),
    val primary300: Color = Color(0xFF253174),
    val greyscale25: Color = Color(0xFFF8FAFB),
    val greyscale200: Color = Color(0xFFE0E5EB),
    val greyscale300: Color = Color(0xFFA4ACB9),
    val greyscale400: Color = Color(0xFF818898),
    val greyscale500: Color = Color(0xFF666D80),
    val greyscale600: Color = Color(0xFF36394A),
    val greyscale900: Color = Color(0xFF0D0D12),
    val successDark: Color = Color(0xFF16A34A),
    val errorDark: Color = Color(0xFFEF4444),
    val white: Color = Color(0xFFFFFFFF),
)
