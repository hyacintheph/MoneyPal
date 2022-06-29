package com.example.moneypal.ui.home.tontine

import NewTontine
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.moneypal.utils.NavRoutes
import com.example.moneypal.viewmodels.TontineViewModel
import com.example.moneypal.viewmodels.UserViewModel

fun NavGraphBuilder.tontineNavGraph(
    navController: NavHostController,
    tontineViewModel: TontineViewModel,
    userViewModel: UserViewModel
){
    navigation(route = "${NavRoutes.HomeScreen}", startDestination = "${NavRoutes.TontineEmpty}"){
        composable("${NavRoutes.TontineEmpty}"){
            TontineEmpty(navController = navController,
                tontineViewModel = tontineViewModel, userViewModel = userViewModel)
        }
        composable("${NavRoutes.NewTontine}"){
            NewTontine(navController = navController,
                tontineViewModel = tontineViewModel)
        }
        composable("${NavRoutes.AddRecipient}"){
            AddRecipient(navController = navController,
                tontineViewModel = tontineViewModel, userViewModel = userViewModel)
        }
        composable("${NavRoutes.SelectRecipient}"){
            SelectRecipient(userViewModel = userViewModel, navController = navController)
        }
    }
}