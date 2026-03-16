package com.example.habittracker.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val HabitShapes = Shapes(

    // Extra Small — Small chips, badges, tiny tags
    // e.g. "+3" avatar badge, Points chip
    extraSmall = RoundedCornerShape(6.dp),

    // Small — Input fields, small buttons
    // e.g. Text fields in Login / Create Account
    small = RoundedCornerShape(10.dp),

    // Medium — Habit cards, Settings rows, Date strip items
    // e.g. HabitCard, ChallengeCard, SettingsItem
    medium = RoundedCornerShape(16.dp),

    // Large — Bottom sheets, main cards, Profile cards
    // e.g. New Good Habit sheet, Leaderboard top card
    large = RoundedCornerShape(20.dp),

    // Extra Large — Full-width primary buttons
    // e.g. "Next", "Continue with E-mail", "Add Habit"
    extraLarge = RoundedCornerShape(32.dp),
)

// ─────────────────────────────────────────────
// CUSTOM SHAPES (used directly in Composables)
// ─────────────────────────────────────────────

// Fully circular — avatar images, FAB button
val CircleShape = RoundedCornerShape(50)

// Bottom sheet top corners only
val BottomSheetShape = RoundedCornerShape(
    topStart    = 24.dp,
    topEnd      = 24.dp,
    bottomStart = 0.dp,
    bottomEnd   = 0.dp
)

// Date strip selected day pill
val DatePillShape = RoundedCornerShape(14.dp)

// Nav bar FAB shape (larger circular)
val FabShape = RoundedCornerShape(50)

// Habit icon container (rounded square)
val HabitIconShape = RoundedCornerShape(14.dp)