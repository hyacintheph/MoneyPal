package com.example.moneypal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.ui.account.AccountUserNavController
import com.example.moneypal.ui.theme.MoneyPalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoneyPalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    UserAccountProcess()
                }
            }
        }
    }

}

@Composable
fun UserAccountProcess() {
    AccountUserNavController(navController = rememberNavController())
}

