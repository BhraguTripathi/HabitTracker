package com.example.habittracker.ui.screens.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.components.BottomNavBar
import com.example.habittracker.ui.components.MiniAvatarStack
import com.example.habittracker.ui.components.NavDestination
import com.example.habittracker.ui.components.SectionHeader
import com.example.habittracker.ui.theme.*

// ─────────────────────────────────────────────────────────────────
// DATA MODELS
// ─────────────────────────────────────────────────────────────────

data class SuggestedHabitItem(
    val emoji    : String,
    val name     : String,
    val subtitle : String,
    val bgColor  : Color,
)

data class HabitClubItem(
    val emoji       : String,
    val name        : String,
    val memberCount : String,
)

data class ExploreChallengeItem(
    val title         : String,
    val timeLeft      : String,
    val progress      : Float,
    val friendsJoined : Int,
)

data class LearningArticleItem(
    val emoji : String,
    val title : String,
    val bgColor: Color,
)

// ─────────────────────────────────────────────────────────────────
// SAMPLE DATA
// ─────────────────────────────────────────────────────────────────

private val suggestedHabits = listOf(
    SuggestedHabitItem("🚶", "Walk",  "10 km",  ExploreCardPeach),
    SuggestedHabitItem("🏊‍♂️", "Swim",  "30 min", ExploreCardPurple),
    SuggestedHabitItem("📕", "Read",  "20 min", ExploreCardGreen),
    SuggestedHabitItem("🧘", "Meditate", "15 min", HabitPurple),
)

private val habitClubs = listOf(
    HabitClubItem("😻", "Cat Lovers", "462 members"),
    HabitClubItem("🏙️", "Istanbul",   "+500 members"),
    HabitClubItem("🏃‍♂️", "Runners",    "336 members"),
    HabitClubItem("📚", "Book Club",  "218 members"),
)

private val exploreChallenges = listOf(
    ExploreChallengeItem("Best Runners! 🏃",  "5 days 13 hours left", 0.30f, 2),
    ExploreChallengeItem("Best Bikers! 🚴",   "2 days 11 hours left", 0.65f, 1),
    ExploreChallengeItem("Early Birds! 🌅",   "3 days 4 hours left",  0.50f, 3),
)

private val learningArticles = listOf(
    LearningArticleItem("💧", "Why should we drink water often?",   Color(0xFF2D3DA8)),
    LearningArticleItem("🚶", "Benefits of regular walking",        Color(0xFF1E3A5F)),
    LearningArticleItem("😴", "How to build a better sleep routine", Color(0xFF3D2D6B)),
)

// ─────────────────────────────────────────────────────────────────
// EXPLORE SCREEN
// ─────────────────────────────────────────────────────────────────

@Composable
fun ExploreScreen(
    onNavigateHome     : () -> Unit = {},
    onNavigateActivity : () -> Unit = {},
    onNavigateProfile  : () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = 80.dp)
        ) {
            // ── Top Bar ───────────────────────────────────────────
            item {
                ExploreTopBar(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 16.dp
                    )
                )
            }

            // ── Suggested for You ─────────────────────────────────
            item {
                SectionHeader(
                    title    = "Suggested for You",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(suggestedHabits) { habit ->
                        SuggestedHabitCard(habit = habit)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // ── Habit Clubs ───────────────────────────────────────
            item {
                SectionHeader(
                    title    = "Habit Clubs",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(habitClubs) { club ->
                        HabitClubCard(club = club)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // ── Challenges ────────────────────────────────────────
            item {
                SectionHeader(
                    title    = "Challenges",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(exploreChallenges) { challenge ->
                        ExploreChallengeCard(challenge = challenge)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // ── Learning ──────────────────────────────────────────
            item {
                SectionHeader(
                    title    = "Learning",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            item {
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(learningArticles) { article ->
                        LearningArticleCard(article = article)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // ── Bottom Nav Bar ────────────────────────────────────────
        BottomNavBar(
            currentDestination = NavDestination.EXPLORE,
            onHomeClick        = onNavigateHome,
            onActivityClick    = onNavigateActivity,
            onProfileClick     = onNavigateProfile,
            modifier           = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// EXPLORE TOP BAR
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ExploreTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text       = "Explore",
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
                imageVector        = Icons.Default.Search,
                contentDescription = "Search",
                tint               = TextPrimary,
                modifier           = Modifier.size(22.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// SUGGESTED HABIT CARD
// Pastel background, emoji in white circle, name + subtitle
// ─────────────────────────────────────────────────────────────────

@Composable
private fun SuggestedHabitCard(
    habit    : SuggestedHabitItem,
    modifier : Modifier = Modifier
) {
    Surface(
        shape    = RoundedCornerShape(20.dp),
        color    = habit.bgColor,
        modifier = modifier
            .size(width = 150.dp, height = 140.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Emoji in white circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = habit.emoji, fontSize = 22.sp)
            }

            // Name + subtitle
            Column {
                Text(
                    text       = habit.name,
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = habit.subtitle,
                    fontSize   = 13.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HABIT CLUB CARD
// White card, emoji in bordered circle, club name + member count
// ─────────────────────────────────────────────────────────────────

@Composable
private fun HabitClubCard(
    club     : HabitClubItem,
    modifier : Modifier = Modifier
) {
    Surface(
        shape           = RoundedCornerShape(20.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier
            .size(width = 150.dp, height = 130.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Emoji in white circle with border
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .border(1.dp, BorderColor, CircleShape)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = club.emoji, fontSize = 22.sp)
            }

            // Name + member count
            Column {
                Text(
                    text       = club.name,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = club.memberCount,
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// EXPLORE CHALLENGE CARD
// Blue background, clock icon, title, time, progress bar, friends
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ExploreChallengeCard(
    challenge : ExploreChallengeItem,
    modifier  : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 220.dp, height = 160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xFF4A4AF5), Color(0xFF2D2DDB))
                )
            )
            .clickable { }
            .padding(16.dp)
    ) {
        Column(
            modifier            = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Clock icon in semi-transparent circle
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⏱", fontSize = 16.sp)
            }

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                // Title
                Text(
                    text       = challenge.title,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = Color.White
                )
                // Time left
                Text(
                    text       = challenge.timeLeft,
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color      = Color.White.copy(alpha = 0.75f)
                )
                // Progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.25f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(challenge.progress)
                            .fillMaxHeight()
                            .background(Color.White, RoundedCornerShape(2.dp))
                    )
                }
                // Friends joined
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    MiniAvatarStack(count = minOf(challenge.friendsJoined, 2))
                    Text(
                        text       = "${challenge.friendsJoined} friends joined",
                        fontSize   = 11.sp,
                        fontFamily = NunitoFontFamily,
                        color      = Color.White.copy(alpha = 0.85f)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// LEARNING ARTICLE CARD
// Colored background (simulates an image), dark overlay, title
// ─────────────────────────────────────────────────────────────────

@Composable
private fun LearningArticleCard(
    article  : LearningArticleItem,
    modifier : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(width = 220.dp, height = 180.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(article.bgColor)
            .clickable { }
    ) {
        // Large emoji as visual placeholder (replace with real image later)
        Text(
            text     = article.emoji,
            fontSize = 72.sp,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 48.dp)
        )

        // Bottom overlay with title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color(0xFF1A1A6E).copy(alpha = 0.9f)
                        )
                    )
                )
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment     = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Article icon
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "📄", fontSize = 12.sp)
                }
                Text(
                    text       = article.title,
                    fontSize   = 13.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = Color.White,
                    lineHeight = 18.sp
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
fun ExploreScreenPreview() {
    ExploreScreen()
}