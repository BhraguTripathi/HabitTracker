package com.example.habittracker.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.components.BottomNavBar
import com.example.habittracker.ui.components.NavDestination
import com.example.habittracker.ui.theme.*

// ─────────────────────────────────────────────────────────────────
// DATA MODELS
// ─────────────────────────────────────────────────────────────────

enum class ProfileTab { ACTIVITY, FRIENDS, ACHIEVEMENTS }

data class ActivityItem(
    val title     : String,
    val timestamp : String,
    val type      : ActivityType,
)

enum class ActivityType { POINTS_EARNED, CHALLENGE_COMPLETED, STREAK_BROKEN }

data class FriendItem(
    val id     : Int,
    val emoji  : String,       // placeholder avatar color/emoji
    val name   : String,
    val points : Int,
)

data class AchievementItem(
    val emoji     : String,
    val bgColor   : Color,
    val title     : String,
    val timeAgo   : String,
)

// ─────────────────────────────────────────────────────────────────
// SAMPLE DATA
// ─────────────────────────────────────────────────────────────────

private val sampleActivity = listOf(
    ActivityItem("112 points earned!",             "Today, 12:34 PM",       ActivityType.POINTS_EARNED),
    ActivityItem("62 points earned!",              "Today, 07:12 AM",       ActivityType.POINTS_EARNED),
    ActivityItem("Challenge completed!",           "Yesterday, 14:12 PM",   ActivityType.CHALLENGE_COMPLETED),
    ActivityItem("Weekly winning streak is broken!", "12 Jun, 16:14 PM",    ActivityType.STREAK_BROKEN),
    ActivityItem("96 points earned!",              "11 Jun, 17:45 PM",      ActivityType.POINTS_EARNED),
    ActivityItem("110 points earned!",             "10 Jun, 18:32 PM",      ActivityType.POINTS_EARNED),
)

private val sampleFriends = listOf(
    FriendItem(1, "🧑‍🦰", "Sharie Bento",     912),
    FriendItem(2, "👨‍💼", "Micah Dantoni",    912),
    FriendItem(3, "🧔",  "Oral Padlo",       912),
    FriendItem(4, "👩",  "Regina Stire",     912),
    FriendItem(5, "👩‍🦱", "Maressa Mcdiarmid",912),
    FriendItem(6, "🧑‍🦲", "Jennings Stohler", 912),
)

private val sampleAchievements = listOf(
    AchievementItem("🏃", Color(0xFFDDE8FF), "Best Runner!",        "1 months ago"),
    AchievementItem("🥇", Color(0xFFFFF3DE), "Best of the month!",  "2 days ago"),
)

// ─────────────────────────────────────────────────────────────────
// PROFILE SCREEN
// ─────────────────────────────────────────────────────────────────

@Composable
fun ProfileScreen(
    onNavigateHome     : () -> Unit = {},
    onNavigateExplore  : () -> Unit = {},
    onNavigateActivity : () -> Unit = {},
    onSettingsClick    : () -> Unit = {},
) {
    var selectedTab by remember { mutableStateOf(ProfileTab.ACTIVITY) }

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
                ProfileTopBar(
                    onSettingsClick = onSettingsClick,
                    modifier        = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 16.dp
                    )
                )
            }

            // ── Avatar + Name + Points ────────────────────────────
            item {
                ProfileHeader(
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // ── Tab Selector ──────────────────────────────────────
            item {
                ProfileTabRow(
                    selectedTab   = selectedTab,
                    onTabSelected = { selectedTab = it },
                    modifier      = Modifier.padding(horizontal = 20.dp)
                )
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }

            // ── Tab Content ───────────────────────────────────────
            when (selectedTab) {
                ProfileTab.ACTIVITY -> {
                    item {
                        ActivityTabHeader(
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    items(sampleActivity) { item ->
                        ActivityRow(
                            item     = item,
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical   = 4.dp
                            )
                        )
                    }
                }

                ProfileTab.FRIENDS -> {
                    item {
                        FriendsTabHeader(
                            count    = sampleFriends.size,
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    items(sampleFriends) { friend ->
                        FriendRow(
                            friend   = friend,
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical   = 4.dp
                            )
                        )
                    }
                }

                ProfileTab.ACHIEVEMENTS -> {
                    item {
                        Text(
                            text       = "${sampleAchievements.size} Achievements",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = NunitoFontFamily,
                            color      = TextPrimary,
                            modifier   = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                    item { Spacer(modifier = Modifier.height(12.dp)) }
                    items(sampleAchievements) { achievement ->
                        AchievementRow(
                            achievement = achievement,
                            modifier    = Modifier.padding(
                                horizontal = 20.dp,
                                vertical   = 4.dp
                            )
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }

        // ── Bottom Nav Bar ────────────────────────────────────────
        BottomNavBar(
            currentDestination = NavDestination.PROFILE,
            onHomeClick        = onNavigateHome,
            onExploreClick     = onNavigateExplore,
            onActivityClick    = onNavigateActivity,
            modifier           = Modifier.align(Alignment.BottomCenter)
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// PROFILE TOP BAR
// "Your Profile" + settings gear button
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ProfileTopBar(
    onSettingsClick : () -> Unit,
    modifier        : Modifier = Modifier,
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text       = "Your Profile",
            fontSize   = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable { onSettingsClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.Settings,
                contentDescription = "Settings",
                tint               = TextPrimary,
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PROFILE HEADER
// Avatar circle + Name + Points chip
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ProfileHeader(modifier: Modifier = Modifier) {
    Row(
        modifier          = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Avatar circle (emoji placeholder — swap with Coil image later)
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color(0xFF4A90D9))
                .border(2.dp, Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "😎", fontSize = 30.sp)
        }

        // Name + Points chip
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text       = "Bhragu Tripathi",
                fontSize   = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color      = TextPrimary
            )
            // Gold points chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFFFF0C2))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "🔥", fontSize = 14.sp)
                    Text(
                        text       = "1452 Points",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily,
                        color      = Color(0xFFB07800)
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PROFILE TAB ROW
// Activity | Friends | Achievements with sliding white pill
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ProfileTabRow(
    selectedTab   : ProfileTab,
    onTabSelected : (ProfileTab) -> Unit,
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
            ProfileTabItem(
                label      = "Activity",
                isSelected = selectedTab == ProfileTab.ACTIVITY,
                onClick    = { onTabSelected(ProfileTab.ACTIVITY) },
                modifier   = Modifier.weight(1f)
            )
            ProfileTabItem(
                label      = "Friends",
                isSelected = selectedTab == ProfileTab.FRIENDS,
                onClick    = { onTabSelected(ProfileTab.FRIENDS) },
                modifier   = Modifier.weight(1f)
            )
            ProfileTabItem(
                label      = "Achievements",
                isSelected = selectedTab == ProfileTab.ACHIEVEMENTS,
                onClick    = { onTabSelected(ProfileTab.ACHIEVEMENTS) },
                modifier   = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun ProfileTabItem(
    label      : String,
    isSelected : Boolean,
    onClick    : () -> Unit,
    modifier   : Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(28.dp))
            .background(if (isSelected) Color.White else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = label,
            fontSize   = 13.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = NunitoFontFamily,
            color      = if (isSelected) Primary else TextSecondary
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// ACTIVITY TAB — Header row
// "Showing last month activity" + filter icon button
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ActivityTabHeader(modifier: Modifier = Modifier) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text       = "Showing last month activity",
            fontSize   = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.FilterList,
                contentDescription = "Filter",
                tint               = TextPrimary,
                modifier           = Modifier.size(18.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// ACTIVITY ROW
// White card: title + timestamp + type icon on right
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ActivityRow(
    item     : ActivityItem,
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
            // Title + timestamp
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text       = item.title,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = item.timestamp,
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }

            // Type icon
            ActivityTypeIcon(type = item.type)
        }
    }
}

@Composable
private fun ActivityTypeIcon(type: ActivityType) {
    when (type) {
        ActivityType.POINTS_EARNED -> {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(SuccessLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.ArrowUpward,
                    contentDescription = "Points earned",
                    tint               = SuccessGreen,
                    modifier           = Modifier.size(18.dp)
                )
            }
        }
        ActivityType.CHALLENGE_COMPLETED -> {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFFFF3DE)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🏆", fontSize = 18.sp)
            }
        }
        ActivityType.STREAK_BROKEN -> {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ErrorLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.ArrowDownward,
                    contentDescription = "Streak broken",
                    tint               = ErrorRed,
                    modifier           = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// FRIENDS TAB — Header row
// "261 Friends" + add button + edit button
// ─────────────────────────────────────────────────────────────────

@Composable
private fun FriendsTabHeader(
    count    : Int,
    modifier : Modifier = Modifier,
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            text       = "$count Friends",
            fontSize   = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            // Add friend button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Add,
                    contentDescription = "Add friend",
                    tint               = TextPrimary,
                    modifier           = Modifier.size(18.dp)
                )
            }
            // Edit button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Edit,
                    contentDescription = "Edit friends",
                    tint               = TextPrimary,
                    modifier           = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// FRIEND ROW
// White card: avatar + name + points + delete button
// ─────────────────────────────────────────────────────────────────

@Composable
private fun FriendRow(
    friend   : FriendItem,
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
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Avatar circle
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFDDE8FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = friend.emoji, fontSize = 22.sp)
                }
                // Name + points
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text(
                        text       = friend.name,
                        fontSize   = 15.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily,
                        color      = TextPrimary
                    )
                    Text(
                        text       = "${friend.points} Points",
                        fontSize   = 12.sp,
                        fontFamily = NunitoFontFamily,
                        color      = TextSecondary
                    )
                }
            }
            // Delete button
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, BorderColor, RoundedCornerShape(10.dp))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Delete,
                    contentDescription = "Remove friend",
                    tint               = TextSecondary,
                    modifier           = Modifier.size(18.dp)
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// ACHIEVEMENT ROW
// White card: colored emoji circle + title + time ago
// ─────────────────────────────────────────────────────────────────

@Composable
private fun AchievementRow(
    achievement : AchievementItem,
    modifier    : Modifier = Modifier,
) {
    Surface(
        shape           = RoundedCornerShape(16.dp),
        color           = Color.White,
        shadowElevation = 0.dp,
        modifier        = modifier.fillMaxWidth()
    ) {
        Row(
            modifier          = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Colored emoji circle
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(achievement.bgColor),
                contentAlignment = Alignment.Center
            ) {
                Text(text = achievement.emoji, fontSize = 22.sp)
            }
            // Title + time ago
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text       = achievement.title,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = achievement.timeAgo,
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
fun ProfileScreenPreview() {
    ProfileScreen()
}