package com.example.habittracker.ui.screens.challenge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.components.MiniAvatarStack
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

// ─────────────────────────────────────────────────────────────────
// DATA MODELS
// ─────────────────────────────────────────────────────────────────

data class ChallengeTask(
    val id          : String,
    val emoji       : String,
    val name        : String,
    val progress    : String,
    val arcColor    : Color,
    val arcSweep    : Float,
    val avatarCount : Int,
    val extraCount  : Int = 0,
)

data class ChallengeDetail(
    val id               : String,
    val emoji            : String,
    val title            : String,
    val dateRange        : String,
    val participantCount : Int,
    val description      : String,
    val tasks            : List<ChallengeTask>,
)

// ─────────────────────────────────────────────────────────────────
// SAMPLE DATA
// ─────────────────────────────────────────────────────────────────

val sampleChallengeDetail = ChallengeDetail(
    id               = "1",
    emoji            = "🏃",
    title            = "Best Runners!",
    dateRange        = "May 28 to 4 June",
    participantCount = 14,
    description      = "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatu. " +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatu.",
    tasks            = listOf(
        ChallengeTask("t1", "💧", "Drink the water", "500/2000 ML",  Primary,             270f, 2, 3),
        ChallengeTask("t2", "🚶", "Walk",            "0/10000 STEPS", Color(0xFF7B8FF7),  50f,  2, 0),
        ChallengeTask("t3", "🌿", "Water Plants",    "0/1 TIMES",    Color(0xFF28C76F),   10f,  0, 0),
        ChallengeTask("t4", "🧘", "Meditate",        "30/30 MIN",    Primary,             360f, 1, 0),
    )
)

// ─────────────────────────────────────────────────────────────────
// CHALLENGE DETAIL SCREEN
// ─────────────────────────────────────────────────────────────────

@Composable
fun ChallengeDetailScreen(
    challenge   : ChallengeDetail = sampleChallengeDetail,
    onBack      : () -> Unit = {},
    onAdd       : () -> Unit = {},
    onJoin      : () -> Unit = {},
) {
    var hasJoined by remember { mutableStateOf(false) }

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
            contentPadding      = PaddingValues(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // ── Top Bar ───────────────────────────────────────────
            item {
                ChallengeDetailTopBar(
                    onBack   = onBack,
                    onAdd    = {
                        hasJoined = !hasJoined
                        onAdd()
                    },
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 16.dp
                    )
                )
            }

            // ── Hero Section ──────────────────────────────────────
            item {
                ChallengeHeroSection(
                    challenge = challenge,
                    modifier  = Modifier.padding(horizontal = 24.dp)
                )
            }

            // ── Join Button ───────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(28.dp))
                Button(
                    onClick  = { hasJoined = !hasJoined },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(56.dp),
                    shape  = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (hasJoined)
                            Primary.copy(alpha = 0.5f)
                        else
                            Primary,
                        contentColor   = Color.White
                    )
                ) {
                    Text(
                        text       = if (hasJoined) "✓  Joined!" else "Join the Challenge",
                        fontSize   = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily
                    )
                }
            }

            // ── Tasks Header ──────────────────────────────────────
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text       = "Tasks",
                        color      = TextPrimary,
                        fontSize   = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // ── Task Cards ────────────────────────────────────────
            items(challenge.tasks) { task ->
                ChallengeTaskCard(
                    task     = task,
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 5.dp
                    )
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// TOP BAR
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ChallengeDetailTopBar(
    onBack   : () -> Unit,
    onAdd    : () -> Unit,
    modifier : Modifier = Modifier,
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
                .clickable { onBack() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint               = TextPrimary,
                modifier           = Modifier.size(20.dp)
            )
        }

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color.White)
                .clickable { onAdd() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.Add,
                contentDescription = "Join",
                tint               = TextPrimary,
                modifier           = Modifier.size(20.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HERO SECTION
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ChallengeHeroSection(
    challenge : ChallengeDetail,
    modifier  : Modifier = Modifier,
) {
    Column(
        modifier            = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Large emoji
        Text(text = challenge.emoji, fontSize = 72.sp)

        // Title
        Text(
            text       = challenge.title,
            color      = TextPrimary,
            fontSize   = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = NunitoFontFamily,
            textAlign  = TextAlign.Center
        )

        // Date range
        Text(
            text       = challenge.dateRange,
            color      = TextSecondary,
            fontSize   = 14.sp,
            fontFamily = NunitoFontFamily,
            textAlign  = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        ChallengeAvatarStack(totalCount = challenge.participantCount)

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text       = challenge.description,
            color      = TextSecondary,
            fontSize   = 14.sp,
            fontFamily = NunitoFontFamily,
            lineHeight = 22.sp,
            textAlign  = TextAlign.Center
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// CHALLENGE AVATAR STACK
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ChallengeAvatarStack(
    totalCount : Int,
    modifier   : Modifier = Modifier,
) {
    val visibleCount = minOf(totalCount, 5)
    val extraCount   = (totalCount - visibleCount).coerceAtLeast(0)

    val avatarColors = listOf(
        Color(0xFFFFB5A0),
        Color(0xFF4A90D9),
        Color(0xFFA0C8FF),
        Color(0xFFFF8FAB),
        Color(0xFFB8D4E8),
    )

    Row(
        modifier              = modifier,
        horizontalArrangement = Arrangement.spacedBy((-10).dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        repeat(visibleCount) { index ->
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(avatarColors[index % avatarColors.size])
            )
        }
        if (extraCount > 0) {
            Box(
                modifier         = Modifier
                    .size(36.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "+$extraCount",
                    fontSize   = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color      = TextPrimary,
                    fontFamily = NunitoFontFamily
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// CHALLENGE TASK CARD
// ─────────────────────────────────────────────────────────────────

@Composable
private fun ChallengeTaskCard(
    task     : ChallengeTask,
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
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier         = Modifier.size(44.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Gray track
                    drawArc(
                        color      = Color(0xFFF0F0F5),
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter  = false,
                        topLeft    = Offset(3.dp.toPx(), 3.dp.toPx()),
                        size       = Size(
                            size.width  - 6.dp.toPx(),
                            size.height - 6.dp.toPx()
                        ),
                        style = Stroke(3.5.dp.toPx(), cap = StrokeCap.Round)
                    )
                    // Colored progress arc
                    drawArc(
                        color      = task.arcColor,
                        startAngle = -90f,
                        sweepAngle = task.arcSweep,
                        useCenter  = false,
                        topLeft    = Offset(3.dp.toPx(), 3.dp.toPx()),
                        size       = Size(
                            size.width  - 6.dp.toPx(),
                            size.height - 6.dp.toPx()
                        ),
                        style = Stroke(3.5.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(text = task.emoji, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Name + progress
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = task.name,
                    color      = TextPrimary,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NunitoFontFamily
                )
                Text(
                    text       = task.progress,
                    color      = TextSecondary,
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily
                )
            }

            if (task.avatarCount > 0) {
                MiniAvatarStack(
                    count      = task.avatarCount,
                    extraCount = task.extraCount
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
fun ChallengeDetailScreenPreview() {
    ChallengeDetailScreen()
}
