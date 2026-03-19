package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.habittracker.ui.screens.auth.LoginScreen
import com.example.habittracker.ui.screens.onboarding.OnboardingScreen
import com.example.habittracker.ui.screens.splash.SplashScreen
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Allow drawing behind system bars (status bar + nav bar)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HabitTrackerTheme {
//                NavGraph()
//                SplashScreen()
                //OnboardingScreen()
                LoginScreen()
            }
        }
    }
}
