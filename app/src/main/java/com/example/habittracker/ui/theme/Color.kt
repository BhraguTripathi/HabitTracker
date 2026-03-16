package com.example.habittracker.ui.theme

import androidx.compose.ui.graphics.Color

// ─────────────────────────────────────────────
// PRIMARY BRAND COLORS
// Extracted from Splash, Onboarding, Buttons
// ─────────────────────────────────────────────
val Primary          = Color(0xFF3D3DF5)   // Main blue — buttons, FAB, active nav
val PrimaryLight     = Color(0xFF6C6CF7)   // Lighter blue — gradients top
val PrimaryDark      = Color(0xFF2424C8)   // Darker blue — gradients bottom
val PrimaryContainer = Color(0xFFE8E8FF)   // Very light blue — chip backgrounds

// ─────────────────────────────────────────────
// SPLASH / ONBOARDING GRADIENT
// The blue-purple gradient seen in Splash.png & Onboarding screens
// ─────────────────────────────────────────────
val GradientStart    = Color(0xFF5B52F5)   // Top of gradient (lighter purple-blue)
val GradientEnd      = Color(0xFF2D1FDB)   // Bottom of gradient (deep blue)

// ─────────────────────────────────────────────
// BACKGROUND COLORS
// ─────────────────────────────────────────────
val BackgroundLight  = Color(0xFFEFF3FB)   // Main app background (light blue-gray)
val BackgroundDark   = Color(0xFF13131F)   // Dark mode background
val SurfaceLight     = Color(0xFFFFFFFF)   // Card / sheet surface (white)
val SurfaceDark      = Color(0xFF1E1E2E)   // Dark mode card surface
val SurfaceVariant   = Color(0xFFF5F7FC)   // Slightly off-white surface

// ─────────────────────────────────────────────
// TEXT COLORS
// ─────────────────────────────────────────────
val TextPrimary      = Color(0xFF0F0F1A)   // Main headings — almost black
val TextSecondary    = Color(0xFF8B8FA8)   // Subtitles, labels, metadata
val TextHint         = Color(0xFFB0B5C8)   // Placeholder / hint text
val TextOnPrimary    = Color(0xFFFFFFFF)   // Text on blue buttons/backgrounds
val TextOnDark       = Color(0xFFFFFFFF)   // Text on dark backgrounds

// ─────────────────────────────────────────────
// SECTION LABEL (UPPERCASE SMALL LABELS)
// e.g. "GENERAL", "ABOUT US" in Settings
// ─────────────────────────────────────────────
val SectionLabel     = Color(0xFF9094A4)

// ─────────────────────────────────────────────
// BORDER / DIVIDER
// ─────────────────────────────────────────────
val BorderColor      = Color(0xFFE5E9F2)   // Card borders, dividers
val DividerColor     = Color(0xFFF0F2F8)   // Thin dividers inside cards

// ─────────────────────────────────────────────
// STATE COLORS
// ─────────────────────────────────────────────
val SuccessGreen     = Color(0xFF28C76F)   // Done ✓, Sounds toggle ON
val SuccessLight     = Color(0xFFD4F5E5)   // Success background tint
val ErrorRed         = Color(0xFFFF4B55)   // Fail, broken streak, error
val ErrorLight       = Color(0xFFFFE5E6)   // Error background tint
val WarningOrange    = Color(0xFFFF8C42)   // Warning states

// ─────────────────────────────────────────────
// POINTS / BADGE / GOLD
// Seen in Points chip, Leaderboard, Achievement badge
// ─────────────────────────────────────────────
val PointsGold       = Color(0xFFFFB800)   // Points chip background
val PointsGoldDark   = Color(0xFFE6A500)   // Deeper gold for shadows
val PointsText       = Color(0xFF7A4F00)   // Dark text on gold chip

// ─────────────────────────────────────────────
// SUCCESS SCREEN (Achievement Claim)
// The full golden-yellow background
// ─────────────────────────────────────────────
val AchievementBg    = Color(0xFFFFBD2E)   // Golden yellow background
val AchievementRay   = Color(0xFFFFC94A)   // Lighter ray color

// ─────────────────────────────────────────────
// HABIT ICON BACKGROUND COLORS
// Pastel circles behind habit emoji icons
// ─────────────────────────────────────────────
val HabitBlue        = Color(0xFFDDE8FF)   // Water, general habits
val HabitGreen       = Color(0xFFDDF5E8)   // Plant, nature habits
val HabitOrange      = Color(0xFFFFECDE)   // Walk, exercise habits
val HabitPurple      = Color(0xFFEFDEFF)   // Meditate, calm habits
val HabitPink        = Color(0xFFFFDEEF)   // General / custom
val HabitYellow      = Color(0xFFFFF3DE)   // Reading, study habits

// ─────────────────────────────────────────────
// EXPLORE CARD BACKGROUNDS
// Pastel card backgrounds in Explore screen
// ─────────────────────────────────────────────
val ExploreCardPeach  = Color(0xFFFFECE0)  // Walk card
val ExploreCardPurple = Color(0xFFECE8FF)  // Swim card
val ExploreCardGreen  = Color(0xFFE2F5EC)  // Read card

// ─────────────────────────────────────────────
// NAV BAR
// ─────────────────────────────────────────────
val NavBarBackground = Color(0xFFFFFFFF)
val NavBarIndicator  = Color(0xFFFFFFFF)
val NavIconActive    = Color(0xFF3D3DF5)
val NavIconInactive  = Color(0xFFB0B5C8)

// ─────────────────────────────────────────────
// DARK MODE OVERRIDES
// ─────────────────────────────────────────────
val BackgroundDarkSurface = Color(0xFF1A1A2E)
val CardDark              = Color(0xFF252538)
val TextPrimaryDark       = Color(0xFFF0F2FF)
val TextSecondaryDark     = Color(0xFF8B8FA8)
val BorderDark            = Color(0xFF2E2E45)