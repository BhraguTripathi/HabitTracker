package com.example.habittracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.BorderColor
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.PrimaryContainer
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

data class ChallengeCardData(
    val title         : String,
    val timeLeft      : String,
    val friendsJoined : Int,
    val progress      : Float,
)

@Composable
fun ChallengeCard(
    challenge : ChallengeCardData,
    modifier  : Modifier = Modifier
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
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.Top
        ) {
            // Left side
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier              = Modifier.weight(1f)
            ) {
                Box(
                    modifier         = Modifier
                        .size(36.dp)
                        .background(PrimaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "⏱", fontSize = 14.sp)
                }
                Column {
                    Text(
                        text       = challenge.title,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = NunitoFontFamily,
                        color      = TextPrimary
                    )
                    Text(
                        text       = challenge.timeLeft,
                        fontSize   = 12.sp,
                        fontFamily = NunitoFontFamily,
                        color      = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    // Progress bar
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(BorderColor)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(challenge.progress)
                                .fillMaxHeight()
                                .background(Primary, RoundedCornerShape(2.dp))
                        )
                    }
                }
            }

            // Right side
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                MiniAvatarStack(count = challenge.friendsJoined)
                Text(
                    text       = "${challenge.friendsJoined} friends joined",
                    fontSize   = 11.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }
        }
    }
}