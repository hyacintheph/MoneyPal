package com.example.moneypal.ui.account

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moneypal.UserAccountActivity
import com.example.moneypal.utils.NavRoutes.*


@Composable
fun AccountUserNavController(navController: NavHostController) {
    // default route
    // passing phone number args to CodeVerification composable
    NavHost(navController = navController, startDestination = "$NewAccount/{phoneNumber}") {
        composable("$NewAccount/{phoneNumber}") {
            UserRegistration(navController = navController)
        }
        composable(
            "$CodeVerification/{phoneNumber}",
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("phoneNumber")?.let {
                CodeVerification(
                    navController = navController,
                    phoneNumber = it
                )
            }
        }
        // provide uid code of Firebase User to WelcomeUser composable
        composable("$WelcomeUser") {
                WelcomeUser()

        }
    }

}

