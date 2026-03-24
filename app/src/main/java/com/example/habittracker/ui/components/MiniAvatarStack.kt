package com.example.habittracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary

private val avatarColors = listOf(
    Color(0xFFFFB5A0),
    Color(0xFFA0C8FF),
    Color(0xFFB8D4E8),
)

@Composable
fun MiniAvatarStack(
    count      : Int = 2,
    extraCount : Int = 0,
) {
    Row(horizontalArrangement = Arrangement.spacedBy((-8).dp)) {
        repeat(minOf(count, 3)) { index ->
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(avatarColors[index % avatarColors.size])
                    .border(1.5.dp, Color.White, CircleShape)
            )
        }
        if (extraCount > 0) {
            Box(
                modifier         = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Primary)
                    .border(1.5.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "+$extraCount",
                    fontSize   = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color      = Color.White,
                    fontFamily = NunitoFontFamily
                )
            }
        }
    }
}