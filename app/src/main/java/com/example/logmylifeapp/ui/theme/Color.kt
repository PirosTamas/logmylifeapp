package com.example.logmylifeapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ── Raw color palette ────────────────────────────────────────────────────────
val Green200  = Color(0xFF13EC5B)
val Green600  = Color(0xFF0EBF49)
val Green900  = Color(0xFF102216)
val Blue800   = Color(0xFF1E293B)
val Blue900   = Color(0xFF0F172A)
val OffWhite  = Color(0xFFF1F5F9)
val OffWhite200 = Color(0xFFF6F8F6)
val Grey100   = Color(0xFFF2F4F3)
val Grey300   = Color(0xFF94A3B8)
val Grey700   = Color(0xFF64748B)
val Orange500 = Color(0xFFF97316)
val White     = Color(0xFFFFFFFF)

// ── Semantic color scheme ────────────────────────────────────────────────────
// Screens read colors from AppColors instead of hardcoding colorResource().
// The correct set is provided by LogMyLifeAppTheme via CompositionLocalProvider.
data class AppColors(
    val background: Color,        // main screen background
    val surface: Color,           // card / container background
    val surfaceVariant: Color,    // subtle surface (chips, progress track, picker buttons)
    val onBackground: Color,      // primary text on background
    val onSurface: Color,         // primary text on cards
    val onSurfaceVariant: Color,  // secondary / muted text
    val primary: Color,           // accent (green)
    val onPrimary: Color,         // text / icon ON the green accent
    val inputBackground: Color,   // form field fill
    val inputBorder: Color,       // form field border
    val isDark: Boolean
)

val LightAppColors = AppColors(
    background       = OffWhite200,
    surface          = White,
    surfaceVariant   = Grey100,
    onBackground     = Blue900,
    onSurface        = Blue900,
    onSurfaceVariant = Grey700,
    primary          = Green200,
    onPrimary        = Green900,
    inputBackground  = Color(0x0D13EC5B),
    inputBorder      = Color(0x3313EC5B),
    isDark           = false
)

val DarkAppColors = AppColors(
    background       = Green900,
    surface          = Blue900,
    surfaceVariant   = Blue800,
    onBackground     = OffWhite,
    onSurface        = OffWhite,
    onSurfaceVariant = Grey300,
    primary          = Green200,
    onPrimary        = Green900,
    inputBackground  = Color(0x1A13EC5B),
    inputBorder      = Color(0x3313EC5B),
    isDark           = true
)

// staticCompositionLocalOf: provides AppColors to every composable in the tree.
// Access it anywhere with: val colors = LocalAppColors.current
val LocalAppColors = staticCompositionLocalOf { LightAppColors }
