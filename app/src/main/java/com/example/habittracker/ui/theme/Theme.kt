package com.example.habittracker.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.habittracker.ui.theme.HabitTypography

// ─────────────────────────────────────────────
// LIGHT COLOR SCHEME
// ─────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(

    // Primary — main blue used on buttons, FAB, active tabs
    primary              = Primary,
    onPrimary            = TextOnPrimary,
    primaryContainer     = PrimaryContainer,
    onPrimaryContainer   = PrimaryDark,

    // Secondary — used for secondary actions, chips
    secondary            = PrimaryLight,
    onSecondary          = TextOnPrimary,
    secondaryContainer   = PrimaryContainer,
    onSecondaryContainer = PrimaryDark,

    // Background — main screen background
    background           = BackgroundLight,
    onBackground         = TextPrimary,

    // Surface — cards, sheets, dialogs
    surface              = SurfaceLight,
    onSurface            = TextPrimary,
    surfaceVariant       = SurfaceVariant,
    onSurfaceVariant     = TextSecondary,

    // Outline — borders, dividers
    outline              = BorderColor,
    outlineVariant       = DividerColor,

    // Error
    error                = ErrorRed,
    onError              = TextOnPrimary,
    errorContainer       = ErrorLight,
    onErrorContainer     = ErrorRed,

    // Inverse
    inverseSurface       = TextPrimary,
    inverseOnSurface     = SurfaceLight,
    inversePrimary       = PrimaryLight,

    // Scrim (overlay behind bottom sheets)
    scrim                = TextPrimary.copy(alpha = 0.4f),
)

// ─────────────────────────────────────────────
// DARK COLOR SCHEME
// ─────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(

    primary              = PrimaryLight,
    onPrimary            = TextOnPrimary,
    primaryContainer     = PrimaryDark,
    onPrimaryContainer   = PrimaryContainer,

    secondary            = PrimaryLight,
    onSecondary          = TextOnPrimary,
    secondaryContainer   = PrimaryDark,
    onSecondaryContainer = PrimaryContainer,

    background           = BackgroundDark,
    onBackground         = TextPrimaryDark,

    surface              = SurfaceDark,
    onSurface            = TextPrimaryDark,
    surfaceVariant       = CardDark,
    onSurfaceVariant     = TextSecondaryDark,

    outline              = BorderDark,
    outlineVariant       = BorderDark,

    error                = ErrorRed,
    onError              = TextOnPrimary,
    errorContainer       = ErrorRed.copy(alpha = 0.2f),
    onErrorContainer     = ErrorRed,

    inverseSurface       = TextPrimaryDark,
    inverseOnSurface     = SurfaceDark,
    inversePrimary       = Primary,

    scrim                = Color(0xFF000000).copy(alpha = 0.5f),
)

// ─────────────────────────────────────────────
// MAIN THEME COMPOSABLE
// ─────────────────────────────────────────────
@Composable
fun HabitTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Set to false — we use our own brand colors, not Android dynamic colors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    // ── Status Bar Styling ──────────────────
    // Makes status bar transparent and sets icon colors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Edge-to-edge: let content draw behind status bar
            WindowCompat.setDecorFitsSystemWindows(window, false)
            // Status bar icons: dark on light screens, light on dark/blue screens
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = HabitTypography,
        shapes      = HabitShapes,
        content     = content
    )
}