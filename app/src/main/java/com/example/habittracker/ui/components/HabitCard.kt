package com.example.habittracker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.theme.BorderColor
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.SuccessGreen
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary

data class HabitCardData(
    val id          : Int,
    val emoji       : String,
    val name        : String,
    val progress    : String,
    val arcColor    : Color,
    val arcSweep    : Float,
    val isDone      : Boolean,
    val avatarCount : Int,
    val extraCount  : Int,
)

@Composable
fun HabitCard(
    habit    : HabitCardData,
    modifier : Modifier = Modifier
){
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 0.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val track = Color(0xFFF0F0F5)
                    drawArc(
                        color      = track,
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
                    drawArc(
                        color      = habit.arcColor,
                        startAngle = -90f,
                        sweepAngle = habit.arcSweep,
                        useCenter  = false,
                        topLeft    = Offset(3.dp.toPx(), 3.dp.toPx()),
                        size       = Size(
                            size.width  - 6.dp.toPx(),
                            size.height - 6.dp.toPx()
                        ),
                        style = Stroke(3.5.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(text = habit.emoji, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Name + progress
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text       = habit.name,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = NunitoFontFamily,
                    color      = TextPrimary
                )
                Text(
                    text       = habit.progress,
                    fontSize   = 12.sp,
                    fontFamily = NunitoFontFamily,
                    color      = TextSecondary
                )
            }

            // Avatars + action
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (habit.avatarCount > 0) {
                    MiniAvatarStack(
                        count      = habit.avatarCount,
                        extraCount = habit.extraCount
                    )
                }
                ActionButton(isDone = habit.isDone)
            }
        }
    }
}

@Composable
private fun ActionButton(isDone: Boolean) {
    Box(
        modifier         = Modifier
            .size(34.dp)
            .background(Color.White, RoundedCornerShape(10.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
    ) {
        if (isDone) {
            Icon(
                imageVector        = Icons.Default.Check,
                contentDescription = "Done",
                tint               = SuccessGreen,
                modifier           = Modifier.size(18.dp)
            )
        } else {
            Text(
                text       = "+",
                fontSize   = 20.sp,
                color      = TextSecondary,
                fontWeight = FontWeight.Light
            )
        }
    }
}