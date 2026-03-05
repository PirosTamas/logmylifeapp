package com.example.logmylifeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val AppDarkColorScheme = darkColorScheme(
    primary          = Green._400,
    onPrimary        = Green._950,
    background       = Green._950,
    surface          = Slate._900,
    onBackground     = Slate._100,
    onSurface        = Slate._100,
    surfaceVariant   = Slate._800,
    onSurfaceVariant = Slate._400,
)

private val AppLightColorScheme = lightColorScheme(
    primary          = Green._400,
    onPrimary        = Green._950,
    background       = Slate._50,
    surface          = Color.White,
    onBackground     = Slate._900,
    onSurface        = Slate._900,
    surfaceVariant   = Slate._100,
    onSurfaceVariant = Slate._500,
)

@Composable
fun LogMyLifeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appColors   = if (darkTheme) DarkAppColors   else LightAppColors
    val colorScheme = if (darkTheme) AppDarkColorScheme else AppLightColorScheme

    // CompositionLocalProvider makes appColors available to ALL child composables
    // via LocalAppColors.current — no need to pass colors as parameters.
    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
