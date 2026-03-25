package com.example.habittracker.ui.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Support
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.DividerColor
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.SectionLabel
import com.example.habittracker.ui.theme.SuccessGreen
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────
// SETTINGS SCREEN
// ─────────────────────────────────────────────────────────────────

@Composable
fun SettingsScreen(
    onBackClick          : () -> Unit = {},
    onGeneralClick       : () -> Unit = {},
    onSecurityClick      : () -> Unit = {},
    onNotificationsClick : () -> Unit = {},
    onRateClick          : () -> Unit = {},
    onShareClick         : () -> Unit = {},
    onAboutClick         : () -> Unit = {},
    onSupportClick       : () -> Unit = {},
) {
    // Toggle states
    var isDarkMode      by remember { mutableStateOf(false) }
    var isSoundsOn      by remember { mutableStateOf(true) }
    var isVacationMode  by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        // ── Top Bar ───────────────────────────────────────────────
        SettingsTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(28.dp))

        // ── GENERAL Section ───────────────────────────────────────
        SettingsSectionLabel(label = "GENERAL")

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
            shape           = RoundedCornerShape(20.dp),
            color           = Color.White,
            shadowElevation = 0.dp,
            modifier        = Modifier.fillMaxWidth()
        ) {
            Column {
                // General row
                SettingsArrowRow(
                    icon        = Icons.Default.Settings,
                    iconBgColor = Color(0xFFE8E8FF),
                    iconTint    = Primary,
                    label       = "General",
                    onClick     = onGeneralClick
                )
                SettingsDivider()

                // Dark Mode toggle
                SettingsToggleRow(
                    icon        = Icons.Default.Info, // moon emoji replacement
                    iconBgColor = Color(0xFFF0F0F0),
                    iconTint    = TextSecondary,
                    label       = "Dark Mode",
                    emoji       = "🌙",
                    checked     = isDarkMode,
                    onCheckedChange = { isDarkMode = it }
                )
                SettingsDivider()

                // Security row
                SettingsArrowRow(
                    icon        = Icons.Default.Key,
                    iconBgColor = Color(0xFFF0F0F0),
                    iconTint    = TextSecondary,
                    label       = "Security",
                    onClick     = onSecurityClick
                )
                SettingsDivider()

                // Notifications row
                SettingsArrowRow(
                    icon        = Icons.Default.Notifications,
                    iconBgColor = Color(0xFFF0F0F0),
                    iconTint    = TextPrimary,
                    label       = "Notifications",
                    onClick     = onNotificationsClick
                )
                SettingsDivider()

                // Sounds toggle
                SettingsToggleRow(
                    icon        = if (isSoundsOn) Icons.AutoMirrored.Filled.VolumeUp else Icons.AutoMirrored.Filled.VolumeMute,
                    iconBgColor = Color(0xFFF0F0F0),
                    iconTint    = TextPrimary,
                    label       = "Sounds",
                    checked     = isSoundsOn,
                    onCheckedChange = { isSoundsOn = it }
                )
                SettingsDivider()

                // Vacation Mode toggle
                SettingsToggleRow(
                    icon        = Icons.Default.Star,
                    iconBgColor = Color(0xFFF0F0F0),
                    iconTint    = TextSecondary,
                    label       = "Vacation Mode",
                    emoji       = "🌴",
                    checked     = isVacationMode,
                    onCheckedChange = { isVacationMode = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── ABOUT US Section ──────────────────────────────────────
        SettingsSectionLabel(label = "ABOUT US")

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
            shape           = RoundedCornerShape(20.dp),
            color           = Color.White,
            shadowElevation = 0.dp,
            modifier        = Modifier.fillMaxWidth()
        ) {
            Column {
                // Rate Routiner
                SettingsArrowRow(
                    icon        = Icons.Default.Star,
                    iconBgColor = Color(0xFFFFF3DE),
                    iconTint    = Color(0xFFFFB800),
                    label       = "Rate Routiner",
                    onClick     = onRateClick
                )
                SettingsDivider()

                // Share with Friends
                SettingsArrowRow(
                    icon        = Icons.Filled.Share,
                    iconBgColor = Color(0xFFE8E8FF),
                    iconTint    = Primary,
                    label       = "Share with Friends",
                    onClick     = onShareClick
                )
                SettingsDivider()

                // About Us
                SettingsArrowRow(
                    icon        = Icons.Default.Info,
                    iconBgColor = Color(0xFFF0F0F0),
                    iconTint    = TextSecondary,
                    label       = "About Us",
                    onClick     = onAboutClick
                )
                SettingsDivider()

                // Support
                SettingsArrowRow(
                    icon        = Icons.Default.Support,
                    iconBgColor = Color(0xFFF0F0F0),
                    iconTint    = TextSecondary,
                    label       = "Support",
                    onClick     = onSupportClick
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ─────────────────────────────────────────────────────────────────
// SETTINGS TOP BAR
// Back button + "Settings" title
// ─────────────────────────────────────────────────────────────────

@Composable
private fun SettingsTopBar(
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
            text       = "Settings",
            fontSize   = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// SECTION LABEL
// Uppercase small gray label — "GENERAL", "ABOUT US"
// ─────────────────────────────────────────────────────────────────

@Composable
private fun SettingsSectionLabel(
    label    : String,
    modifier : Modifier = Modifier,
) {
    Text(
        text       = label,
        fontSize   = 12.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = NunitoFontFamily,
        color      = SectionLabel,
        letterSpacing = 1.2.sp,
        modifier   = modifier.padding(start = 4.dp)
    )
}

// ─────────────────────────────────────────────────────────────────
// THIN DIVIDER between rows inside a card
// ─────────────────────────────────────────────────────────────────

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        modifier  = Modifier.padding(horizontal = 16.dp),
        thickness = 1.dp,
        color     = DividerColor
    )
}

// ─────────────────────────────────────────────────────────────────
// SETTINGS ARROW ROW
// Icon | Label | ChevronRight →
// ─────────────────────────────────────────────────────────────────

@Composable
private fun SettingsArrowRow(
    icon        : ImageVector,
    iconBgColor : Color,
    iconTint    : Color,
    label       : String,
    emoji       : String? = null,  // optional emoji override for icon
    onClick     : () -> Unit,
    modifier    : Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SettingsIconBox(
                icon        = icon,
                iconBgColor = iconBgColor,
                iconTint    = iconTint,
                emoji       = emoji
            )
            Text(
                text       = label,
                fontSize   = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = NunitoFontFamily,
                color      = TextPrimary
            )
        }
        Icon(
            imageVector        = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint               = TextSecondary,
            modifier           = Modifier.size(16.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// SETTINGS TOGGLE ROW
// Icon | Label | Switch
// ─────────────────────────────────────────────────────────────────

@Composable
private fun SettingsToggleRow(
    icon            : ImageVector,
    iconBgColor     : Color,
    iconTint        : Color,
    label           : String,
    emoji           : String? = null,
    checked         : Boolean,
    onCheckedChange : (Boolean) -> Unit,
    modifier        : Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SettingsIconBox(
                icon        = icon,
                iconBgColor = iconBgColor,
                iconTint    = iconTint,
                emoji       = emoji
            )
            Text(
                text       = label,
                fontSize   = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = NunitoFontFamily,
                color      = TextPrimary
            )
        }

        Switch(
            checked         = checked,
            onCheckedChange = onCheckedChange,
            colors          = SwitchDefaults.colors(
                checkedThumbColor       = Color.White,
                checkedTrackColor       = SuccessGreen,
                uncheckedThumbColor     = Color.White,
                uncheckedTrackColor     = Color(0xFFD8DCE8),
                uncheckedBorderColor    = Color(0xFFD8DCE8),
            )
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// SETTINGS ICON BOX
// Small rounded square with icon or emoji inside
// ─────────────────────────────────────────────────────────────────

@Composable
private fun SettingsIconBox(
    icon        : ImageVector,
    iconBgColor : Color,
    iconTint    : Color,
    emoji       : String? = null,
) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(iconBgColor),
        contentAlignment = Alignment.Center
    ) {
        if (emoji != null) {
            Text(text = emoji, fontSize = 18.sp)
        } else {
            Icon(
                imageVector        = icon,
                contentDescription = null,
                tint               = iconTint,
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PREVIEW
// ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}