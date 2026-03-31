package com.example.habittracker.navigation

sealed class Screen(val route: String) {
    object Splash         : Screen("splash")
    object Onboarding     : Screen("onboarding")
    object Login          : Screen("login")
    object CreateAccount  : Screen("create_account")
    object Gender         : Screen("gender")
    object HabitPicker    : Screen("habit_picker")
    object Home           : Screen("home")
    object Explore        : Screen("explore")
    object Activity       : Screen("activity")
    object Leaderboard    : Screen("leaderboard")
    object Profile        : Screen("profile")
    object Setting        : Screen("setting")
    object CreateHabit    : Screen("create_habit")
    object HabitDetail    : Screen("habit_detail")
    object ChallengeDetail: Screen("challenge_detail")
}