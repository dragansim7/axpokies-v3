package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
  darkColorScheme(
    primary = OrangePrimary,
    onPrimary = DarkTextOnOrange,
    primaryContainer = CharcoalCardTop,
    onPrimaryContainer = OrangeLight,
    secondary = OrangeSecondary,
    onSecondary = DarkTextOnOrange,
    background = CharcoalBackground,
    onBackground = TextWhite,
    surface = CharcoalSurface,
    onSurface = TextWhite,
    surfaceVariant = CharcoalSurfaceVariant,
    onSurfaceVariant = TextLightGrey,
    outline = OrangeBorder,
  )

@Composable
fun MyApplicationTheme(
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = DarkColorScheme,
    typography = Typography,
    content = content
  )
}


