package com.example.moneypal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Orange600,
    primaryVariant = Orange400,
    error = Red500,
)

private val LightColorPalette = lightColors(
    primary = Orange600,
    primaryVariant = Orange400,
    error = Red500,
)

private val shapes = Shapes(
    small = RoundedCornerShape(percent = 10),
    medium = RoundedCornerShape(percent = 50),
    large = RoundedCornerShape(percent = 99)
)

@Composable
fun MoneyPalTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}