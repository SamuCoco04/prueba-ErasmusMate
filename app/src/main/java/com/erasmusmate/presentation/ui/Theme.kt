package com.erasmusmate.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF355C7D),
    secondary = Color(0xFF6C5B7B),
    tertiary = Color(0xFFC06C84)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF89B4FA),
    secondary = Color(0xFFB4BEFE),
    tertiary = Color(0xFFF2CDCD)
)

@Composable
fun ErasmusMateTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
