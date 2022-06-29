package com.example.moneypal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.moneypal.ui.home.Home
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.viewmodels.TontineViewModel
import com.example.moneypal.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // get single instance of viewmodel
        val tontineViewModel: TontineViewModel by viewModels()
        val userViewModel: UserViewModel by viewModels()
        setContent {
            MoneyPalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Home(tontineViewModel = tontineViewModel, userViewModel = userViewModel)
                }
            }
        }
    }
}

