package com.example.moneypal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.ui.Launcher
import com.example.moneypal.ui.account.CodeVerification
import com.example.moneypal.ui.account.UserLogin
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.NavRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyPalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AppLaunch()
                }
            }
        }
    }
}

@Composable
fun AppLaunch() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "${NavRoutes.Launch}"){
        composable("${NavRoutes.Launch}"){
            Launcher(navController = navController)
        }
        composable("${NavRoutes.Login}"){
            UserLogin(navController = navController)
        }
        composable(
            "${NavRoutes.CodeVerification}/{phoneNumber}",
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("phoneNumber")?.let {
                CodeVerification(
                    navController = navController,
                    phoneNumber = it,
                    tag = "LOGIN"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoneyPalTheme {
        AppLaunch()
    }
}