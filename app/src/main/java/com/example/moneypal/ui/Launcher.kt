package com.example.moneypal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.R
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.NavRoutes
import com.example.moneypal.utils.SingleRouteNavigation
import kotlinx.coroutines.delay

@Composable
fun Launcher(modifier: Modifier = Modifier,
             navController: NavHostController = rememberNavController()){
    MoneyPalTheme() {
        Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colors.primary) {
            LaunchedEffect(Unit){
                SingleRouteNavigation(navController, "${NavRoutes.Login}")
                delay(4000)
            }
            Column(modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Image(modifier = modifier.size(120.dp),
                    painter = painterResource(R.drawable.moneypal_logo_white),
                    contentDescription = "Moneypal")
                Text(text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LauncherPreview(){
    Launcher()
}