package com.example.moneypal.ui.account

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.splitCode
import com.example.moneypal.viewmodels.UserAccountViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moneypal.*
import com.example.moneypal.R
import com.example.moneypal.utils.findActivity
import kotlinx.coroutines.awaitCancellation

@Composable
fun CodeVerification(
    modifier: Modifier = Modifier,
    viewModel: UserAccountViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    phoneNumber: String = "",
    tag: String = ""
){
    // find correct activity
    val activity: Activity = when(LocalContext.current.findActivity()){
        is UserAccountActivity -> LocalContext.current.findActivity() as UserAccountActivity
        else  -> LocalContext.current.findActivity() as LoginActivity
    }
    val isInvalidCredential:Boolean by viewModel.isInvalidCredential.observeAsState(false)
    val isTooManyRequest: Boolean by viewModel.isTooManyRequest.observeAsState(false)
    val isErrorUploadImage: Boolean by viewModel.isErrorUploadImage.observeAsState(false)
    val isSuccessfullyAuthenticated: Boolean by viewModel.isSuccessfullyAuthenticated.observeAsState(false)
    val isErrorAuthenticated: Boolean by viewModel.isErrorAuthenticated.observeAsState(false)
    val isInputCodeInvalid: Boolean by viewModel.isInputCodeInvalid.observeAsState(false)
    val isCodeVerificationSend: Boolean by viewModel.isCodeVerificationSend.observeAsState(false)
    val isErrorUpdatedProfile: Boolean by viewModel.isErrorUpdatedProfile.observeAsState(false)
    val isInvalidAccount: Boolean by viewModel.isInvalidAccount.observeAsState(false)
    val focusManager = LocalFocusManager.current
    val code: String by viewModel.code.observeAsState("")
    var isCodeShouldBeResend by rememberSaveable() {
        mutableStateOf(false)
    }
    var isCodeValidated by rememberSaveable() {
        mutableStateOf(false)
    }
    val maxValue = 2
    var code1 by rememberSaveable() {
        mutableStateOf("")
    }
    var code2 by rememberSaveable() {
        mutableStateOf("")
    }
    var code3 by rememberSaveable() {
        mutableStateOf("")
    }
    var code4 by rememberSaveable() {
        mutableStateOf("")
    }
    var code5 by rememberSaveable() {
        mutableStateOf("")
    }
    var code6 by rememberSaveable() {
        mutableStateOf("")
    }
    MoneyPalTheme {
        Scaffold {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.padding(16.dp)) {
                Image(
                    painter = if(isSystemInDarkTheme())
                        painterResource(id = R.drawable.moneypal_logo_white)
                    else painterResource(id = R.drawable.moneypal_logo_colored),
                    contentDescription = stringResource(R.string.moneypal_logo),
                    modifier = modifier.size(50.dp))
                Text(text = stringResource(id = R.string.app_name),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)

                Text(text = stringResource(R.string.validation_code),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(8.dp)
                )
                Text(text = stringResource(id = R.string.enter_digit))
                Row(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextField(value = if (code != "") splitCode(code, 1) else code1,
                        onValueChange = {if(it.length < maxValue) code1 = it},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = !isCodeValidated
                    )
                    OutlinedTextField(value = if (code != "") splitCode(code, 2) else code2,
                        onValueChange = {if(it.length < maxValue) code2 = it},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = !isCodeValidated
                    )
                    OutlinedTextField(value = if (code != "") splitCode(code, 3) else code3,
                        onValueChange = {if(it.length < maxValue) code3 = it},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = !isCodeValidated
                    )
                    OutlinedTextField(value = if (code != "") splitCode(code, 4) else code4,
                        onValueChange = {if(it.length < maxValue) code4 = it},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = !isCodeValidated
                    )
                    OutlinedTextField(value = if (code != "") splitCode(code, 5) else code5,
                        onValueChange = {if(it.length < maxValue) code5 = it},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = !isCodeValidated
                    )
                    OutlinedTextField(value = if (code != "") splitCode(code, 6) else code6,
                        onValueChange = {if(it.length < maxValue) code6 = it},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = !isCodeValidated
                    )
                }

                // launching phone number verification on coroutine scope without delay
                LaunchedEffect(Unit){
                    viewModel.processAuthentication(activity, navController, phoneNumber,
                    isAccountCreated = (tag == "LOGIN"))

                }
                Log.d("Activity", tag)
                // use coroutine LaunchEffect side effect to listen on asynchronous
                // sending sms code
                // 60 seconds of delay, this delay is the timeout of sending sms code verification
                LaunchedEffect(code){
                    viewModel.processCodeVerification()
                    if(code.isNotBlank()){
                        isCodeValidated = true
                    }
                    awaitCancellation()
                }
                // Automatic retrieving sms code have been implemented with CLOUDOTP sms provider ,
                // so this button perform user
                // input code if code have send by other sms providers
                Button(onClick = {
                    focusManager.clearFocus()
                                 if(tag.isNotBlank()){
                                     viewModel.processCodeVerificationWithCode(
                                         activity = activity,
                                         userInputCode = code1+code2+code3+code4+code5+code6,
                                        isAccountCreated = (tag == "LOGIN"))
                                 }else{
                                     viewModel.processCodeVerificationWithCode(
                                         activity = activity,
                                         userInputCode = code1+code2+code3+code4+code5+code6, true)
                                 }
                },
                    enabled = isCodeValidated,
                    modifier = modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.confirm))
                }
                Row(modifier = modifier.padding(top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.code_not_receive_question))
                    ClickableText(text = AnnotatedString(stringResource(R.string.resend_code)),
                        onClick = {
                            isCodeShouldBeResend = true
                            if(tag.isNotBlank()){
                                viewModel.resendVerificationCode(activity, phoneNumber,
                                    isAccountCreated = (tag == "LOGIN"))
                            }else{
                                viewModel.resendVerificationCode(activity, phoneNumber, true)
                            }
                                  },
                    modifier = modifier.padding(start = 8.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary,
                        fontSize = 16.sp
                    ))
                }
                if(!isCodeVerificationSend || isCodeShouldBeResend){
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 24.dp, bottom = 24.dp)) {
                        Text(text = "Sending code message...")
                        LinearProgressIndicator(modifier = modifier.padding(top = 8.dp))

                    }
                }
                if(isInvalidCredential) {
                    Toast.makeText(
                        activity,
                        stringResource(R.string.invalid_phone_number), Toast.LENGTH_LONG
                    ).show()
                }
                if(isTooManyRequest){
                    Toast.makeText(activity,
                        stringResource(R.string.to_many_request), Toast.LENGTH_LONG).show()
                }

                if(isInputCodeInvalid){
                    Toast.makeText(activity,
                        stringResource(R.string.invalid_verification_code),Toast.LENGTH_LONG).show()
                }
                if(isErrorUpdatedProfile){
                    Toast.makeText(activity,
                        stringResource(R.string.successfully_update_profile),Toast.LENGTH_LONG).show()
                }
                if(isErrorUploadImage){
                    Toast.makeText(activity,
                        stringResource(R.string.error_image),Toast.LENGTH_LONG).show()
                }
                if(isErrorAuthenticated){
                    Toast.makeText(activity,
                        stringResource(R.string.authentication_error),Toast.LENGTH_LONG).show()
                }
                if(isInvalidAccount){
                    activity.startActivity(Intent(activity,
                        WelcomeUserActivity::class.java))
                }

                if(isSuccessfullyAuthenticated && (tag == "LOGIN")){
                    activity.startActivity(Intent(activity,
                        MainActivity::class.java))
                }
            }
        }
    }

}


@Composable
fun CodeVerificationPreview(modifier: Modifier = Modifier){
    MoneyPalTheme {
        Scaffold {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier.padding(16.dp)) {
                Image(
                    painter = if(isSystemInDarkTheme())
                        painterResource(id = R.drawable.moneypal_logo_white)
                    else painterResource(id = R.drawable.moneypal_logo_colored),
                    contentDescription = stringResource(R.string.moneypal_logo),
                    modifier = modifier.size(50.dp))
                Text(text = stringResource(id = R.string.app_name),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)

                Text(text = stringResource(R.string.validation_code),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = modifier.padding(8.dp)
                )
                Text(text = stringResource(id = R.string.enter_digit))
                Row(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    OutlinedTextField(value = "",
                        onValueChange = {},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                    OutlinedTextField(value = "",
                        onValueChange = {},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                    OutlinedTextField(value = "",
                        onValueChange = {},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                    OutlinedTextField(value = "",
                        onValueChange = {},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                    OutlinedTextField(value = "",
                        onValueChange = {},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                    OutlinedTextField(value = "",
                        onValueChange = {},
                        modifier = modifier
                            .width(50.dp)
                            .height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                    )
                }

                Button(onClick = {},
                    enabled = false,
                    modifier = modifier.fillMaxWidth()) {
                    Text(text = stringResource(R.string.confirm))
                }
                Row(modifier = modifier.padding(top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = stringResource(R.string.code_not_receive_question))
                    ClickableText(text = AnnotatedString(stringResource(R.string.resend_code)),
                        onClick = {
                        },
                        modifier = modifier.padding(start = 8.dp),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary,
                            fontSize = 16.sp
                        ))
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CodeVerificationLightPreview(){
    CodeVerificationPreview()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CodeVerificationDarkPreview(){
    CodeVerificationPreview()
}