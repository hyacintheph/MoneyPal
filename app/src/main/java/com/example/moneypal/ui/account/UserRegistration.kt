package com.example.moneypal.ui.account

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.R
import com.example.moneypal.UserAccountActivity
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.*

/**
 * @author Hyacinthe TSAGUE
 */

@Composable
fun UserRegistration(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    // scroll state
    val scrollableState = rememberScrollState()
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var isUserAgreeWithTerms by rememberSaveable() {
        mutableStateOf(false)
    }
    val context = LocalContext.current.applicationContext
    MoneyPalTheme {
        // default container
       Scaffold() {


           // column for all content
           Column(modifier = modifier
               .fillMaxSize()
               .verticalScroll(state = scrollableState)) {
               // header for other screen
               HeaderConnexion(
                   title = stringResource(R.string.create_an_account),
                   modifier = modifier
                       .background(MaterialTheme.colors.primary), navController = navController)
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
                   // user phone textfield
                   OutlinedTextField(
                       value = phoneNumber,
                       onValueChange = {phoneNumber = it},
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                       label = { Text(text = stringResource(id = R.string.phone_placeholder))},
                       modifier = modifier
                           .fillMaxWidth()
                           .padding(horizontal = 16.dp),
                       singleLine = true
                   )
                   // choose terms and conditions
                   Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = modifier.fillMaxWidth()) {
                       Checkbox(checked = isUserAgreeWithTerms,
                           onCheckedChange = {isUserAgreeWithTerms = it},
                           modifier = modifier.padding(0.dp))
                       Text(text = stringResource(R.string.read_terms_and_condition))
                       Text(text = stringResource(R.string.terms_and_conditions),
                           modifier = modifier.padding(start = 4.dp),
                           style = TextStyle(fontWeight = FontWeight.Normal,
                               textDecoration = TextDecoration.Underline)
                       )
                   }

                   // sign up process by getting user phone number
                   Button(onClick = {
                       processPhoneValidation(
                           context,
                           navController,
                           phoneNumber,
                           isUserAgreeWithTerms) },
                       modifier = modifier
                           .fillMaxWidth()
                           .padding(16.dp)) {
                       Text(text = stringResource(R.string.sign_up))
                   }
                   // sign in info and clickable text for sign in
                   Row(modifier = modifier
                       .padding(top = 24.dp)
                       .fillMaxWidth(),
                       horizontalArrangement = Arrangement.Center,
                       verticalAlignment = Alignment.CenterVertically) {
                       Text(text = stringResource(R.string.have_account))
                       ClickableText(
                           text = AnnotatedString(stringResource(id = R.string.sign_in_here)),
                           onClick = {navController.navigate(NavRoutes.Login.toString())},
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

fun processPhoneValidation(context: Context, navController: NavHostController,
                           phoneNumber: String,
                           isUserAgreeWithTerms: Boolean){
    // if phone number is blank input
    // show toast message to invite him to enter phone number
    // invalid number will be test on CodeVerification composable
    if(phoneNumber.isBlank()){
        Toast.makeText(context, context.getString(R.string.provide_phone_number),
            Toast.LENGTH_LONG).show()
    }else{
        if(isUserAgreeWithTerms){
            SingleRouteNavigation(
                navController,
                "${NavRoutes.CodeVerification}/${phoneNumber}"
            )
        }else{
            Toast.makeText(context,context.getString(R.string.provide_phone_number),
                Toast.LENGTH_LONG).show()
        }
    }

}


@Preview(showBackground = true)
@Composable
fun UserRegistrationPreview(){
    UserRegistration()
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun UserRegistrationDarkPreview(){
    UserRegistration()
}