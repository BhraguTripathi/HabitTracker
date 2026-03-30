package com.example.habittracker.ui.screens.activity

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import com.example.habittracker.ui.components.BottomNavBar
import com.example.habittracker.ui.components.NavDestination
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.ErrorRed
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.SectionLabel
import com.example.habittracker.ui.theme.SuccessGreen
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────
// DATA MODEL
// ─────────────────────────────────────────────────────────────────

data class ActivitySummary(
    val successRate   : Int,
    val completed     : Int,
    val pointsEarned  : Int,
    val bestStreakDay  : Int,
    val skipped       : Int,
    val failed        : Int,
)

private val sampleSummary = ActivitySummary(
    successRate  = 98,
    completed    = 244,
    pointsEarned = 322,
    bestStreakDay = 22,
    skipped      = 4,
    failed       = 2,
)

// Weekly chart data points (normalized 0..1)
private val chartPoints = listOf(0.55f, 0.45f, 0.30f, 0.50f, 0.65f, 0.55f, 0.70f, 0.95f)
private val chartLabels = listOf("4", "5", "6", "7", "8", "9", "10", "11")

// Mood data: day label → emoji
private val moodData = listOf(
    "S" to "🙂",
    "M" to "😍",
    "T" to null,
    "W" to "🙂",
    "T" to "🙂",
    "F" to null,
    "S" to "😍",
)

// ─────────────────────────────────────────────────────────────────
// ACTIVITY SCREEN
// ─────────────────────────────────────────────────────────────────

@Composable
fun ActivityScreen(
    onNavigateHome    : () -> Unit = {},
    onNavigateExplore : () -> Unit = {},
    onNavigateProfile : () -> Unit = {},
) {
    var selectedTab   by remember { mutableStateOf("Weekly") }
    var weekOffset    by remember { mutableIntStateOf(0) }

    // Simple week label based on offset
    val weekLabel = when (weekOffset) {
        0    -> "This week"
        -1   -> "Last week"
        else -> "${-weekOffset} weeks ago"
    }
    val weekDate = "May 28 - Jun 3"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            // ── Top Bar ───────────────────────────────────────────
            item {
                ActivityTopBar(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 16.dp
                    )
                )
            }

            // ── Daily | Weekly | Monthly Tab ──────────────────────
            item {
                ActivityTabRow(
                    selectedTab   = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier      = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // ── Date navigation ───────────────────────────────────
            item {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text       = weekLabel,
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = TextPrimary
                        )
                        Text(
                            text       = weekDate,
                            fontSize   = 13.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        NavArrowButton(
                            icon    = Icons.AutoMirrored.Filled.ArrowBackIos,
                            onClick = { weekOffset-- }
                        )
                        NavArrowButton(
                            icon    = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            onClick = { if (weekOffset < 0) weekOffset++ }
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // ── All Habits Summary Card ───────────────────────────
            item {
                AllHabitsSummaryCard(
                    summary  = sampleSummary,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // ── Habits Chart Card ─────────────────────────────────
            item {
                HabitsChartCard(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // ── Avg Mood Card ─────────────────────────────────────
            item {
                AvgMoodCard(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // ── Bottom Nav Bar ────────────────────────────────────────
        BottomNavBar(
            currentDestination = NavDestination.ACTIVITY,
            onHomeClick        = onNavigateHome,
            onExploreClick     = onNavigateExplore,
            onProfileClick     = onNavigateProfile,
            hasActivityBadge   = false,
            modifier           = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// TOP BAR — "Activity" + person icon
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ActivityTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text       = "Activity",
            fontSize   = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.Person,
                contentDescription = "Profile",
                tint               = TextPrimary,
                modifier           = Modifier.size(22.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// DAILY | WEEKLY | MONTHLY TAB ROW
// Same segmented pill pattern used in Home + Profile
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ActivityTabRow(
    selectedTab   : String,
    onTabSelected : (String) -> Unit,
    modifier      : Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFFE8EDF5))
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("Daily", "Weekly", "Monthly").forEach { tab ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(28.dp))
                        .background(
                            if (selectedTab == tab) Color.White else Color.Transparent
                        )
                        .clickable { onTabSelected(tab) }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = tab,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = NunitoFontFamily,
                        color      = if (selectedTab == tab) Primary else TextSecondary
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// NAV ARROW BUTTON — < and > week navigation
// ─────────────────────────────────────────────────────────────────

@Composable
private fun NavArrowButton(
    icon    : androidx.compose.ui.graphics.vector.ImageVector,
    onClick : () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            tint               = TextPrimary,
            modifier           = Modifier.size(16.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// ALL HABITS SUMMARY CARD
// Eyes emoji + "All Habits / Summary" + dropdown + 6-stat grid
// ─────────────────────────────────────────────────────────────────

@Composable
private fun AllHabitsSummaryCard(
    summary  : ActivitySummary,
    modifier : Modifier = Modifier,
) {
    Surface(
        shape           = RoundedCornerShape(20.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header row
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF0F0F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "👀", fontSize = 20.sp)
                    }
                    Column {
                        Text(
                            text       = "All Habits",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = TextPrimary
                        )
                        Text(
                            text       = "Summary",
                            fontSize   = 12.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                }
                // Dropdown chevron
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(BackgroundLight),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "∨", fontSize = 16.sp, color = TextSecondary)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2-column stats grid
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier            = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Success Rate
                    StatItem(
                        label = "SUCCESS RATE",
                        content = {
                            Text(
                                text       = "%${summary.successRate}",
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = NunitoFontFamily,
                                color      = SuccessGreen
                            )
                        }
                    )
                    // Points Earned
                    StatItem(
                        label = "POINTS EARNED",
                        content = {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFFFF0C2))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text       = "🔥 ${summary.pointsEarned}",
                                    fontSize   = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = NunitoFontFamily,
                                    color      = Color(0xFFB07800)
                                )
                            }
                        }
                    )
                    // Skipped
                    StatItem(
                        label = "SKIPPED",
                        content = {
                            Text(
                                text       = "${summary.skipped}",
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = NunitoFontFamily,
                                color      = TextPrimary
                            )
                        }
                    )
                }

                Column(
                    modifier            = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Completed
                    StatItem(
                        label = "COMPLETED",
                        content = {
                            Text(
                                text       = "${summary.completed}",
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = NunitoFontFamily,
                                color      = TextPrimary
                            )
                        }
                    )
                    // Best Streak Day
                    StatItem(
                        label = "BEST STREAK DAY",
                        content = {
                            Text(
                                text       = "${summary.bestStreakDay}",
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = NunitoFontFamily,
                                color      = TextPrimary
                            )
                        }
                    )
                    // Failed
                    StatItem(
                        label = "FAILED",
                        content = {
                            Text(
                                text       = "${summary.failed}",
                                fontSize   = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                fontFamily = NunitoFontFamily,
                                color      = ErrorRed
                            )
                        }
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// STAT ITEM — label + value composable slot
// ─────────────────────────────────────────────────────────────────

@Composable
private fun StatItem(
    label   : String,
    content : @Composable () -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text          = label,
            fontSize      = 10.sp,
            fontWeight    = FontWeight.Bold,
            fontFamily    = NunitoFontFamily,
            color         = SectionLabel,
            letterSpacing = 0.8.sp
        )
        content()
    }
}

// ─────────────────────────────────────────────────────────────────
// HABITS CHART CARD
// Line chart drawn with Canvas — wave line + filled gradient below
// ─────────────────────────────────────────────────────────────────

@Composable
private fun HabitsChartCard(modifier: Modifier = Modifier) {
    Surface(
        shape           = RoundedCornerShape(20.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFF0F0F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "📈", fontSize = 20.sp)
                    }
                    Column {
                        Text(
                            text       = "Habits",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = TextPrimary
                        )
                        Text(
                            text       = "Comparison by week",
                            fontSize   = 12.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                }
                // Burn badge
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = BackgroundLight
                ) {
                    Column(
                        modifier            = Modifier.padding(
                            horizontal = 12.dp,
                            vertical   = 6.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text       = "🔥 Burn!",
                            fontSize   = 13.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = TextPrimary
                        )
                        Text(
                            text       = "32 habits",
                            fontSize   = 11.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Wave line chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w        = size.width
                    val h        = size.height - 24.dp.toPx() // leave room for labels
                    val pts      = chartPoints
                    val stepX    = w / (pts.size - 1)

                    // Convert normalized points to canvas coords
                    val coords = pts.mapIndexed { i, v ->
                        Offset(i * stepX, h * (1f - v))
                    }

                    // Filled gradient area under curve
                    val fillPath = Path().apply {
                        moveTo(coords.first().x, h)
                        coords.forEach { lineTo(it.x, it.y) }
                        lineTo(coords.last().x, h)
                        close()
                    }
                    drawPath(
                        path  = fillPath,
                        brush = Brush.verticalGradient(
                            colors    = listOf(
                                Primary.copy(alpha = 0.25f),
                                Color.Transparent
                            ),
                            startY    = 0f,
                            endY      = h
                        )
                    )

                    // Line
                    val linePath = Path().apply {
                        moveTo(coords.first().x, coords.first().y)
                        coords.forEachIndexed { i, pt ->
                            if (i > 0) {
                                val prev = coords[i - 1]
                                val cpX  = (prev.x + pt.x) / 2f
                                cubicTo(cpX, prev.y, cpX, pt.y, pt.x, pt.y)
                            }
                        }
                    }
                    drawPath(
                        path  = linePath,
                        color = Primary,
                        style = Stroke(
                            width     = 3.dp.toPx(),
                            cap       = StrokeCap.Round,
                            join      = StrokeJoin.Round
                        )
                    )

                    // Last point dot
                    drawCircle(
                        color  = Primary,
                        radius = 6.dp.toPx(),
                        center = coords.last()
                    )

                    // Vertical bar under last point
                    drawLine(
                        color       = Primary.copy(alpha = 0.3f),
                        start       = Offset(coords.last().x, coords.last().y + 8.dp.toPx()),
                        end         = Offset(coords.last().x, h),
                        strokeWidth = 2.dp.toPx()
                    )
                }

                // X-axis labels
                Row(
                    modifier              = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    chartLabels.forEach { label ->
                        Text(
                            text       = label,
                            fontSize   = 11.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// AVG MOOD CARD
// Smiley emoji header + mood scatter plot per day
// ─────────────────────────────────────────────────────────────────

@Composable
private fun AvgMoodCard(modifier: Modifier = Modifier) {
    Surface(
        shape           = RoundedCornerShape(20.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Header
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFFFF3DE)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🙂", fontSize = 20.sp)
                }
                Column {
                    Text(
                        text       = "Happy",
                        fontSize   = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily,
                        color      = TextPrimary
                    )
                    Text(
                        text       = "Avg. Mood",
                        fontSize   = 12.sp,
                        fontFamily = NunitoFontFamily,
                        color      = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mood scatter — emoji scattered at varying heights + day labels
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Bottom
            ) {
                moodData.forEachIndexed { _, (day, emoji) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Emoji at different vertical offsets
                        val offsetDp = if (emoji == "😍") 0.dp else 20.dp
                        Box(modifier = Modifier.height(48.dp)) {
                            if (emoji != null) {
                                Text(
                                    text     = emoji,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .align(Alignment.TopCenter)
                                        .padding(top = offsetDp)
                                )
                            }
                        }
                        // Day label
                        Text(
                            text       = day,
                            fontSize   = 11.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PREVIEW
// ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ActivityScreenPreview() {
    ActivityScreen()
}