package com.example.moneypal.ui.home.tontine

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moneypal.MainActivity
import com.example.moneypal.R
import com.example.moneypal.models.UserData
import com.example.moneypal.ui.theme.CustomGray
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.SelectedUserCard
import com.example.moneypal.utils.UserCard
import com.example.moneypal.utils.findActivity
import com.example.moneypal.viewmodels.UserViewModel

@Composable
fun SelectRecipient(modifier: Modifier = Modifier,
                    userViewModel: UserViewModel,
                    navController: NavHostController) {
    val users by userViewModel.allUsers.observeAsState()
    var selectedRecipients by rememberSaveable() {
        mutableStateOf(listOf<UserData>())
    }
    val activity: MainActivity = LocalContext.current.findActivity() as MainActivity
    MoneyPalTheme() {
        Surface(modifier = modifier.fillMaxSize(),
        ) {
            Column(modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack")
                        }

                        Column() {
                            Text(
                                text = stringResource(R.string.select_recipient),
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(text = "${selectedRecipients.size} ${stringResource(R.string.members_selected)}")
                        }
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "search recipient")
                    }
                }
                if(selectedRecipients.isNotEmpty()){
                    LazyRow(modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)){
                        items(items = selectedRecipients){item: UserData ->
                            SelectedUserCard(
                                modifier.clickable {
                                    selectedRecipients = selectedRecipients.toMutableList().also {
                                        it.remove(item)
                                    }  },
                                imageUri = Uri.parse(item.avatar),
                                userName = item.name!!)
                        }
                    }
                }else{
                    Text(text = stringResource(id = R.string.any_user_selected),
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 16.dp), fontWeight = FontWeight.Bold)
                }

                users?.let {
                    LazyColumn(modifier = modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                        .heightIn(min = 400.dp, max = 600.dp)){
                        items(items = users!!){ item: UserData ->
                            //Toast.makeText(activity, imageUri.toString(), Toast.LENGTH_LONG).show()
                            UserCard(
                                modifier = modifier
                                    .clickable {
                                        selectedRecipients = selectedRecipients.toMutableList()
                                            .also { it.remove(item) }
                                        selectedRecipients = selectedRecipients + listOf(item)
                                        item.clicked = !item.clicked
                                               },
                                userData = item,
                                type = "select",
                                userViewModel = userViewModel
                            )
                        }
                    }
                }
                Button(onClick = {
                    userViewModel.setSelectedRecipient(selectedRecipients)
                    navController.popBackStack()
                                 Log.d("Activity", "${selectedRecipients[0].phoneNumber}")
                }, modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)) {
                    Text(text = stringResource(id = R.string.complete))
                }
            }
        }
    }
}

@Preview
@Composable
fun SelectRecipientPreview(modifier: Modifier = Modifier){
    MoneyPalTheme() {
        Surface(modifier = modifier.fillMaxSize(),
            color = if(isSystemInDarkTheme()) MaterialTheme.colors.surface else CustomGray
        ) {
            Column(modifier = modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack")
                        }

                        Column() {
                            Text(
                                text = stringResource(R.string.select_recipient),
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(text = "8 ${stringResource(R.string.members_selected)}")
                        }
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "search recipient")
                    }
                }
                LazyRow(modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)){

                }
                LazyColumn(modifier = modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
                    .heightIn(500.dp, 800.dp)){

                }
                Button(onClick = { /*TODO*/ }, modifier = modifier
                    .fillMaxWidth()
                    .height(50.dp)) {
                    Text(text = stringResource(id = R.string.complete))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectRecipientLightPreview() {
    SelectRecipientPreview()
}