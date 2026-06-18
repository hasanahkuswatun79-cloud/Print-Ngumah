package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlueDark,
    secondary = SecondaryOrangeDark,
    tertiary = SecondaryOrangeDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    surfaceVariant = SurfaceDark,
    onPrimary = TextLight,
    onSecondary = TextDark,
    onBackground = TextLight,
    onSurface = TextLight,
    onSurfaceVariant = TextMuted,
    outlineVariant = BorderLight.copy(alpha = 0.1f)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlue,
    secondary = SecondaryOrange,
    tertiary = SecondaryOrange,
    background = BackgroundLight,
    surface = SurfaceLight,
    surfaceVariant = SurfaceLight,
    onPrimary = SurfaceLight,
    onSecondary = SurfaceLight,
    onBackground = TextDark,
    onSurface = TextDark,
    onSurfaceVariant = TextMuted,
    outlineVariant = BorderLight
)

val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            @Suppress("DEPRECATION")
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = AppShapes,
        content = content
    )
}
