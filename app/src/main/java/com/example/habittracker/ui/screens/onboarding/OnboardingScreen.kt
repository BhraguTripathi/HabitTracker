package com.example.habittracker.ui.screens.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.R

// ─────────────────────────────────────────────────────────────────
// BRAND COLORS (inline for this file)
// ─────────────────────────────────────────────────────────────────
private val BrandBlue    = Color(0xFF3D3DF5)
private val GradStart    = Color(0xFF6B73FF)
private val GradEnd      = Color(0xFF000DFF)
private val TextDark     = Color(0xFF0F0F1A)
private val TextGray     = Color(0xFF8B8FA8)
private val SuccessGreen = Color(0xFF28C76F)
private val CardBg       = Color.White
private val ArcTrack     = Color(0xFFF0F0F5)

// ─────────────────────────────────────────────────────────────────
// DATA MODEL
// ─────────────────────────────────────────────────────────────────
data class OnboardingPage(
    val title    : String,
    val subtitle : String,
)

private val pages = listOf(
    OnboardingPage(
        title    = "Create\nGood Habits",
        subtitle = "Change your life by slowly adding new healthy habits and sticking to them.",
    ),
    OnboardingPage(
        title    = "Track\nYour Progress",
        subtitle = "Everyday you become one step closer to your goal. Don't give up!",
    ),
    OnboardingPage(
        title    = "Stay Together\nand Strong",
        subtitle = "Find friends to discuss common topics. Complete challenges together.",
    ),
)

// ─────────────────────────────────────────────────────────────────
// ROOT SCREEN
// ─────────────────────────────────────────────────────────────────
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onContinueWithEmail : () -> Unit = {},
    onAppleSignIn       : () -> Unit = {},
    onGoogleSignIn      : () -> Unit = {},
    onFacebookSignIn    : () -> Unit = {},
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(GradStart, GradEnd),
                    start  = Offset(0f, 0f),
                    end    = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {

        // ── Full screen concentric rings ─────────────────────────
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx     = center.x
            val cy     = size.height * 0.38f
            val color  = Color.White.copy(alpha = 0.05f)
            listOf(0.35f, 0.52f, 0.70f).forEach { fraction ->
                drawCircle(
                    color  = color,
                    radius = size.minDimension * fraction,
                    center = Offset(cx, cy),
                    style  = Stroke(width = 1.5.dp.toPx())
                )
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {

            // ── Pager — takes all space above bottom section ──────
            HorizontalPager(
                state    = pagerState,
                modifier = Modifier.weight(1f)
            ) { index ->
                when (index) {
                    0 -> Page1Content()
                    1 -> Page2Content()
                    else -> Page3Content()
                }
            }

            // ── Bottom — same on every page ───────────────────────
            BottomSection(
                currentPage         = pagerState.currentPage,
                totalPages          = pages.size,
                onContinueWithEmail = onContinueWithEmail,
                onAppleSignIn       = onAppleSignIn,
                onGoogleSignIn      = onGoogleSignIn,
                onFacebookSignIn    = onFacebookSignIn,
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PAGE 1 — "Create Good Habits"
//
// Exact positions from design:
//   [A] TOP-RIGHT:     [CheckPill] → [Woman laughing] (circle, half off right edge)
//   [B] MIDDLE-LEFT:   [Asian woman] (circle) → [CheckPill]
//   [C] CENTER-LOWER:  [CheckPill] → [Man with beanie] (larger circle)
//   Decor: hexagon + 2 dots, top-left area
// ─────────────────────────────────────────────────────────────────
@Composable
fun Page1Content() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {

        // ── Decor: hollow hexagon top-left ────────────────────────
        Canvas(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 52.dp)
                .size(22.dp)
        ) {
            val path = Path()
            val cx = size.width / 2f
            val cy = size.height / 2f
            val r  = size.minDimension / 2f
            for (i in 0 until 6) {
                val a = Math.toRadians((60.0 * i) - 30.0)
                val x = (cx + r * Math.cos(a)).toFloat()
                val y = (cy + r * Math.sin(a)).toFloat()
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()
            drawPath(path, Color.White.copy(alpha = 0.35f), style = Stroke(1.5.dp.toPx()))
        }

        // ── Decor: two small dots ─────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 60.dp, start = 20.dp)
                .size(8.dp)
                .background(Color.White.copy(alpha = 0.4f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 88.dp, start = 48.dp)
                .size(6.dp)
                .background(Color.White.copy(alpha = 0.25f), CircleShape)
        )

        // ── [A] TOP-RIGHT: pill + laughing woman ──────────────────
        // Pill is to the left, avatar circle is on the right
        // Avatar extends slightly off-screen on the right
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 45.dp),
            verticalAlignment     = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy((-14).dp)
        ) {
            CheckPill()
            PersonCircle(
                imageRes = R.drawable.onboarding_person1,
                size     = 80
            )
        }

        // ── [B] MIDDLE-LEFT: asian woman + pill ───────────────────
        // Avatar on left, pill extends right
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(bottom = 150.dp),
            verticalAlignment     = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy((-18).dp)
        ) {
            PersonCircle(
                imageRes = R.drawable.onboarding_person2,
                size     = 80
            )
            CheckPill()
        }

        // ── [C] CENTER-LOWER: pill + man with beanie ─────────────
        // Larger avatar, positioned in center-lower area
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 100.dp, start = 8.dp),
            verticalAlignment     = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy((-18).dp)
        ) {
            CheckPill()
            PersonCircle(
                imageRes = R.drawable.onboarding_person3,
                size     = 100
            )
        }

        // ── Title + Subtitle at bottom ────────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 24.dp)
        ) {
            PageTitleText(pages[0])
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PAGE 2 — "Track Your Progress"
//
// Exact layout from design (content starts from very TOP):
//   "Challenges" label
//   [Challenge Card] — timer icon | title + time + progress bar | avatars + "2 friends joined"
//   "Habits" label
//   [Habit Card 1] — arc icon (💧, ~75% fill) | name + progress | 2 avatars + +3 badge | + btn
//   [Habit Card 2] — arc icon (🚶, ~15% fill) | name + progress | 2 avatars | + btn
//   [Habit Card 3] — arc icon (🧘, 100% fill) | name + progress | 1 avatar | ✓ btn (green)
//   Title + Subtitle below
// ─────────────────────────────────────────────────────────────────
@Composable
fun Page2Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        // ── "Challenges" label ────────────────────────────────────
        Text(
            text       = "Challenges",
            color      = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize   = 13.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Challenge Card ────────────────────────────────────────
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = CardBg,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier              = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top
            ) {
                // Left: icon + name + time + progress bar
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier              = Modifier.weight(1f)
                ) {
                    // Timer/clock icon
                    Box(
                        modifier         = Modifier
                            .size(34.dp)
                            .background(Color(0xFFEEEEFF), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "⏱", fontSize = 14.sp)
                    }
                    Column {
                        Text(
                            text       = "Best Runners! 🏃",
                            fontWeight = FontWeight.Bold,
                            fontSize   = 13.sp,
                            color      = TextDark
                        )
                        Text(
                            text      = "5 days 13 hours left",
                            fontSize  = 11.sp,
                            color     = TextGray
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        // Progress bar
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(4.dp)
                                .background(Color(0xFFF0F0F5), RoundedCornerShape(2.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.35f)
                                    .fillMaxHeight()
                                    .background(BrandBlue, RoundedCornerShape(2.dp))
                            )
                        }
                    }
                }

                // Right: avatars + "2 friends joined"
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SmallAvatarStack(
                        images     = listOf(
                            R.drawable.onboarding_person1,
                            R.drawable.onboarding_person2
                        ),
                        extraCount = 0
                    )
                    Text(
                        text     = "2 friends joined",
                        fontSize = 10.sp,
                        color    = TextGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── "Habits" label ────────────────────────────────────────
        Text(
            text       = "Habits",
            color      = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize   = 13.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Habit Card 1: Drink the water ─────────────────────────
        // Arc: ~75% filled blue, water drop emoji
        // Right: 2 avatars + "+3" blue badge + "+" button
        HabitCard2(
            emoji      = "💧",
            arcSweep   = 270f,
            arcColor   = BrandBlue,
            name       = "Drink the water",
            progress   = "500/2000 ML",
            avatarRes  = listOf(
                R.drawable.onboarding_person3,
                R.drawable.onboarding_person4
            ),
            extraCount = 3,
            isDone     = false
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Habit Card 2: Walk ────────────────────────────────────
        // Arc: ~15% filled (very little progress), walking emoji
        // Right: 2 avatars + "+" button
        HabitCard2(
            emoji      = "🚶",
            arcSweep   = 50f,
            arcColor   = Color(0xFF7B8FF7),
            name       = "Walk",
            progress   = "0/10000 STEPS",
            avatarRes  = listOf(
                R.drawable.onboarding_person1,
                R.drawable.onboarding_person2
            ),
            extraCount = 0,
            isDone     = false
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ── Habit Card 3: Meditate ────────────────────────────────
        // Arc: full 360° (completed), meditation emoji
        // Right: 1 avatar + green check button
        HabitCard2(
            emoji      = "🧘",
            arcSweep   = 360f,
            arcColor   = BrandBlue,
            name       = "Meditate",
            progress   = "30/30 MIN",
            avatarRes  = listOf(R.drawable.onboarding_person3),
            extraCount = 0,
            isDone     = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── Title + Subtitle ──────────────────────────────────────
        PageTitleText(pages[1])
    }
}

// ─────────────────────────────────────────────────────────────────
// PAGE 3 — "Stay Together and Strong"
//
// Exact layout from design:
//   4 TALL PORTRAIT PILL SHAPES with real photos (NOT circles):
//     - TOP-CENTER:   black man   (top, shifted slightly right)
//     - LEFT-CENTER:  asian man   (vertically centered, left side)
//     - RIGHT-CENTER: asian woman (vertically centered, right side)
//     - BOTTOM-CENTER: blonde woman (bottom, centered)
//   CENTER: white rounded card "BEST STREAK DAY / 22" overlapping pills
//   Background: faint wavy S-curve line + concentric rings
//   Decor: hexagon, 2 dots (top-left area)
// ─────────────────────────────────────────────────────────────────
@Composable
fun Page3Content() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {

        // ── Decor: hexagon ────────────────────────────────────────
        Canvas(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 14.dp, start = 60.dp)
                .size(18.dp)
        ) {
            val path = Path()
            val cx = size.width / 2f
            val cy = size.height / 2f
            val r  = size.minDimension / 2f
            for (i in 0 until 6) {
                val a = Math.toRadians((60.0 * i) - 30.0)
                val x = (cx + r * Math.cos(a)).toFloat()
                val y = (cy + r * Math.sin(a)).toFloat()
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            path.close()
            drawPath(path, Color.White.copy(alpha = 0.35f), style = Stroke(1.5.dp.toPx()))
        }

        // Dots
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 68.dp, start = 8.dp)
                .size(8.dp)
                .background(Color.White.copy(alpha = 0.4f), CircleShape)
        )
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 92.dp, start = 32.dp)
                .size(5.dp)
                .background(Color.White.copy(alpha = 0.25f), CircleShape)
        )

        // ── Illustration area — Box for absolute positioning ──────
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.58f) // top 58% of screen for illustration
                .align(Alignment.TopStart)
        ) {

            // ── TOP-CENTER: black man portrait pill ───────────────
            PersonPill(
                imageRes = R.drawable.onboarding_person4,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(x = 24.dp, y = 8.dp)
            )

            // ── LEFT-CENTER: asian man portrait pill ──────────────
            PersonPill(
                imageRes = R.drawable.onboarding_person5,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(y = (-8).dp)
            )

            // ── RIGHT-CENTER: asian woman portrait pill ───────────
            PersonPill(
                imageRes = R.drawable.onboarding_person6,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(y = 16.dp)
            )

            // ── BOTTOM-CENTER: blonde woman portrait pill ─────────
            PersonPill(
                imageRes = R.drawable.onboarding_person7,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = (-16).dp, y = (-8).dp)
            )

            // ── CENTER: "BEST STREAK DAY / 22" card ──────────────
            // Overlaps all 4 portrait pills
            Surface(
                shape          = RoundedCornerShape(16.dp),
                color          = Color.White,
                shadowElevation = 6.dp,
                modifier       = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-20).dp, y = (-10).dp)
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical   = 12.dp
                    )
                ) {
                    Text(
                        text          = "BEST STREAK DAY",
                        fontSize      = 10.sp,
                        fontWeight    = FontWeight.Bold,
                        color         = TextGray,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text       = " ",
                        fontSize   = 25.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color      = TextDark,
                        lineHeight = 30.sp
                    )
                }
            }
        }

        // ── Title + Subtitle at bottom ────────────────────────────
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 24.dp)
        ) {
            PageTitleText(pages[2])
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// HABIT CARD — Page 2
// White card with:
//   LEFT:   Canvas arc progress ring + emoji center
//   CENTER: habit name + progress text
//   RIGHT:  overlapping avatars + (+ button OR green check)
// ─────────────────────────────────────────────────────────────────
@Composable
fun HabitCard2(
    emoji      : String,
    arcSweep   : Float,
    arcColor   : Color,
    name       : String,
    progress   : String,
    avatarRes  : List<Int>,
    extraCount : Int,
    isDone     : Boolean,
) {
    Surface(
        shape    = RoundedCornerShape(14.dp),
        color    = CardBg,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            // ── Arc progress icon ─────────────────────────────────
            Box(
                modifier         = Modifier.size(44.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Gray background track
                    drawArc(
                        color      = ArcTrack,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter  = false,
                        topLeft    = Offset(3.dp.toPx(), 3.dp.toPx()),
                        size       = Size(
                            size.width  - 6.dp.toPx(),
                            size.height - 6.dp.toPx()
                        ),
                        style = Stroke(3.dp.toPx(), cap = StrokeCap.Round)
                    )
                    // Colored progress arc
                    drawArc(
                        color      = arcColor,
                        startAngle = -90f,
                        sweepAngle = arcSweep,
                        useCenter  = false,
                        topLeft    = Offset(3.dp.toPx(), 3.dp.toPx()),
                        size       = Size(
                            size.width  - 6.dp.toPx(),
                            size.height - 6.dp.toPx()
                        ),
                        style = Stroke(3.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(text = emoji, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.width(10.dp))

            // ── Name + progress ───────────────────────────────────
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = name,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 13.sp,
                    color      = TextDark
                )
                Text(
                    text     = progress,
                    fontSize = 11.sp,
                    color    = TextGray
                )
            }

            // ── Avatars + action button ───────────────────────────
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                SmallAvatarStack(
                    images     = avatarRes,
                    extraCount = extraCount
                )

                if (isDone) {
                    // Green check circle
                    Box(
                        modifier         = Modifier
                            .size(30.dp)
                            .background(Color(0xFFE5F9EE), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Check,
                            contentDescription = null,
                            tint               = SuccessGreen,
                            modifier           = Modifier.size(16.dp)
                        )
                    }
                } else {
                    // "+" button circle
                    Box(
                        modifier         = Modifier
                            .size(30.dp)
                            .border(1.5.dp, Color(0xFFDDE2F0), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text       = "+",
                            fontSize   = 18.sp,
                            color      = TextGray,
                            textAlign  = TextAlign.Center,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PERSON CIRCLE — circular avatar with white border
// Used in page 1
// ─────────────────────────────────────────────────────────────────
@Composable
fun PersonCircle(
    @DrawableRes imageRes : Int,
    size                  : Int,
    modifier              : Modifier = Modifier
) {
    Image(
        painter            = painterResource(id = imageRes),
        contentDescription = null,
        contentScale       = ContentScale.Crop,
        modifier           = modifier
            .size(size.dp)
            .clip(CircleShape)
            .border(2.5.dp, Color.White.copy(alpha = 0.7f), CircleShape)
    )
}

// ─────────────────────────────────────────────────────────────────
// PERSON PILL — tall rounded rectangle portrait shape
// Used in page 3 ONLY — this is the KEY difference from circles!
// Width: 66dp, Height: 100dp, corner radius: 36dp
// ─────────────────────────────────────────────────────────────────
@Composable
fun PersonPill(
    @DrawableRes imageRes : Int,
    modifier              : Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = Color.White.copy(alpha = 0.5f),
                shape = CircleShape
            )
    ) {
        Image(
            painter            = painterResource(id = imageRes),
            contentDescription = null,
            contentScale       = ContentScale.Crop,
            modifier           = Modifier.fillMaxSize()
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// SMALL AVATAR STACK — overlapping mini circles
// Used in page 2 habit cards + challenge card
// ─────────────────────────────────────────────────────────────────
@Composable
fun SmallAvatarStack(
    images     : List<Int>,
    extraCount : Int = 0
) {
    // We use a fixed-width Box and offset each avatar manually
    val avatarSize   = 24
    val overlapOffset = 14 // each avatar shifts 14dp right

    val totalWidth = (avatarSize + (images.size - 1) * overlapOffset +
            if (extraCount > 0) overlapOffset else 0).dp

    Box(modifier = Modifier
        .wrapContentSize()
        .height(avatarSize.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy((-10).dp),
            verticalAlignment     = Alignment.CenterVertically
        ) {
            images.forEach { res ->
                Image(
                    painter            = painterResource(id = res),
                    contentDescription = null,
                    contentScale       = ContentScale.Crop,
                    modifier           = Modifier
                        .size(avatarSize.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, Color.White, CircleShape)
                )
            }

            // "+3" badge if extraCount > 0
            if (extraCount > 0) {
                Box(
                    modifier         = Modifier
                        .size(avatarSize.dp)
                        .background(BrandBlue, CircleShape)
                        .border(1.5.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = "+$extraCount",
                        color      = Color.White,
                        fontSize   = 7.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// CHECK PILL — white rounded pill with blue check + gray lines
// Used in page 1 next to each person photo
// ─────────────────────────────────────────────────────────────────
@Composable
fun CheckPill() {
    Surface(
        shape = RoundedCornerShape(100.dp),
        color = Color.White
    ) {
        Row(
            modifier              = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Blue filled circle with white check
            Box(
                modifier         = Modifier
                    .size(20.dp)
                    .background(BrandBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Check,
                    contentDescription = null,
                    tint               = Color.White,
                    modifier           = Modifier.size(12.dp)
                )
            }
            // Two placeholder lines
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Box(
                    modifier = Modifier
                        .width(56.dp)
                        .height(5.dp)
                        .background(color = BrandBlue, RoundedCornerShape(3.dp))
                )
                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(5.dp)
                        .background(color = BrandBlue, RoundedCornerShape(3.dp))
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PAGE TITLE TEXT
// Large bold white title + subtitle
// Reused across all 3 pages
// ─────────────────────────────────────────────────────────────────
@Composable
fun PageTitleText(page: OnboardingPage) {
    Text(
        text       = page.title,
        color      = Color.White,
        fontWeight = FontWeight.ExtraBold,
        fontSize   = 36.sp,
        lineHeight = 44.sp,
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text       = page.subtitle,
        color      = Color.White.copy(alpha = 0.78f),
        fontWeight = FontWeight.Normal,
        fontSize   = 15.sp,
        lineHeight = 23.sp,
    )
}

// ─────────────────────────────────────────────────────────────────
// BOTTOM SECTION — fixed, same on all 3 pages
// Dots + Continue button + 3 social buttons + terms text
// ─────────────────────────────────────────────────────────────────
@Composable
fun BottomSection(
    currentPage         : Int,
    totalPages          : Int,
    onContinueWithEmail : () -> Unit,
    onAppleSignIn       : () -> Unit,
    onGoogleSignIn      : () -> Unit,
    onFacebookSignIn    : () -> Unit,
) {
    Column(
        modifier            = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.Start,
    ) {

        // ── Page dot indicators ───────────────────────────────────
        // Active dot = wider pill (20dp wide), inactive = small circle (8dp)
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment     = Alignment.CenterVertically
        ) {
            repeat(totalPages) { index ->
                val isActive = index == currentPage
                Box(
                    modifier = Modifier
                        .size(width = if (isActive) 20.dp else 8.dp, height = 8.dp)
                        .background(
                            color = if (isActive) Color.White else Color.White.copy(alpha = 0.35f),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ── "Continue with E-mail" button ─────────────────────────
        // White button, dark text + arrow icon
        Button(
            onClick  = onContinueWithEmail,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape  = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor   = TextDark
            )
        ) {
            Icon(
                imageVector        = Icons.Filled.Email,
                contentDescription = null,
                tint               = TextDark,
                modifier           = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text       = "Continue with E-mail",
                fontWeight = FontWeight.Bold,
                fontSize   = 15.sp,
                color      = TextDark
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Social buttons row ────────────────────────────────────
        // 3 equal-width WHITE solid buttons: Apple | Google | Facebook
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Apple
            SocialBtn(
                modifier = Modifier.weight(1f),
                label    = "Apple",
                icon     = {
                    Image(
                        painter = painterResource(R.drawable.apple),
                        contentDescription = "apple_icon",
                        Modifier.size(20.dp)
                    )
                },
                onClick  = onAppleSignIn
            )
            // Google
            SocialBtn(
                modifier = Modifier.weight(1f),
                label    = "Google",
                icon     = {
                    Image(
                        painter = painterResource(R.drawable.google),
                        contentDescription = "apple_icon",
                        Modifier.size(20.dp)
                    )
                },
                onClick  = onGoogleSignIn
            )
            // Facebook
            SocialBtn(
                modifier = Modifier.weight(1f),
                label    = "Facebook",
                icon     = {
                    Image(
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = "apple_icon",
                        Modifier.size(20.dp)
                    )
                },
                onClick  = onFacebookSignIn
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // ── Terms text ────────────────────────────────────────────
        Text(
            text      = "By continuing you agree Terms of Services & Privacy Policy",
            color     = Color.White.copy(alpha = 0.55f),
            fontSize  = 11.sp,
            textAlign = TextAlign.Center,
            lineHeight = 16.sp,
            modifier  = Modifier.fillMaxWidth()
        )
    }
}

// ─────────────────────────────────────────────────────────────────
// SOCIAL BUTTON
// Solid white rounded button with icon + label
// ─────────────────────────────────────────────────────────────────
@Composable
fun SocialBtn(
    modifier : Modifier = Modifier,
    label    : String,
    icon     : @Composable () -> Unit,
    onClick  : () -> Unit,
) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(46.dp),
        shape    = RoundedCornerShape(32.dp),
        color    = Color.White,
    ) {
        Row(
            modifier              = Modifier.fillMaxSize(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            icon()
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text       = label,
                fontSize   = 13.sp,
                fontWeight = FontWeight.Bold,
                color      = TextDark
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────
// PREVIEW
// ─────────────────────────────────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnboardingPreview() {
    OnboardingScreen()
}