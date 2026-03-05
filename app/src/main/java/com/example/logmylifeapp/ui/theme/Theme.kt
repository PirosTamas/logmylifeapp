package com.example.logmylifeapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

// Material3 color schemes built from our own palette.
// Material components (DatePicker, AlertDialog, Switch, etc.) use these.
private val AppDarkColorScheme = darkColorScheme(
    primary = Green200,
    onPrimary = Green900,
    background = Green900,
    surface = Blue900,
    onBackground = OffWhite,
    onSurface = OffWhite,
    surfaceVariant = Blue800,
    onSurfaceVariant = Grey300,
)

private val AppLightColorScheme = lightColorScheme(
    primary = Green200,
    onPrimary = Green900,
    background = OffWhite200,
    surface = White,
    onBackground = Blue900,
    onSurface = Blue900,
    surfaceVariant = Grey100,
    onSurfaceVariant = Grey700,
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
