package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.habittracker.navigation.NavGraph
import com.example.habittracker.ui.screens.challenge.ChallengeDetailScreen
import com.example.habittracker.ui.screens.habit.CreateHabitScreen
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HabitTrackerTheme {
                NavGraph()
            }
        }
    }
}