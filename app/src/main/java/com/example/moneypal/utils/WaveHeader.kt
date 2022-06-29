package com.example.moneypal.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.moneypal.R
import com.example.moneypal.ui.theme.MoneyPalTheme

@Preview("WaveHeader", showBackground = true)
@Composable
fun WaveHeader(
    modifier: Modifier = Modifier,
){
    MoneyPalTheme() {
       Box(modifier = modifier){
           Image(
               painter = painterResource(id = R.drawable.wave),
               contentDescription = "wave",
               modifier = modifier)
       }
    }
}