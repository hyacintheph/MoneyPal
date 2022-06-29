package com.example.moneypal.ui.home.tontine

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.R
import com.example.moneypal.models.Tontine
import com.example.moneypal.ui.theme.CustomGray
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.NavRoutes
import com.example.moneypal.utils.SingleRouteNavigation
import com.example.moneypal.utils.WelcomeTitle
import com.example.moneypal.viewmodels.TontineViewModel
import com.example.moneypal.viewmodels.UserViewModel


@Composable
fun TontineEmpty(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    tontineViewModel: TontineViewModel = hiltViewModel(),
    userViewModel: UserViewModel = hiltViewModel()
){
    val tontines by tontineViewModel.tontines.observeAsState(listOf())
    val tontineLoading by tontineViewModel.tontineLoading.observeAsState(false)
    val authUser = userViewModel.authUser.observeAsState()
    MoneyPalTheme() {
        Surface(modifier = modifier.fillMaxSize(),  color = if(isSystemInDarkTheme())
            MaterialTheme.colors.surface else CustomGray ) {

            if(tontines.isEmpty()){
                Column(modifier = modifier
                    .fillMaxWidth()
                    .height(600.dp)
                    .padding(top = 24.dp)
                    .verticalScroll(rememberScrollState())) {
                    WelcomeTitle(modifier = modifier)
                    (authUser.value?.name)?.let {
                        Text(
                            text = it,
                            modifier = modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                    }
                    Image(painter = painterResource(R.drawable.money_pocket),
                        contentDescription = "money_wallet",
                        modifier = modifier
                            .padding(vertical = 24.dp)
                            .size(150.dp)
                            .alpha(0.6f)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = stringResource(R.string.new_tontine_message),
                        textAlign = TextAlign.Center,
                        modifier = modifier
                            .padding(24.dp)
                            .alpha(0.8f)
                            .fillMaxWidth())
                    Button(
                        onClick = {
                            SingleRouteNavigation(navController, "${NavRoutes.NewTontine}")
                        },
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 24.dp)
                    ) {
                        Text(text = stringResource(R.string.create_new_tontine))
                    }
                }
            }
            else{
               if(tontineLoading){
                   Column(modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                       CircularProgressIndicator()
                       Text(text = stringResource(id = R.string.loading_tontine),
                        modifier = modifier.padding(vertical = 8.dp))
                   }
               }else{
                   Column(modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
                       Row(verticalAlignment = Alignment.CenterVertically,
                           horizontalArrangement = Arrangement.SpaceBetween,
                           modifier = modifier.fillMaxWidth()) {
                           Text(text = stringResource(id = R.string.home),
                               fontSize = 24.sp, fontWeight = FontWeight.Bold)
                           IconButton(onClick = { SingleRouteNavigation(navController,
                               "${NavRoutes.NewTontine}") }) {
                               Row() {
                                   Icon(painter = painterResource(id = R.drawable.ic_baseline_add_box_24),
                                       contentDescription = "icon add", tint = MaterialTheme.colors.primary)
                                   Text(text = stringResource(id = R.string.new_tontine))
                               }
                           }
                       }

                       LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)){
                           item {
                               Text(text = "${stringResource(id = R.string.your_tontine)}(${tontines.size})",
                                   fontWeight = FontWeight.Bold, fontSize = 18.sp)
                           }
                           items(items = tontines){item: Tontine ->
                               AnimatedVisibility(visible = tontines.isNotEmpty()) {
                                   TontineCard(tontine = item)
                               }
                           }
                       }
                   }
               }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TontineEmptyLightPreview(){
    TontineEmpty()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TontineEmptyDarkPreview(){
    TontineEmpty()
}