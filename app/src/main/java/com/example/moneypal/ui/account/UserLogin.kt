package com.example.moneypal.ui.account

import android.content.Intent
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.LoginActivity
import com.example.moneypal.R
import com.example.moneypal.UserAccountActivity
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.*
import com.example.moneypal.viewmodels.UserAccountViewModel


@Composable
fun UserLogin(modifier: Modifier = Modifier,
              navController: NavHostController = rememberNavController(),
                viewModel: UserAccountViewModel = hiltViewModel()) {
    val isSuccessfullyAuthenticated: Boolean by viewModel.isSuccessfullyAuthenticated.observeAsState(false)
    var phoneNumber by rememberSaveable() {
        mutableStateOf("")
    }
    val activity = LocalContext.current.findActivity() as LoginActivity
    MoneyPalTheme {
        // default container
        Scaffold() {
            // scroll state
            val scrollableState = rememberScrollState()
            // column for all content
            Column(modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = scrollableState)) {
                // header for other screen
                HeaderConnexion(
                    title = stringResource(R.string.login),
                    modifier = modifier
                        .background(MaterialTheme.colors.primary),
                    showBackIcon = false,
                    navController = navController
                )
                // wave image background
                WaveHeader(modifier = modifier.fillMaxWidth(),
                )
                // content for user interaction
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // user phone Textfield
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {phoneNumber = it},
                        label = { Text(text = stringResource(id = R.string.phone_placeholder)) },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        singleLine = true ,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    // sign up process by user
                    Button(onClick = {
                                     if(phoneNumber.isNotBlank()){
                                        SingleRouteNavigation(navController,
                                            "${NavRoutes.CodeVerification}/${phoneNumber}")
                                     }else{
                                         Toast.makeText(activity,
                                         activity.getString(R.string.provide_phone_number),
                                             Toast.LENGTH_LONG).show()
                                     }
                    },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)) {
                        Text(text = stringResource(id = R.string.login))
                    }
                    // sign in info and clickable text for sign in
                    Row(modifier = modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.new_question))
                        ClickableText(
                            text = AnnotatedString(stringResource(id = R.string.create_new_account)),
                            onClick = {
                                      activity.startActivity(Intent(activity,
                                          UserAccountActivity::class.java))
                            },
                            modifier = modifier.padding(4.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.primary,
                                fontSize = 16.sp
                            ),
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun UserLoginPreview(modifier: Modifier = Modifier,
              navController: NavHostController = rememberNavController()) {
    MoneyPalTheme {
        // default container
        Scaffold() {
            // scroll state
            val scrollableState = rememberScrollState()
            // column for all content
            Column(modifier = modifier
                .fillMaxSize()
                .verticalScroll(state = scrollableState)) {
                // header for other screen
                HeaderConnexion(
                    title = stringResource(R.string.login),
                    modifier = modifier
                        .background(MaterialTheme.colors.primary),
                    showBackIcon = false,
                    navController = navController
                )
                // wave image background
                WaveHeader(modifier = modifier.fillMaxWidth(),
                )
                // content for user interaction
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // user phone Textfield
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = stringResource(id = R.string.phone_placeholder)) },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        singleLine = true ,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    // sign up process by user
                    Button(onClick = { /*TODO*/ },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp)) {
                        Text(text = stringResource(id = R.string.login))
                    }
                    // sign in info and clickable text for sign in
                    Row(modifier = modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = stringResource(R.string.new_question))
                        ClickableText(
                            text = AnnotatedString(stringResource(id = R.string.create_new_account)),
                            onClick = {navController.navigate(NavRoutes.NewAccount.toString())},
                            modifier = modifier.padding(4.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colors.primary,
                                fontSize = 16.sp
                            ),
                        )
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun UserLoginPreview(){
    UserLoginPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun UserLoginDarkPreview(){
    UserLoginPreview()
}