package com.example.habittracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.ui.screens.activity.ActivityScreen
import com.example.habittracker.ui.screens.auth.GenderScreen
import com.example.habittracker.ui.screens.auth.HabitPicker
import com.example.habittracker.ui.screens.auth.LoginScreen
import com.example.habittracker.ui.screens.auth.SignUpScreen
import com.example.habittracker.ui.screens.challenge.ChallengeDetailScreen
import com.example.habittracker.ui.screens.explore.ExploreScreen
import com.example.habittracker.ui.screens.habit.CreateHabitScreen
import com.example.habittracker.ui.screens.habit.HabitDetailScreen
import com.example.habittracker.ui.screens.home.HomeScreen
import com.example.habittracker.ui.screens.leaderboard.LeaderboardScreen
import com.example.habittracker.ui.screens.onboarding.OnboardingScreen
import com.example.habittracker.ui.screens.profile.ProfileScreen
import com.example.habittracker.ui.screens.settings.SettingsScreen
import com.example.habittracker.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController    = navController,
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
                onNextClick     = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
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
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // ── Home ──────────────────────────────────────────────────
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateExplore    = { navController.navigate(Screen.Explore.route) },
                onNavigateActivity   = { navController.navigate(Screen.Activity.route) },
                onNavigateProfile    = { navController.navigate(Screen.Profile.route) },
                onViewAllHabits      = { },
                onViewAllChallenges  = { navController.navigate(Screen.Leaderboard.route) },
                // ← wired up: FAB → Create Custom Habit
                onCreateCustomHabit  = { navController.navigate(Screen.CreateHabit.route) },
                // ← wired up: HabitCard tap → Habit Detail
                onHabitClick         = { navController.navigate(Screen.HabitDetail.route) }
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
            ActivityScreen(
                onNavigateHome    = { navController.navigate(Screen.Home.route) },
                onNavigateExplore = { navController.navigate(Screen.Explore.route) },
                onNavigateProfile = { navController.navigate(Screen.Profile.route) }
            )
        }

        // ── Leaderboard ───────────────────────────────────────────
        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(
                onBackClick = { navController.popBackStack() }
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
            SettingsScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        // ── Create Habit ──────────────────────────────────────────
        composable(Screen.CreateHabit.route) {
            CreateHabitScreen(
                onBackClick    = { navController.popBackStack() },
                onHabitCreated = { navController.popBackStack() }
            )
        }

        // ── Habit Detail ──────────────────────────────────────────  ← NEW
        composable(Screen.HabitDetail.route) {
            HabitDetailScreen(
                onBackClick = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate(Screen.CreateHabit.route)
                }
            )
        }

        // ── Challenge Detail ──────────────────────────────────────
        composable(Screen.ChallengeDetail.route) {
            ChallengeDetailScreen(
                onBack = { navController.popBackStack() },
                onAdd  = { },
                onJoin = { }
            )
        }
    }
}