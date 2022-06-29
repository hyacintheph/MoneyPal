package com.example.moneypal.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moneypal.R

@Composable
fun HeaderConnexion(
    title: String,
    modifier: Modifier,
    showBackIcon: Boolean = true,
    navController: NavHostController
){
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(MaterialTheme.colors.primary)
    ) {
        if(showBackIcon){
            IconButton(onClick = { navController.navigate(NavRoutes.Login.toString()) }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = Color.White)
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.moneypal_logo_white),
                contentDescription = "MoneyPal logo",

                )
            Text(text = stringResource(id = R.string.app_name),
                fontSize = 16.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.save_money_text),
                modifier = modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle2,
                color = Color.White,
                fontWeight = FontWeight.Light)

            Text(text = title, style = MaterialTheme.typography.h5,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = modifier.padding(8.dp)
            )
        }
    }
}