package com.example.moneypal.ui.home.profile

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.MainActivity
import com.example.moneypal.R
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.findActivity
import com.example.moneypal.viewmodels.UserAccountViewModel

@Composable
fun EditAccount(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    userAccountViewModel: UserAccountViewModel = hiltViewModel()){
    var userName by rememberSaveable() { mutableStateOf("") }
    var phoneNumber by rememberSaveable() { mutableStateOf("") }
    val activity: MainActivity = LocalContext.current.findActivity() as MainActivity
    val isSuccessfullyUpdated by userAccountViewModel.isProfileUpdated.observeAsState(false)

    MoneyPalTheme {
       Surface {
           Column(modifier = modifier
               .fillMaxSize()
               .padding(16.dp)) {
               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   modifier = modifier.fillMaxWidth()) {
                   IconButton(onClick = {
                       // navigate on previous composable
                       navController.popBackStack()
                   }) {
                       Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack")
                   }
                   Text(text = "Edit Account",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                   )
               }
               OutlinedTextField(
                   value = userName,
                   onValueChange = {userName = it},
                   label = {Text(text = stringResource(R.string.your_name_placeholder))},
                   modifier = modifier
                       .padding(top = 24.dp)
                       .fillMaxWidth())
               OutlinedTextField(
                   value = phoneNumber,
                   onValueChange = {phoneNumber = it},
                   label = {Text(text = stringResource(R.string.phone_placeholder))},
                   modifier = modifier
                       .padding(top = 24.dp)
                       .fillMaxWidth())
               Button(onClick = {
                   // TODO: complete update phone number with verification steps
                   // update only username
                               if(userName.isNotBlank() && phoneNumber.isBlank()){
                                   userAccountViewModel.updateUserProfile(userName = userName)
                               }else{
                                   Toast.makeText(activity,
                                       "You can only update your name for moment !",
                                       Toast.LENGTH_LONG).show()
                               }
                   // update only phone number
//                   if (userName.isBlank() && phoneNumber.isNotBlank()){
//                       userAccountViewModel.updateUserProfile(phoneNumber = phoneNumber)
//                   }
//                   // update username and phone number
//                   if (userName.isNotBlank() && phoneNumber.isNotBlank()){
//                       userAccountViewModel.updateUserProfile(userName = userName,
//                           phoneNumber = phoneNumber)
//                   }
                   if(userName.isBlank() && phoneNumber.isBlank()){
                       Toast.makeText(activity,
                           activity.getString(R.string.enter_name_or_phone),
                           Toast.LENGTH_LONG).show()
                   }
               },
                   modifier = modifier
                       .padding(top = 24.dp)
                       .fillMaxWidth()) {
                   Text(text = "Save")
               }
               if(isSuccessfullyUpdated){
                   Toast.makeText(activity, stringResource(R.string.successfully_update_profile),
                       Toast.LENGTH_LONG).show()
               }
           }
       }
   }
}

@Preview
@Composable
fun EditAccountPreview(modifier: Modifier = Modifier){
    MoneyPalTheme {
        Surface {
            Column(modifier = modifier
                .fillMaxSize()
                .padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxWidth()) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack")
                    }
                    Text(text = "Edit Account",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {Text(text = stringResource(R.string.your_name_placeholder))},
                    modifier = modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth())
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {Text(text = stringResource(R.string.phone_placeholder))},
                    modifier = modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth())
                Button(onClick = { /*TODO*/ },
                    modifier = modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditAccountLightPreview(){
    EditAccountPreview()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EditAccountDarkPreview(){
    EditAccountPreview()
}