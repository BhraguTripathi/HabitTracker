package com.example.habittracker.ui.screens.auth

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.habittracker.ui.components.GenderCard
import com.example.habittracker.ui.theme.BackgroundLight
import com.example.habittracker.ui.theme.NunitoFontFamily
import com.example.habittracker.ui.theme.Primary
import com.example.habittracker.ui.theme.TextPrimary

enum class Gender { MALE, FEMALE }

@Composable
fun GenderScreen(
    onBackClick : () -> Unit = {},
    onNextClick : () -> Unit = {}
){
    var selectedGender by remember { mutableStateOf<Gender?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to create account.",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                text = "Create Account",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Section title ─────────────────────────────────────────
        Text(
            text = "Choose you gender",
            fontSize   = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = NunitoFontFamily,
            color      = TextPrimary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,

        ){
            GenderCard(
                emoji      = "🙋‍♂️",
                label      = "Male",
                isSelected = selectedGender == Gender.MALE,
                modifier   = Modifier.weight(1f),
                onClick    = { selectedGender = Gender.MALE }
            )

            Spacer(modifier = Modifier.width(16.dp))

            GenderCard(
                emoji      = "🙋‍♀️",
                label      = "Female",
                isSelected = selectedGender == Gender.FEMALE,
                modifier   = Modifier.weight(1f),
                onClick    = { selectedGender = Gender.FEMALE }
            )

        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = selectedGender != null,
            shape   = RoundedCornerShape(32.dp),
            colors  = ButtonDefaults.buttonColors(
                containerColor = Primary,
                contentColor  = Color.White
            )
        ) {
            Text(
                text       = "Next",
                fontSize   = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = NunitoFontFamily,
                color      = Color.White
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GenderScreenPreview(){
    GenderScreen()
}