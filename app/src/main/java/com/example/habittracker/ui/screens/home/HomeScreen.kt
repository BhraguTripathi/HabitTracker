package com.example.habittracker.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.components.BottomNavBar
import com.example.habittracker.ui.components.ChallengeCard
import com.example.habittracker.ui.components.ChallengeCardData
import com.example.habittracker.ui.components.HabitCard
import com.example.habittracker.ui.components.HabitCardData
import com.example.habittracker.ui.components.MiniAvatarStack
import com.example.habittracker.ui.components.NavDestination
import com.example.habittracker.ui.components.SectionHeader
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.BorderColor
import com.example.habittracker.ui.theme.ErrorRed
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.PrimaryContainer
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────
// DATA MODELS — Home screen specific
// ─────────────────────────────────────────────────────────────────
private data class DateItem(
    val dayNum  : Int,
    val dayName : String,
)

// ─────────────────────────────────────────────────────────────────
// SAMPLE DATA
// ─────────────────────────────────────────────────────────────────
private val sampleDates = listOf(
    DateItem(2, "FRI"),
    DateItem(3, "SAT"),
    DateItem(4, "SUN"),
    DateItem(5, "MON"),
    DateItem(6, "TUE"),
    DateItem(7, "WED"),
    DateItem(8, "THU"),
    DateItem(9, "FRI"),
)

private val sampleChallenge = ChallengeCardData(
    title         = "Best Runners! 🏃",
    timeLeft      = "5 days 13 hours left",
    friendsJoined = 2,
    progress      = 0.35f,
)

private val sampleHabits = listOf(
    HabitCardData(1, "💧", "Drink the water", "500/2000 ML",  Primary,            270f, false, 2, 3),
    HabitCardData(2, "🚶", "Walk",            "0/10000 STEPS", Color(0xFF7B8FF7), 50f,  false, 2, 0),
    HabitCardData(3, "🌿", "Water Plants",    "0/1 TIMES",     Color(0xFF28C76F), 10f,  false, 0, 0),
    HabitCardData(4, "🧘", "Meditate",        "30/30 MIN",     Primary,           360f, true,  1, 0),
)

// ─────────────────────────────────────────────────────────────────
// HOME SCREEN
// ─────────────────────────────────────────────────────────────────
@Composable
fun HomeScreen(
    onNavigateExplore   : () -> Unit = {},
    onNavigateActivity  : () -> Unit = {},
    onNavigateProfile   : () -> Unit = {},
    onViewAllHabits     : () -> Unit = {},
    onViewAllChallenges : () -> Unit = {},
) {
    var selectedDateIndex by remember { mutableIntStateOf(1) }
    var selectedTab       by remember { mutableStateOf("Today") }
    var isFabOpen         by remember { mutableStateOf(false) }
    var selectedMood      by remember { mutableIntStateOf(-1) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {

        // ── Scrollable content ────────────────────────────────────
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = 80.dp)
        ) {
            item {
                HomeTopBar(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 12.dp
                    )
                )
            }
            item {
                HomeGreeting(
                    userName = "Mert",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                HomeTabRow(
                    selectedTab   = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier      = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                HomeDateStrip(
                    dates          = sampleDates,
                    selectedIndex  = selectedDateIndex,
                    onDateSelected = { selectedDateIndex = it },
                    modifier       = Modifier.padding(start = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                HomeProgressBanner(
                    completedCount = 1,
                    totalCount     = 4,
                    modifier       = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                SectionHeader(
                    title     = "Challenges",
                    onViewAll = onViewAllChallenges,
                    modifier  = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item {
                ChallengeCard(
                    challenge = sampleChallenge,
                    modifier  = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                SectionHeader(
                    title     = "Habits",
                    onViewAll = onViewAllHabits,
                    modifier  = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            items(sampleHabits) { habit ->
                HabitCard(
                    habit    = habit,
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 4.dp
                    )
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // ── Scrim overlay ─────────────────────────────────────────
        AnimatedVisibility(
            visible = isFabOpen,
            enter   = fadeIn(),
            exit    = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
                    .clickable { isFabOpen = false }
            )
        }

        // ── Add Menu Sheet ────────────────────────────────────────
        AnimatedVisibility(
            visible  = isFabOpen,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            enter    = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit     = slideOutVertically(targetOffsetY = { it }) + fadeOut()
        ) {
            HomeAddMenuSheet(
                selectedMood   = selectedMood,
                onMoodSelected = { selectedMood = it },
                onQuitBadHabit = { isFabOpen = false },
                onNewGoodHabit = { isFabOpen = false }
            )
        }

        // ── Bottom Nav Bar ────────────────────────────────────────
        BottomNavBar(
            currentDestination = NavDestination.HOME,
            isFabOpen          = isFabOpen,
            onFabClick         = { isFabOpen = !isFabOpen },
            onExploreClick     = onNavigateExplore,
            onActivityClick    = onNavigateActivity,
            onProfileClick     = onNavigateProfile,
            modifier           = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// HOME TOP BAR — only used here
// ─────────────────────────────────────────────────────────────────
@Composable
private fun HomeTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Box(
            modifier         = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.CalendarMonth,
                contentDescription = "Calendar",
                tint               = TextPrimary,
                modifier           = Modifier.size(22.dp)
            )
        }

        Box(contentAlignment = Alignment.TopEnd) {
            Box(
                modifier         = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint               = TextPrimary,
                    modifier           = Modifier.size(22.dp)
                )
            }
            Box(
                modifier = Modifier
                    .padding(top = 4.dp, end = 4.dp)
                    .size(8.dp)
                    .background(ErrorRed, CircleShape)
                    .border(1.dp, Color.White, CircleShape)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HOME GREETING — only used here
// ─────────────────────────────────────────────────────────────────
@Composable
private fun HomeGreeting(
    userName : String,
    modifier : Modifier = Modifier
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text       = "Hi, $userName 👋",
                fontSize   = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color      = TextPrimary
            )
            Text(
                text       = "Let's make habits together!",
                fontSize   = 14.sp,
                fontFamily = NunitoFontFamily,
                color      = TextSecondary
            )
        }
        Box(
            modifier         = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color(0xFFDEEFFB)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "😇", fontSize = 28.sp)
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HOME TAB ROW — only used here
// ─────────────────────────────────────────────────────────────────
@Composable
private fun HomeTabRow(
    selectedTab   : String,
    onTabSelected : (String) -> Unit,
    modifier      : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(Color(0xFFE8EDF5))
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {

            // Today
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (selectedTab == "Today") Color.White
                        else Color.Transparent
                    )
                    .clickable { onTabSelected("Today") }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "Today",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NunitoFontFamily,
                    color      = if (selectedTab == "Today") Primary
                    else TextSecondary
                )
            }

            // Clubs with badge
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(28.dp))
                    .background(
                        if (selectedTab == "Clubs") Color.White
                        else Color.Transparent
                    )
                    .clickable { onTabSelected("Clubs") }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text       = "Clubs",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = NunitoFontFamily,
                        color      = if (selectedTab == "Clubs") Primary
                        else TextSecondary
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (selectedTab == "Clubs") PrimaryContainer
                                else Color(0xFFDDE2F0)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text       = "2",
                            fontSize   = 11.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = if (selectedTab == "Clubs") Primary
                            else TextSecondary
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HOME DATE STRIP — only used here
// ─────────────────────────────────────────────────────────────────
@Composable
private fun HomeDateStrip(
    dates          : List<DateItem>,
    selectedIndex  : Int,
    onDateSelected : (Int) -> Unit,
    modifier       : Modifier = Modifier
) {
    LazyRow(
        modifier              = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(dates.size) { index ->
            val date       = dates[index]
            val isSelected = index == selectedIndex

            Box(
                modifier = Modifier
                    .size(width = 58.dp, height = 68.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .then(
                        if (isSelected)
                            Modifier.border(2.dp, Primary, RoundedCornerShape(16.dp))
                        else Modifier
                    )
                    .clickable { onDateSelected(index) },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text       = date.dayNum.toString(),
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily,
                        color      = if (isSelected) Primary else TextPrimary
                    )
                    Text(
                        text       = date.dayName,
                        fontSize   = 11.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = NunitoFontFamily,
                        color      = if (isSelected) Primary else TextSecondary
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HOME PROGRESS BANNER — only used here
// ─────────────────────────────────────────────────────────────────
@Composable
private fun HomeProgressBanner(
    completedCount : Int,
    totalCount     : Int,
    modifier       : Modifier = Modifier
) {
    val progress = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF4A4AF5), Color(0xFF2D2DDB)),
                    start  = Offset(0f, 0f),
                    end    = Offset(Float.POSITIVE_INFINITY, 0f)
                )
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier         = Modifier.size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.size(60.dp)
                ) {
                    drawCircle(
                        color  = Color.White.copy(alpha = 0.25f),
                        radius = size.minDimension / 2,
                        style  = Stroke(5.dp.toPx())
                    )
                    drawArc(
                        color      = Color.White,
                        startAngle = -90f,
                        sweepAngle = 360f * progress,
                        useCenter  = false,
                        topLeft    = Offset(4.dp.toPx(), 4.dp.toPx()),
                        size       = Size(
                            size.width  - 8.dp.toPx(),
                            size.height - 8.dp.toPx()
                        ),
                        style = Stroke(5.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(
                    text       = "${(progress * 100).toInt()}%",
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = Color.White
                )
            }

            Column {
                Text(
                    text       = "Your daily goals almost done! 🔥",
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text       = "$completedCount of $totalCount completed",
                    fontSize   = 13.sp,
                    fontFamily = NunitoFontFamily,
                    color      = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HOME ADD MENU SHEET — only used here
// ─────────────────────────────────────────────────────────────────
@Composable
private fun HomeAddMenuSheet(
    selectedMood   : Int,
    onMoodSelected : (Int) -> Unit,
    onQuitBadHabit : () -> Unit,
    onNewGoodHabit : () -> Unit,
) {
    val moods = listOf("😠", "😟", "😐", "🙂", "😍")

    Column(
        modifier            = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Quit + New row
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Surface(
                onClick  = onQuitBadHabit,
                shape    = RoundedCornerShape(20.dp),
                color    = Color.White,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier              = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text       = "Quit Bad Habit",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = TextPrimary
                        )
                        Text(
                            text       = "Never too late...",
                            fontSize   = 12.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                    Box(
                        modifier         = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFFE5EC)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🛡️", fontSize = 16.sp)
                    }
                }
            }

            Surface(
                onClick  = onNewGoodHabit,
                shape    = RoundedCornerShape(20.dp),
                color    = Color.White,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    modifier              = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text       = "New Good Habit",
                            fontSize   = 15.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = TextPrimary
                        )
                        Text(
                            text       = "For a better life",
                            fontSize   = 12.sp,
                            fontFamily = NunitoFontFamily,
                            color      = TextSecondary
                        )
                    }
                    Box(
                        modifier         = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE5F9EE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "✅", fontSize = 16.sp)
                    }
                }
            }
        }

        // Add Mood row
        Surface(
            shape    = RoundedCornerShape(20.dp),
            color    = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text       = "Add Mood",
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily,
                        color      = TextPrimary
                    )
                    Text(
                        text       = "How're you feeling?",
                        fontSize   = 12.sp,
                        fontFamily = NunitoFontFamily,
                        color      = TextSecondary
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    moods.forEachIndexed { index, emoji ->
                        val isSelected = index == selectedMood
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) Primary else BorderColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .clickable { onMoodSelected(index) },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = emoji, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
