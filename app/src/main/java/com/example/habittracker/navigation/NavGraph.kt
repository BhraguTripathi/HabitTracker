package com.example.habittracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.ui.screens.auth.GenderScreen
import com.example.habittracker.ui.screens.auth.HabitPicker
import com.example.habittracker.ui.screens.auth.LoginScreen
import com.example.habittracker.ui.screens.auth.SignUpScreen
import com.example.habittracker.ui.screens.explore.ExploreScreen
import com.example.habittracker.ui.screens.home.HomeScreen
import com.example.habittracker.ui.screens.onboarding.OnboardingScreen
import com.example.habittracker.ui.screens.profile.ProfileScreen
import com.example.habittracker.ui.screens.settings.SettingsScreen
import com.example.habittracker.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController  = navController,
        startDestination = Screen.Splash.route
    ) {

        // ── Splash ────────────────────────────────────────────────
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Onboarding ────────────────────────────────────────────
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onContinueWithEmail = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                },
                onGoogleSignIn   = { },
                onFacebookSignIn = { },
                onAppleSignIn    = { }
            )
        }

        // ── Login ─────────────────────────────────────────────────
        composable(Screen.Login.route) {
            LoginScreen(
                onBackClick     = { navController.popBackStack() },
                onNextClick     = { navController.navigate(Screen.Home.route) },
                onCreateAccount = { navController.navigate(Screen.CreateAccount.route) }
            )
        }

        // ── Create Account (SignUp) ────────────────────────────────
        composable(Screen.CreateAccount.route) {
            SignUpScreen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { navController.navigate(Screen.Gender.route) },
                onLogin     = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.CreateAccount.route) { inclusive = true }
                    }
                },
                onCreateAccount = { }
            )
        }

        // ── Gender ────────────────────────────────────────────────
        composable(Screen.Gender.route) {
            GenderScreen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { navController.navigate(Screen.HabitPicker.route) }
            )
        }

        // ── Habit Picker ──────────────────────────────────────────
        composable(Screen.HabitPicker.route) {
            HabitPicker(
                onBackClick = { navController.popBackStack() },
                onNextClick = {
                    navController.navigate(Screen.Home.route) {
                        // Clear entire auth back stack so user can't go back
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Home ──────────────────────────────────────────────────
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateExplore   = { navController.navigate(Screen.Explore.route) },
                onNavigateActivity  = { navController.navigate(Screen.Activity.route) },
                onNavigateProfile   = { navController.navigate(Screen.Profile.route) },
                onViewAllHabits     = { },
                onViewAllChallenges = { }
            )
        }

        // ── Explore ───────────────────────────────────────────────
        composable(Screen.Explore.route) {
            ExploreScreen(
                onNavigateHome     = { navController.navigate(Screen.Home.route) },
                onNavigateActivity = { navController.navigate(Screen.Activity.route) },
                onNavigateProfile  = { navController.navigate(Screen.Profile.route) }
            )
        }

        // ── Activity ──────────────────────────────────────────────
        composable(Screen.Activity.route) {
            HomeScreen(
                onNavigateExplore  = { navController.navigate(Screen.Explore.route) },
                onNavigateActivity = { },
                onNavigateProfile  = { navController.navigate(Screen.Profile.route) }
            )
        }

        // ── Leaderboard ───────────────────────────────────────────
        composable(Screen.Leaderboard.route) {
            HomeScreen(
                onNavigateExplore  = { navController.navigate(Screen.Explore.route) },
                onNavigateActivity = { navController.navigate(Screen.Activity.route) },
                onNavigateProfile  = { navController.navigate(Screen.Profile.route) }
            )
        }

        // ── Profile ───────────────────────────────────────────────
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateHome     = { navController.navigate(Screen.Home.route) },
                onNavigateExplore  = { navController.navigate(Screen.Explore.route) },
                onNavigateActivity = { navController.navigate(Screen.Activity.route) },
                onSettingsClick    = { navController.navigate(Screen.Setting.route) }
            )
        }

        // ── Settings ──────────────────────────────────────────────
        composable(Screen.Setting.route) {
            ProfileScreen(
                onNavigateHome     = { navController.navigate(Screen.Home.route) },
                onNavigateExplore  = { navController.navigate(Screen.Explore.route) },
                onNavigateActivity = { navController.navigate(Screen.Activity.route) },
                onSettingsClick    = { }
            )
        }

        // ── Create Habit ──────────────────────────────────────────
        composable(Screen.CreateHabit.route) {
            HomeScreen(
                onNavigateExplore  = { navController.navigate(Screen.Explore.route) },
                onNavigateActivity = { navController.navigate(Screen.Activity.route) },
                onNavigateProfile  = { navController.navigate(Screen.Profile.route) }
            )
        }

        // ── Challenge Detail ──────────────────────────────────────
        composable(Screen.ChallengeDetail.route) {
            HomeScreen(
                onNavigateExplore  = { navController.navigate(Screen.Explore.route) },
                onNavigateActivity = { navController.navigate(Screen.Activity.route) },
                onNavigateProfile  = { navController.navigate(Screen.Profile.route) }
            )
        }
    }
}