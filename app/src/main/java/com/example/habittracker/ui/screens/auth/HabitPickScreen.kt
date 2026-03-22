package com.example.habittracker.ui.screens.auth

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.components.HabitPickCard
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.TextPrimary
import com.example.habittracker.ui.theme.TextSecondary


data class HabitItem(
    val id : Int,
    val emoji : String,
    val label : String
)

private val habitList = listOf(
    HabitItem(1, "💧", "Drink water"),
    HabitItem(2, "🏃‍♀️", "Run"),
    HabitItem(3, "📖", "Read books"),
    HabitItem(4, "🧘‍♀️", "Meditate"),
    HabitItem(5, "👨‍💻", "Study"),
    HabitItem(6, "📕", "Journal"),
    HabitItem(7, "🌿", "Plant care"),
    HabitItem(8, "😴", "Sleep early")
)

@Composable
fun HabitPicker(
    onBackClick : () -> Unit = {},
    onNextClick : () -> Unit = {}
){
    val selectedHabit = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint               = TextPrimary,
                    modifier           = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text       = "Create Account",
                fontSize   = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color      = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Section title + subtitle ──────────────────────────────
        Text(
            text       = "Choose your first habits",
            fontSize   = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text       = "You may add more habits later",
            fontSize   = 13.sp,
            fontFamily = NunitoFontFamily,
            color      = TextSecondary
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding      = PaddingValues(bottom = 16.dp),
            verticalArrangement   = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(habitList) { habit ->
                HabitPickCard(
                    habit = habit,
                    isSelected = habit.id in selectedHabit,
                    onClick = {
                        if (habit.id in selectedHabit){
                            selectedHabit.remove(habit.id)
                        } else {
                            selectedHabit.add(habit.id)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Next Button ───────────────────────────────────────────
        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Next",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun HabitPickerScreenPreview(){
    HabitPicker()
}