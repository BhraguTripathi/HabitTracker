package com.example.habittracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.screens.auth.HabitItem
import com.example.habittracker.ui.theme.BorderColor
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.TextPrimary

@Composable
fun HabitPickCard(
    habit : HabitItem,
    isSelected : Boolean,
    onClick : () -> Unit
){
    Surface(
        onClick         = onClick,
        shape           = RoundedCornerShape(20.dp),
        color           = Color.White,
        border          = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) Primary else BorderColor
        ),
        tonalElevation  = 0.dp,
        modifier        = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text     = habit.emoji,
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text       = habit.label,
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = NunitoFontFamily,
                color      = if (isSelected) Primary else TextPrimary
            )
        }
    }

}