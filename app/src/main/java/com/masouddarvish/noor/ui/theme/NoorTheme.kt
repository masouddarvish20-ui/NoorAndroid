package com.masouddarvish.noor.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class NoorPalette(
    val name: String,
    val primary: Color,
    val deep: Color,
    val background: Color,
    val soft: Color,
    val accent: Color,
    val text: Color,
    val muted: Color,
    val dark: Boolean = false
)

val Gold = Color(0xFFD7B35A)

val NoorPalettes = listOf(
    NoorPalette("زمردی کلاسیک", Color(0xFF0A6758), Color(0xFF06483E), Color(0xFF07352F), Color(0xFF104B43), Color(0xFFE2B85C), Color(0xFFFFFBF1), Color(0xFFD7E4DF), true),
    NoorPalette("فیروزه‌ای کرم", Color(0xFF237A78), Color(0xFF155C59), Color(0xFFF7F1E7), Color(0xFFFFFBF4), Color(0xFFD6A956), Color(0xFF27403F), Color(0xFF6F7C7B), false),
    NoorPalette("شب طلایی", Color(0xFF233B3B), Color(0xFF111B1B), Color(0xFF090D0D), Color(0xFF172121), Color(0xFFE0B85B), Color(0xFFF8EED8), Color(0xFFB9B2A4), true),
    NoorPalette("روشن آرام", Color(0xFF8D8A6C), Color(0xFF6C694D), Color(0xFFFFFCF6), Color(0xFFF4F0E6), Color(0xFFC69A4B), Color(0xFF4A4431), Color(0xFF7F775E), false)
)

@Composable
fun NoorTheme(
    palette: NoorPalette,
    content: @Composable () -> Unit
) {
    val scheme = if (palette.dark) {
        darkColorScheme(
            primary = palette.primary,
            secondary = palette.accent,
            background = palette.background,
            surface = palette.soft,
            surfaceVariant = palette.deep,
            onPrimary = Color.White,
            onSecondary = Color(0xFF251D0E),
            onBackground = palette.text,
            onSurface = palette.text,
            onSurfaceVariant = palette.muted
        )
    } else {
        lightColorScheme(
            primary = palette.primary,
            secondary = palette.accent,
            background = palette.background,
            surface = palette.soft,
            surfaceVariant = palette.deep,
            onPrimary = Color.White,
            onSecondary = Color(0xFF251D0E),
            onBackground = palette.text,
            onSurface = palette.text,
            onSurfaceVariant = palette.muted
        )
    }

    MaterialTheme(
        colorScheme = scheme,
        typography = Typography(),
        content = content
    )
}
