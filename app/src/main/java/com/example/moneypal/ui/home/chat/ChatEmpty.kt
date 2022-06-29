package com.example.moneypal.ui.home.chat

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.MainActivity
import com.example.moneypal.R
import com.example.moneypal.ui.theme.CustomGray
import com.example.moneypal.ui.theme.MoneyPalTheme

@Composable
fun ChatEmpty(modifier: Modifier = Modifier,
              navController: NavHostController = rememberNavController()) {
    MoneyPalTheme {
        Surface( color = if(isSystemInDarkTheme()) MaterialTheme.colors.surface else CustomGray) {
          Column(modifier = modifier
              .fillMaxSize()
              .padding(top = 16.dp, start = 16.dp, end = 16.dp)
              .verticalScroll(rememberScrollState())) {
              Text(text = stringResource(id = R.string.chats),
                  style = MaterialTheme.typography.h5,
                  fontWeight = FontWeight.Bold)
              Column(modifier = modifier.fillMaxWidth().height(450.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                  Image(painter = painterResource(id = R.drawable.conversion),
                      modifier = modifier.size(150.dp).alpha(0.6f),
                      contentDescription = "IconChats")
                  Text(text = stringResource(R.string.any_conversation_message),
                    modifier = modifier
                        .fillMaxWidth()
                        .alpha(0.8f)
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center)
              }
          }
        }
    }
}

@Preview
@Composable
fun ChatEmptyLightPreview(){
    ChatEmpty()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ChatEmptyDarkPreview(){
    ChatEmpty()
}