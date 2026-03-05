package com.example.logmylifeapp.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Suppress("unused")
object Slate {
    val _50  = Color(0xFFF8FAFC)
    val _100 = Color(0xFFF1F5F9)
    val _200 = Color(0xFFE2E8F0)
    val _300 = Color(0xFFCBD5E1)
    val _400 = Color(0xFF94A3B8)
    val _500 = Color(0xFF64748B)
    val _600 = Color(0xFF475569)
    val _700 = Color(0xFF334155)
    val _800 = Color(0xFF1E293B)
    val _900 = Color(0xFF0F172A)
    val _950 = Color(0xFF020617)
}

object Green {
    val _400 = Color(0xFF13EC5B)
    val _600 = Color(0xFF0EBF49)
    val _950 = Color(0xFF102216)
}

object Orange {
    val _500 = Color(0xFFF97316)
}

data class AppColors(
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onBackground: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val inputBackground: Color,
    val inputBorder: Color,
    val isDark: Boolean
)

val LightAppColors = AppColors(
    background       = Slate._50,
    surface          = Color.White,
    surfaceVariant   = Slate._100,
    onBackground     = Slate._900,
    onSurface        = Slate._900,
    onSurfaceVariant = Slate._500,
    primary          = Green._400,
    onPrimary        = Green._950,
    inputBackground  = Color.White,
    inputBorder      = Slate._200,
    isDark           = false
)

val DarkAppColors = AppColors(
    background       = Green._950,
    surface          = Slate._900,
    surfaceVariant   = Slate._800,
    onBackground     = Slate._100,
    onSurface        = Slate._100,
    onSurfaceVariant = Slate._400,
    primary          = Green._400,
    onPrimary        = Green._950,
    inputBackground  = Color(0x1A13EC5B),
    inputBorder      = Color(0x3313EC5B),
    isDark           = true
)

val LocalAppColors = staticCompositionLocalOf { LightAppColors }
