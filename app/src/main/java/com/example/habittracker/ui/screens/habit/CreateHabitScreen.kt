package com.example.habittracker.ui.screens.habit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.BorderColor
import com.example.habittracker.ui.theme.DividerColor
import com.example.habittracker.ui.theme.ExploreCardGreen
import com.example.habittracker.ui.theme.ExploreCardPeach
import com.example.habittracker.ui.theme.ExploreCardPurple
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.SectionLabel
import com.example.habittracker.ui.theme.SuccessGreen
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────
// DATA MODELS
// ─────────────────────────────────────────────────────────────────

data class PopularHabitData(
    val emoji   : String,
    val name    : String,
    val goal    : String,
    val bgColor : Color,
)

private val popularHabits = listOf(
    PopularHabitData("🚶", "Walk",    "10 km",  ExploreCardPeach),
    PopularHabitData("🏊‍♂️", "Swim",    "30 min", ExploreCardPurple),
    PopularHabitData("📕", "Read",    "20 min", ExploreCardGreen),
    PopularHabitData("🧘", "Meditate","15 min", Color(0xFFEFDEFF)),
    PopularHabitData("💧", "Drink",   "2L/day", Color(0xFFDDE8FF)),
)

// Available color options for habit color picker
private val habitColors = listOf(
    Color(0xFFE85D26), // Orange
    Color(0xFF3D3DF5), // Blue (Primary)
    Color(0xFF28C76F), // Green
    Color(0xFFFF4B55), // Red
    Color(0xFFFFB800), // Gold
    Color(0xFF9B51E0), // Purple
    Color(0xFF56CCF2), // Sky blue
    Color(0xFFFF6B9D), // Pink
)

// ─────────────────────────────────────────────────────────────────
// CREATE CUSTOM HABIT SCREEN
// ─────────────────────────────────────────────────────────────────

@Composable
fun CreateHabitScreen(
    onBackClick    : () -> Unit = {},
    onHabitCreated : () -> Unit = {},
) {
    var habitName       by remember { mutableStateOf("Walk") }
    var selectedColor   by remember { mutableStateOf(habitColors[0]) }
    var selectedIcon    by remember { mutableStateOf("🚶") }
    var goalTimes       by remember { mutableIntStateOf(1) }
    var reminderEnabled by remember { mutableStateOf(true) }
    var reminderTime    by remember { mutableStateOf("09:30") }
    var habitType       by remember { mutableStateOf("Build") } // "Build" or "Quit"
    var showGoalDialog  by remember { mutableStateOf(false) }
    var tempGoalTimes   by remember { mutableStateOf("") }

    // --- FREQUENCY STATE ---
    var frequencyType       by remember { mutableStateOf("Daily") } // "Daily", "Weekly", "Monthly"
    var selectedDaysOfWeek  by remember { mutableStateOf(setOf<Int>()) } // 1..7 (1=Mon)
    var selectedDaysOfMonth by remember { mutableStateOf(setOf<Int>()) } // 1..31
    var showFrequencyDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {

        // ── Top Bar ───────────────────────────────────────────────
        CreateHabitTopBar(
            onBackClick = onBackClick,
            modifier    = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
        )

        // ── NAME Section ──────────────────────────────────────────
        CreateHabitSectionLabel(
            label    = "NAME",
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Underline text input
        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            BasicTextField(
                value         = habitName,
                onValueChange = { habitName = it },
                textStyle     = TextStyle(
                    fontSize   = 28.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                ),
                cursorBrush = SolidColor(Primary),
                modifier    = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box {
                        if (habitName.isEmpty()) {
                            Text(
                                text       = "Enter habit name",
                                fontSize   = 28.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = NunitoFontFamily,
                                color      = TextSecondary.copy(alpha = 0.5f)
                            )
                        }
                        innerTextField()
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = DividerColor, thickness = 1.dp)
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── ICON AND COLOR Section ────────────────────────────────
        CreateHabitSectionLabel(
            label    = "ICON AND COLOR",
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier              = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Icon Selector
            IconSelectorCard(
                selectedIcon = selectedIcon,
                modifier     = Modifier.weight(1f)
            )
            // Color Selector
            ColorSelectorCard(
                selectedColor = selectedColor,
                modifier      = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── GOAL Section ──────────────────────────────────────────
        CreateHabitSectionLabel(
            label    = "GOAL",
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape           = RoundedCornerShape(16.dp),
            color           = Color.White,
            shadowElevation = 0.dp,
            modifier        = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Times row
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text       = if (goalTimes > 1) "$goalTimes times" else "$goalTimes time",
                            fontSize   = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = TextPrimary
                        )
                        Text(
                            text       = "per day",
                            fontSize   = 13.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                    // Edit button
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                            .clickable {
                                tempGoalTimes = goalTimes.toString()
                                showGoalDialog = true
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Edit,
                            contentDescription = "Edit goal",
                            tint               = TextSecondary,
                            modifier           = Modifier.size(18.dp)
                        )
                    }
                }

                if (showGoalDialog) {
                    AlertDialog(
                        onDismissRequest = { showGoalDialog = false },
                        title = { Text(
                            text = "Set Goal",
                            color = TextPrimary,
                            fontFamily = NunitoFontFamily,
                            fontWeight = FontWeight.Bold)
                        },
                        text = {
                            OutlinedTextField(
                                value         = tempGoalTimes,
                                onValueChange = { tempGoalTimes = it },
                                label         = { Text("Times per day") },
                                singleLine    = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                                ),
                                textStyle = TextStyle(
                                    color = TextPrimary,
                                    fontFamily = NunitoFontFamily,
                                )
                            )
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                val parsed = tempGoalTimes.toIntOrNull()
                                if (parsed != null && parsed > 0) {
                                    goalTimes = parsed
                                }
                                showGoalDialog = false
                            }) {
                                Text("Save")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showGoalDialog = false }) {
                                Text("Cancel")
                            }
                        },
                        containerColor = BackgroundLight
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Frequency chip row
                val frequencyLabel = when (frequencyType) {
                    "Daily" -> "Every day"
                    "Weekly" -> if (selectedDaysOfWeek.isEmpty()) "Select days" else "${selectedDaysOfWeek.size} days / week"
                    "Monthly" -> if (selectedDaysOfMonth.isEmpty()) "Select dates" else "${selectedDaysOfMonth.size} days / month"
                    else -> ""
                }

                FrequencyChip(
                    icon    = "🔄",
                    left    = frequencyType,
                    right   = frequencyLabel,
                    onClick = { showFrequencyDialog = true }
                )
            }
        }

        if (showFrequencyDialog) {
            FrequencySelectionDialog(
                currentType    = frequencyType,
                currentWeekly  = selectedDaysOfWeek,
                currentMonthly = selectedDaysOfMonth,
                onDismiss      = { showFrequencyDialog = false },
                onSave         = { type, weekly, monthly ->
                    frequencyType       = type
                    selectedDaysOfWeek  = weekly
                    selectedDaysOfMonth = monthly
                    showFrequencyDialog = false
                }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── REMINDERS Section ─────────────────────────────────────
        CreateHabitSectionLabel(
            label    = "REMINDERS",
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape           = RoundedCornerShape(16.dp),
            color           = Color.White,
            shadowElevation = 0.dp,
            modifier        = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Reminder toggle row
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "Remember to set off time for a workout today.",
                        fontSize   = 14.sp,
                        fontFamily = NunitoFontFamily,
                        color      = TextSecondary,
                        modifier   = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Switch(
                        checked         = reminderEnabled,
                        onCheckedChange = { reminderEnabled = it },
                        colors          = SwitchDefaults.colors(
                            checkedThumbColor    = Color.White,
                            checkedTrackColor    = SuccessGreen,
                            uncheckedThumbColor  = Color.White,
                            uncheckedTrackColor  = Color(0xFFD8DCE8),
                            uncheckedBorderColor = Color(0xFFD8DCE8),
                        )
                    )
                }

                if (reminderEnabled) {
                    Spacer(modifier = Modifier.height(12.dp))
                    // Time chip row
                    FrequencyChip(
                        icon  = "🌙",
                        left  = reminderTime,
                        right = "Every day"
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Add Reminder Button ───────────────────────────────────
        Surface(
            shape           = RoundedCornerShape(16.dp),
            color           = Color.White,
            shadowElevation = 0.dp,
            modifier        = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clickable { }
        ) {
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector        = Icons.Default.Add,
                    contentDescription = null,
                    tint               = TextPrimary,
                    modifier           = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text       = "Add Reminder",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── HABIT TYPE Section ────────────────────────────────────
        CreateHabitSectionLabel(
            label    = "HABIT TYPE",
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        HabitTypeSelector(
            selectedType = habitType,
            onTypeSelected = { habitType = it },
            modifier     = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ── LOCATION Section ──────────────────────────────────────
        CreateHabitSectionLabel(
            label    = "LOCATION",
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Surface(
            shape           = RoundedCornerShape(16.dp),
            color           = Color.White,
            shadowElevation = 0.dp,
            modifier        = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clickable { }
        ) {
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFE8E8FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint               = Primary,
                        modifier           = Modifier.size(18.dp)
                    )
                }
                Text(
                    text       = "Add Location",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Add Habit Button ──────────────────────────────────────
        Button(
            onClick  = onHabitCreated,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .height(56.dp),
            shape  = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor   = Color.White
            )
        ) {
            Text(
                text       = "Add Habit",
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color      = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ─────────────────────────────────────────────────────────────────
// FREQUENCY SELECTION DIALOG
// Daily, Weekly, Monthly handling
// ─────────────────────────────────────────────────────────────────

@Composable
fun FrequencySelectionDialog(
    currentType    : String,
    currentWeekly  : Set<Int>,
    currentMonthly : Set<Int>,
    onDismiss      : () -> Unit,
    onSave         : (String, Set<Int>, Set<Int>) -> Unit
) {
    var type by remember { mutableStateOf(currentType) }
    var weekly by remember { mutableStateOf(currentWeekly) }
    var monthly by remember { mutableStateOf(currentMonthly) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Set Frequency",
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color = TextPrimary
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Type Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE8EDF5))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Daily", "Weekly", "Monthly").forEach { option ->
                        val isSelected = type == option
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Color.White else Color.Transparent)
                                .clickable { type = option }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = option,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Primary else TextSecondary,
                                fontFamily = NunitoFontFamily
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Content based on selected type
                when (type) {
                    "Daily" -> {
                        Text(
                            text = "Habit will be active every day.",
                            fontSize = 14.sp,
                            color = TextSecondary,
                            fontFamily = NunitoFontFamily,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    "Weekly" -> {
                        val days = listOf( "S", "M", "T", "W", "T", "F", "S")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            days.forEachIndexed { index, day ->
                                val dayNumber = index + 1
                                val isSelected = weekly.contains(dayNumber)
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Primary else Color(0xFFF0F0F5))
                                        .clickable {
                                            weekly = if (isSelected) weekly - dayNumber else weekly + dayNumber
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = day,
                                        color = if (isSelected) Color.White else TextSecondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                    "Monthly" -> {
                        // Minimalist grid pattern
                        Column(modifier = Modifier.fillMaxWidth()) {
                            for (row in 0..4) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    for (col in 1..7) {
                                        val dayNumber = row * 7 + col
                                        if (dayNumber <= 31) {
                                            val isSelected = monthly.contains(dayNumber)
                                            Box(
                                                modifier = Modifier
                                                    .size(32.dp)
                                                    .clip(CircleShape)
                                                    .background(if (isSelected) Primary else Color(0xFFF0F0F5))
                                                    .clickable {
                                                        monthly = if (isSelected) monthly - dayNumber else monthly + dayNumber
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = dayNumber.toString(),
                                                    fontSize = 12.sp,
                                                    color = if (isSelected) Color.White else TextSecondary
                                                )
                                            }
                                        } else {
                                            Spacer(modifier = Modifier.size(32.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onSave(type, weekly, monthly) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        containerColor = BackgroundLight
    )
}

// ─────────────────────────────────────────────────────────────────
// CREATE HABIT TOP BAR
// Back button + "Create Custom Habit" title
// ─────────────────────────────────────────────────────────────────

@Composable
private fun CreateHabitTopBar(
    onBackClick : () -> Unit,
    modifier    : Modifier = Modifier,
) {
    Row(
        modifier          = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable { onBackClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint               = TextPrimary,
                modifier           = Modifier.size(20.dp)
            )
        }
        Text(
            text       = "Create Custom Habit",
            fontSize   = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// SECTION LABEL
// Uppercase small gray label — reused pattern from Settings
// ─────────────────────────────────────────────────────────────────

@Composable
private fun CreateHabitSectionLabel(
    label    : String,
    modifier : Modifier = Modifier,
) {
    Text(
        text          = label,
        fontSize      = 12.sp,
        fontWeight    = FontWeight.Bold,
        fontFamily    = NunitoFontFamily,
        color         = SectionLabel,
        letterSpacing = 1.2.sp,
        modifier      = modifier.padding(start = 4.dp)
    )
}

// ─────────────────────────────────────────────────────────────────
// ICON SELECTOR CARD
// White card showing selected icon + "Icon" label
// ─────────────────────────────────────────────────────────────────

@Composable
private fun IconSelectorCard(
    selectedIcon : String,
    modifier     : Modifier = Modifier,
) {
    Surface(
        shape           = RoundedCornerShape(16.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier
            .height(72.dp)
            .clickable { }
    ) {
        Row(
            modifier          = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Icon in light gray rounded square
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF0F0F5)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = selectedIcon, fontSize = 22.sp)
            }
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text       = "Walking",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = "Icon",
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// COLOR SELECTOR CARD
// White card showing selected color circle + "Color" label
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ColorSelectorCard(
    selectedColor : Color,
    modifier      : Modifier = Modifier,
) {
    Surface(
        shape           = RoundedCornerShape(16.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier
            .height(72.dp)
            .clickable { }
    ) {
        Row(
            modifier          = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Color circle
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(selectedColor)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text       = "Orange",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = "Color",
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// FREQUENCY CHIP
// Gray pill showing icon + left text + separator + right text
// Used for "Daily | Every day" and "09:30 | Every day"
// ─────────────────────────────────────────────────────────────────

@Composable
private fun FrequencyChip(
    icon    : String,
    left    : String,
    right   : String,
    onClick : () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BackgroundLight)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = icon, fontSize = 14.sp)
            Text(
                text       = left,
                fontSize   = 13.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = NunitoFontFamily,
                color      = TextPrimary
            )
            Text(
                text       = "·",
                fontSize   = 16.sp,
                color      = TextSecondary
            )
            Text(
                text       = right,
                fontSize   = 13.sp,
                fontFamily = NunitoFontFamily,
                color      = TextSecondary
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HABIT TYPE SELECTOR
// "Build | Quit" segmented toggle — white pill slides
// ─────────────────────────────────────────────────────────────────

@Composable
private fun HabitTypeSelector(
    selectedType   : String,
    onTypeSelected : (String) -> Unit,
    modifier       : Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFFE8EDF5))
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            // Build tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (selectedType == "Build") Color.White
                        else Color.Transparent
                    )
                    .clickable { onTypeSelected("Build") }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "Build",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NunitoFontFamily,
                    color      = if (selectedType == "Build") Primary
                    else TextSecondary
                )
            }
            // Quit tab
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (selectedType == "Quit") Color.White
                        else Color.Transparent
                    )
                    .clickable { onTypeSelected("Quit") }
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "Quit",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NunitoFontFamily,
                    color      = if (selectedType == "Quit") Primary
                    else TextSecondary
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// NEW GOOD HABIT BOTTOM SHEET
// Slides up from FAB — shown over Home screen
// Has: "Create Custom Habit" row + popular habits grid
// ─────────────────────────────────────────────────────────────────

@Composable
fun NewGoodHabitSheet(
    onCreateCustomHabit : () -> Unit = {},
    onHabitSelected     : (PopularHabitData) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BackgroundLight,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            )
            .padding(top = 12.dp, bottom = 32.dp)
    ) {
        // Drag handle
        Box(
            modifier = Modifier
                .size(width = 40.dp, height = 4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(Color(0xFFD8DCE8))
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── NEW GOOD HABIT label ──────────────────────────────────
        Text(
            text          = "NEW GOOD HABIT",
            fontSize      = 12.sp,
            fontWeight    = FontWeight.Bold,
            fontFamily    = NunitoFontFamily,
            color         = SectionLabel,
            letterSpacing = 1.2.sp,
            modifier      = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ── Create Custom Habit row ───────────────────────────────
        Surface(
            shape           = RoundedCornerShape(16.dp),
            color           = Color.White,
            shadowElevation = 0.dp,
            modifier        = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clickable { onCreateCustomHabit() }
        ) {
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text       = "Create Custom Habit",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, BorderColor, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = Icons.Default.Add,
                        contentDescription = null,
                        tint               = TextPrimary,
                        modifier           = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── POPULAR HABITS label ──────────────────────────────────
        Text(
            text          = "POPULAR HABITS",
            fontSize      = 12.sp,
            fontWeight    = FontWeight.Bold,
            fontFamily    = NunitoFontFamily,
            color         = SectionLabel,
            letterSpacing = 1.2.sp,
            modifier      = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // ── Popular habit cards row ───────────────────────────────
        LazyRow(
            contentPadding        = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(popularHabits) { habit ->
                PopularHabitCard(
                    habit   = habit,
                    onClick = { onHabitSelected(habit) }
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// POPULAR HABIT CARD
// Pastel card: emoji + name + goal — used in the sheet
// ─────────────────────────────────────────────────────────────────

@Composable
private fun PopularHabitCard(
    habit   : PopularHabitData,
    onClick : () -> Unit,
) {
    Surface(
        shape    = RoundedCornerShape(20.dp),
        color    = habit.bgColor,
        modifier = Modifier
            .size(width = 120.dp, height = 120.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier            = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = habit.emoji, fontSize = 20.sp)
            }
            Column {
                Text(
                    text       = habit.name,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = habit.goal,
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PREVIEW
// ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateHabitScreenPreview() {
    CreateHabitScreen()
}

@Preview(showBackground = true)
@Composable
fun NewGoodHabitSheetPreview() {
    NewGoodHabitSheet()
}
