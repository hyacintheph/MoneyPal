package com.example.moneypal.ui.home.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.moneypal.utils.NavRoutes

fun NavGraphBuilder.accountGraph(navController: NavHostController) {
    navigation(route = "${NavRoutes.ProfileScreen}", startDestination = "${NavRoutes.AccountProfile}" ){
        composable("${NavRoutes.AccountProfile}"){
            UserAccount(navController = navController)
        }
        composable("${NavRoutes.EditAccount}"){
            EditAccount(navController = navController)
        }
    }
}