package com.example.moneypal.ui.home.tontine

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.MainActivity
import com.example.moneypal.R
import com.example.moneypal.models.UserData
import com.example.moneypal.ui.theme.CustomGray
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.*
import com.example.moneypal.viewmodels.TontineViewModel
import com.example.moneypal.viewmodels.UserViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun AddRecipient(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    tontineViewModel: TontineViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel(),
){
    val scrollState = rememberScrollState()
    val users by userViewModel.allUsers.observeAsState()
    val isTontineSuccessfullyCreate: Boolean by tontineViewModel
        .isTontineSuccessfullyCreated.observeAsState(false)
    val isTontineUnSuccessfullyCreate: Boolean by tontineViewModel
        .isTontineUnSuccessfullyCreated.observeAsState(false)
    var isLaunched by remember {
        mutableStateOf(false)
    }

    val activity: MainActivity = LocalContext.current.findActivity() as MainActivity
    val selectedRecipient: List<UserData> by userViewModel.selectedRecipient.observeAsState(listOf())
    MoneyPalTheme {
        Surface(modifier = modifier.fillMaxSize(),
            color = if(isSystemInDarkTheme()) MaterialTheme.colors.surface else CustomGray
        ) {

            Column(modifier = modifier.fillMaxWidth()) {
                if((!isTontineSuccessfullyCreate || isTontineUnSuccessfullyCreate) && isLaunched){
                    LinearProgressIndicator(modifier = modifier.fillMaxWidth())
                }
                Column(modifier = modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(16.dp)) {
                    Row(modifier = modifier
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "ArrowBack")
                            }
                            Text(
                                text = stringResource(R.string.add_recipient),
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Row(modifier = modifier
                            .width(130.dp)
                            .padding(start = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Surface(modifier = modifier
                                .size(50.dp)
                                .border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = CircleShape
                                ),
                                shape = CircleShape,
                                color = MaterialTheme.colors.primary
                            ) {
                                Column(modifier = modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(painter = painterResource(R.drawable.ic_baseline_check_24),
                                        contentDescription = "Check")
                                }
                            }
                            Surface(modifier = modifier
                                .size(50.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colors.primary,
                                    shape = CircleShape
                                ),
                                shape = CircleShape
                            ) {
                                Column(modifier = modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = "2", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                }
                            }
                        }
                    }
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = stringResource(R.string.total_members),
                            fontWeight = FontWeight.Bold)
                        Text(text = "${selectedRecipient.size}", fontWeight = FontWeight.Bold)
                    }
                    OutlinedButton(modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                        onClick = { SingleRouteNavigation(navController,
                            "${NavRoutes.SelectRecipient}") }) {
                        Icon(painter = painterResource(R.drawable.ic_baseline_person_24),
                            contentDescription = "")
                        Text(text = stringResource(R.string.add_recipient),
                            modifier = modifier.padding(start = 4.dp))
                    }
                    Column(modifier = modifier.fillMaxSize()) {
                        // content
                        users?.let{
                            LazyColumn(modifier = modifier
                                .fillMaxWidth()
                                .heightIn(min = 400.dp, max = 600.dp)){
                                items(items = selectedRecipient){ item: UserData ->

                                    UserCard(
                                        modifier = modifier,
                                        userData = item,
                                        type = "delete")
                                }
                            }
                        }
//                    if(isLoading){
//                        CircularProgressIndicator(modifier = modifier.size(100.dp))
//                    }
                    }
//                error?.let {
//                    error.printStackTrace()
//                    //Toast.makeText(activity, "${error.printStackTrace()}", Toast.LENGTH_LONG).show()
//                }
                    Button(modifier = modifier
                        .fillMaxWidth(),
                        onClick = {
                            tontineViewModel.createNewTontine(users = selectedRecipient)
                            isLaunched = true
                            if(isTontineSuccessfullyCreate){
                                isLaunched = false
                                navController.popBackStack()
                                Toast.makeText(activity, activity
                                    .getString(R.string.successfully_created_tontine),
                                    Toast.LENGTH_LONG).show()
                            }else{
                                Toast.makeText(activity, activity
                                    .getString(R.string.unsuccessfully_created_tontine),
                                    Toast.LENGTH_LONG).show()
                            }
                        }) {
                        Text(text = stringResource(R.string.finish))
                    }
                }
            }
            }
    }
}

@Preview
@Composable
fun AddRecipientPreview(){
    AddRecipient()
}