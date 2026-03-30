package com.example.habittracker.ui.screens.leaderboard

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.PrimaryContainer
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────
// DATA MODELS
// ─────────────────────────────────────────────────────────────────

data class LeaderboardEntry(
    val rank        : Int,
    val name        : String,
    val points      : Int,
    val avatarColor : Color,
    val emoji       : String,   // placeholder avatar emoji
)

// ─────────────────────────────────────────────────────────────────
// SAMPLE DATA
// ─────────────────────────────────────────────────────────────────

private val sampleLeaderboard = listOf(
    LeaderboardEntry(1, "Mert Kahveci",      1452, Color(0xFF4A90D9), "🧑"),
    LeaderboardEntry(2, "MirayK",            1223, Color(0xFFFFB5A0), "👩"),
    LeaderboardEntry(3, "Onur O.",            968, Color(0xFFA0C8FF), "👨"),
    LeaderboardEntry(4, "Jennings Stohler",   912, Color(0xFFFF8FAB), "🧑‍🦱"),
    LeaderboardEntry(5, "Scotty Tovias",      846, Color(0xFFB8D4E8), "👦"),
    LeaderboardEntry(6, "Amelina Aguila",     771, Color(0xFFFFB5A0), "👩‍🦰"),
    LeaderboardEntry(7, "Kally Cirrincione",  693, Color(0xFF90CAF9), "👩"),
    LeaderboardEntry(8, "Layla Schupbach",    555, Color(0xFFA5D6A7), "👱‍♀️"),
)

// ─────────────────────────────────────────────────────────────────
// LEADERBOARD SCREEN
// ─────────────────────────────────────────────────────────────────

@Composable
fun LeaderboardScreen(
    onBackClick : () -> Unit = {},
) {
    var selectedTab by remember { mutableStateOf("Weekly") }

    // Top 3 and rest
    val top3 = sampleLeaderboard.take(3)
    val rest  = sampleLeaderboard.drop(3)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEFF3FB))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        // ── Top Bar ───────────────────────────────────────────────
        item {
            LeaderboardTopBar(
                onBackClick = onBackClick,
                modifier    = Modifier
                    .background(Color(0xFFEFF3FB))
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            )
        }

        // ── Daily | Weekly | Monthly Tab ──────────────────────────
        item {
            LeaderboardTabRow(
                selectedTab   = selectedTab,
                onTabSelected = { selectedTab = it },
                modifier      = Modifier
                    .background(Color(0xFFEFF3FB))
                    .padding(horizontal = 20.dp)
            )
        }

        // ── Blue Gradient Hero + Top 3 Podium ────────────────────
        item {
            LeaderboardPodium(
                top3     = top3,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // ── Rank 4+ List ──────────────────────────────────────────
        itemsIndexed(rest) { index, entry ->
            LeaderboardRow(
                entry    = entry,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical   = 5.dp
                )
            )
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

// ─────────────────────────────────────────────────────────────────
// TOP BAR
// Back arrow + "Leaderboard" title
// ─────────────────────────────────────────────────────────────────

@Composable
private fun LeaderboardTopBar(
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
            text       = "Leaderboard",
            fontSize   = 24.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// TAB ROW
// Daily | Weekly | Monthly — same pill pattern as all other tabs
// ─────────────────────────────────────────────────────────────────

@Composable
private fun LeaderboardTabRow(
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
                            if (selectedTab == tab) Color.White
                            else Color.Transparent
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
// PODIUM — Blue gradient box with top 3 rankings
// 2nd (left, shorter pill) | 1st (center, tallest + crown) | 3rd (right)
// ─────────────────────────────────────────────────────────────────

@Composable
private fun LeaderboardPodium(
    top3     : List<LeaderboardEntry>,
    modifier : Modifier = Modifier,
) {
    val first  = top3.getOrNull(0)
    val second = top3.getOrNull(1)
    val third  = top3.getOrNull(2)

    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF4A4AF5), Color(0xFF2D2DDB)),
                    start  = Offset(0f, 0f),
                    end    = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .padding(top = 24.dp, bottom = 32.dp)
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment     = Alignment.Bottom
        ) {
            // ── 2nd Place (left, shorter) ─────────────────────────
            if (second != null) {
                PodiumEntry(
                    entry      = second,
                    rankLabel  = "2nd",
                    pillHeight = 100.dp,
                    avatarSize = 64.dp,
                    showCrown  = false
                )
            }

            // ── 1st Place (center, tallest + crown) ───────────────
            if (first != null) {
                PodiumEntry(
                    entry      = first,
                    rankLabel  = "1st",
                    pillHeight = 130.dp,
                    avatarSize = 72.dp,
                    showCrown  = true
                )
            }

            // ── 3rd Place (right, shorter) ────────────────────────
            if (third != null) {
                PodiumEntry(
                    entry      = third,
                    rankLabel  = "3rd",
                    pillHeight = 90.dp,
                    avatarSize = 60.dp,
                    showCrown  = false
                )
            }
        }
    }
}

@Composable
private fun PodiumEntry(
    entry      : LeaderboardEntry,
    rankLabel  : String,
    pillHeight : androidx.compose.ui.unit.Dp,
    avatarSize : androidx.compose.ui.unit.Dp,
    showCrown  : Boolean,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        // Crown emoji above 1st place
        if (showCrown) {
            Text(text = "👑", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
        } else {
            Spacer(modifier = Modifier.height(32.dp))
        }

        // Avatar circle
        Box(
            modifier = Modifier
                .size(avatarSize)
                .clip(CircleShape)
                .background(entry.avatarColor),
            contentAlignment = Alignment.Center
        ) {
            Text(text = entry.emoji, fontSize = (avatarSize.value * 0.4f).sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Name
        Text(
            text       = entry.name,
            fontSize   = 13.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            color      = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Pill container — white rounded pill with rank + points
        Box(
            modifier = Modifier
                .width(90.dp)
                .height(pillHeight)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.White.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Rank label e.g. "1st"
                Text(
                    text       = rankLabel,
                    fontSize   = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = NunitoFontFamily,
                    color      = Color.White
                )
                // Points chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFFFF0C2))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(text = "🔥", fontSize = 10.sp)
                        Text(
                            text       = "${entry.points}",
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = Color(0xFFB07800)
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// LEADERBOARD ROW
// White card: rank circle + name + points + avatar
// For rank 4 and below
// ─────────────────────────────────────────────────────────────────

@Composable
private fun LeaderboardRow(
    entry    : LeaderboardEntry,
    modifier : Modifier = Modifier,
) {
    Surface(
        shape           = RoundedCornerShape(16.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier.fillMaxWidth()
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            // Rank number circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(PrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "${entry.rank}",
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = Primary
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Name + points
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = entry.name,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = "${entry.points} Points",
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }

            // Avatar circle
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(entry.avatarColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = entry.emoji, fontSize = 18.sp)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PREVIEW
// ─────────────────────────────────────────────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LeaderboardScreenPreview() {
    LeaderboardScreen()
}