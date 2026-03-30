package com.example.habittracker.ui.screens.habit

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.DividerColor
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────
// DATA MODEL
// ─────────────────────────────────────────────────────────────────

data class HabitDetail(
    val emoji       : String,
    val name        : String,
    val progress    : String,
    val arcColor    : Color,
    val arcSweep    : Float,  // degrees 0..360
    val frequency   : String,
    val reminder    : String,
    val streak      : Int,
    val totalDone   : Int,
    val habitType   : String, // "Build" or "Quit"
)

private val sampleHabitDetail = HabitDetail(
    emoji     = "💧",
    name      = "Drink the water",
    progress  = "500 / 2000 ML",
    arcColor  = Primary,
    arcSweep  = 90f,
    frequency = "Daily · Every day",
    reminder  = "09:30 · Every day",
    streak    = 7,
    totalDone = 42,
    habitType = "Build",
)

// ─────────────────────────────────────────────────────────────────
// HABIT DETAIL SCREEN
// Opens when "View" quick action is tapped on a HabitCard
// ─────────────────────────────────────────────────────────────────

@Composable
fun HabitDetailScreen(
    habit       : HabitDetail = sampleHabitDetail,
    onBackClick : () -> Unit = {},
    onEditClick : () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        LazyColumn(
            modifier            = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Top Bar ───────────────────────────────────────────
            item {
                HabitDetailTopBar(
                    onBackClick = onBackClick,
                    onEditClick = onEditClick,
                    modifier    = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 16.dp
                    )
                )
            }

            // ── Large Arc Hero ────────────────────────────────────
            item {
                HabitDetailHero(
                    habit    = habit,
                    modifier = Modifier.padding(vertical = 24.dp)
                )
            }

            // ── Streak + Total Done stat cards ────────────────────
            item {
                HabitStatsRow(
                    streak    = habit.streak,
                    totalDone = habit.totalDone,
                    modifier  = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // ── Frequency + Reminder + Type info card ─────────────
            item {
                HabitInfoCard(
                    habit    = habit,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(28.dp)) }

            // ── Mark Done button ──────────────────────────────────
            item {
                Button(
                    onClick  = { },
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
                        text       = "Mark as Done ✓",
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily,
                        color      = Color.White
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// TOP BAR — back left, title center, edit right
// ─────────────────────────────────────────────────────────────────

@Composable
private fun HabitDetailTopBar(
    onBackClick : () -> Unit,
    onEditClick : () -> Unit,
    modifier    : Modifier = Modifier,
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
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
            text       = "Habit Detail",
            fontSize   = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable { onEditClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.Edit,
                contentDescription = "Edit",
                tint               = TextPrimary,
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HERO — large circular arc ring + emoji + name + progress text
// ─────────────────────────────────────────────────────────────────

@Composable
private fun HabitDetailHero(
    habit    : HabitDetail,
    modifier : Modifier = Modifier,
) {
    Column(
        modifier            = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Large arc ring
        Box(
            modifier         = Modifier.size(160.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Gray track
                drawCircle(
                    color  = Color(0xFFF0F0F5),
                    radius = size.minDimension / 2,
                    style  = Stroke(14.dp.toPx())
                )
                // Colored progress arc
                drawArc(
                    color      = habit.arcColor,
                    startAngle = -90f,
                    sweepAngle = habit.arcSweep,
                    useCenter  = false,
                    topLeft    = Offset(7.dp.toPx(), 7.dp.toPx()),
                    size       = Size(
                        size.width  - 14.dp.toPx(),
                        size.height - 14.dp.toPx()
                    ),
                    style = Stroke(14.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = habit.emoji, fontSize = 40.sp)
                Text(
                    text       = "${(habit.arcSweep / 360f * 100).toInt()}%",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
            }
        }

        // Habit name
        Text(
            text       = habit.name,
            fontSize   = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary,
            textAlign  = TextAlign.Center
        )

        // Progress text e.g. "500 / 2000 ML"
        Text(
            text       = habit.progress,
            fontSize   = 15.sp,
            fontFamily = NunitoFontFamily,
            color      = TextSecondary
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// STATS ROW — Streak card + Total Done card
// ─────────────────────────────────────────────────────────────────

@Composable
private fun HabitStatsRow(
    streak    : Int,
    totalDone : Int,
    modifier  : Modifier = Modifier,
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            emoji    = "🔥",
            value    = "$streak Days",
            label    = "Current Streak",
            modifier = Modifier.weight(1f)
        )
        StatCard(
            emoji    = "✅",
            value    = "$totalDone Times",
            label    = "Total Completed",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    emoji    : String,
    value    : String,
    label    : String,
    modifier : Modifier = Modifier,
) {
    Surface(
        shape           = RoundedCornerShape(16.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier
    ) {
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = emoji, fontSize = 28.sp)
            Text(
                text       = value,
                fontSize   = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color      = TextPrimary
            )
            Text(
                text       = label,
                fontSize   = 12.sp,
                fontFamily = NunitoFontFamily,
                color      = TextSecondary
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// INFO CARD — Frequency + Reminder + Habit Type rows
// ─────────────────────────────────────────────────────────────────

@Composable
private fun HabitInfoCard(
    habit    : HabitDetail,
    modifier : Modifier = Modifier,
) {
    Surface(
        shape           = RoundedCornerShape(16.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow(label = "Frequency",  value = habit.frequency)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color    = DividerColor
            )
            InfoRow(label = "Reminder",   value = habit.reminder)
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color    = DividerColor
            )
            InfoRow(label = "Habit Type", value = habit.habitType)
        }
    }
}

@Composable
private fun InfoRow(
    label : String,
    value : String,
) {
    Row(
        modifier              = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text       = label,
            fontSize   = 14.sp,
            fontFamily = NunitoFontFamily,
            color      = TextSecondary
        )
        Text(
            text       = value,
            fontSize   = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// PREVIEW
// ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HabitDetailScreenPreview() {
    HabitDetailScreen()
}