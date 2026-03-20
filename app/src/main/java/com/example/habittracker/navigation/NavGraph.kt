package com.example.habittracker.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.ui.screens.auth.LoginScreen
import com.example.habittracker.ui.screens.auth.SignUpScreen
import com.example.habittracker.ui.screens.onboarding.OnboardingScreen
import com.example.habittracker.ui.screens.splash.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        //Splash Screen
        composable(Screen.Splash.route){
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Onboarding.route){
                        popUpTo(Screen.Splash.route){
                            inclusive = true
                        }
                    }
                }
            )
        }

        //Onboarding Screen
        composable(Screen.Onboarding.route){
            OnboardingScreen(
                onContinueWithEmail = {
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Onboarding.route){
                            inclusive = true
                        }
                    }
                },
                onGoogleSignIn = { },
                onFacebookSignIn = { },
                onAppleSignIn = { }
            )
        }

        //Login Screen
        composable(Screen.Login.route){
            LoginScreen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { navController.navigate(Screen.CreateAccount.route) },
//                onForgetPassword = { },
                onCreateAccount  = {
                    navController.navigate(Screen.CreateAccount.route)
                }
            )
        }

        //SignUp Screen
        composable(Screen.CreateAccount.route) {
            SignUpScreen(
                onBackClick = { navController.popBackStack() },
                onNextClick = { navController.navigate(Screen.Gender.route) },
                onLogin = { navController.navigate(Screen.Login.route) },
                onCreateAccount= {}
            )
        }

    }
}