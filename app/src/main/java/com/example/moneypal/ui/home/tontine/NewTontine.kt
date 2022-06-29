import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moneypal.MainActivity
import com.example.moneypal.R
import com.example.moneypal.ui.theme.CustomGray
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.NavRoutes
import com.example.moneypal.utils.SingleRouteNavigation
import com.example.moneypal.utils.UserSetting
import com.example.moneypal.utils.findActivity
import com.example.moneypal.viewmodels.TontineViewModel

@Composable
fun NewTontine(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    tontineViewModel: TontineViewModel
){
    var tontineName by rememberSaveable() {
        mutableStateOf("")
    }
    var tontineDescription by rememberSaveable() {
        mutableStateOf("")
    }
    var tontineAmount by rememberSaveable() {
        mutableStateOf("")
    }
    var notifyAllMembers by rememberSaveable() {
        mutableStateOf(false)
    }
    val scrollState = rememberScrollState()
    var overflowText by rememberSaveable() {
        mutableStateOf(false)
    }
    val activity: MainActivity = LocalContext.current.findActivity() as MainActivity
    MoneyPalTheme {
        Surface(modifier = modifier.fillMaxSize(),
            color = if(isSystemInDarkTheme()) MaterialTheme.colors.surface else CustomGray) {
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
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack")
                        }
                        Text(
                            text = stringResource(id = R.string.new_tontine),
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
                                color = MaterialTheme.colors.primary,
                                shape = CircleShape
                            ),
                            shape = CircleShape) {
                            Column(modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "1", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            }
                        }
                        Surface(modifier = modifier
                            .size(50.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = CircleShape
                            ),
                            shape = CircleShape) {
                            Column(modifier = modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "2", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            }
                        }
                    }
                }
                // content
                Column(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)) {
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = tontineName,
                        onValueChange = {tontineName = it},
                        label = { Text(text = "Name")})
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        value = tontineDescription,
                        onValueChange = {if(tontineDescription.length <= 250) tontineDescription = it
                        else overflowText = true},
                        label = { Text(text = "Description")})
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), horizontalArrangement = Arrangement.End) {
                        Text(text = "${tontineDescription.length}",
                            fontWeight = FontWeight.Bold, fontSize = 12.sp,
                            color = if(overflowText) Color.Red else MaterialTheme.colors.onSurface)
                        Text(text = stringResource(R.string.caracters_total),
                            modifier = modifier.alpha(0.6f),
                            fontSize = 12.sp)
                    }
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        value = tontineAmount,
                        onValueChange = {tontineAmount = it},
                        label = { Text(text = "Amount")},
                        trailingIcon = {
                            Icon(painter = painterResource(R.drawable.ic_baseline_attach_money_24),
                                contentDescription = "money_icon")},
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    UserSetting(
                        modifier = modifier,
                        settingTitle = stringResource(R.string.notification),
                        checked = notifyAllMembers,
                        onCheckedChange = {notifyAllMembers = !notifyAllMembers},
                        titleDescription = stringResource(R.string.automatic_notify_members),
                        checkTrackColor = MaterialTheme.colors.primary
                    )
                }
                Button(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                    onClick = {
                        if(tontineName.isBlank() ||
                            tontineDescription.isBlank() || tontineAmount.isBlank()){
                            Toast.makeText(activity, activity.getString(R.string.enter_all_data),
                                Toast.LENGTH_LONG).show()
                        }else{
                            tontineViewModel.setTontineData(tontineName, tontineDescription, tontineAmount,
                                notifyAllMembers)
                            SingleRouteNavigation(navController, "${NavRoutes.AddRecipient}")
                        }
                    }) {
                    Text(text = stringResource(R.string.next))
                }
            }
        }
    }
}


@Composable
fun NewTontinePreview(modifier: Modifier = Modifier){
    MoneyPalTheme {
        Surface(modifier = modifier.fillMaxSize(),
            color = if(isSystemInDarkTheme()) MaterialTheme.colors.surface else CustomGray) {
            Column(modifier = modifier
                .fillMaxSize()
                .padding(16.dp)) {
                Row(modifier = modifier
                    .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "ArrowBack")
                        }
                        Text(text = "New Tontine",
                            style = MaterialTheme.typography.h5,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Row(modifier = modifier.width(150.dp),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Surface(modifier = modifier
                            .size(50.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colors.primary,
                                shape = CircleShape
                            ),
                            shape = CircleShape) {
                            Column(modifier = modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "1", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            }
                        }
                        Surface(modifier = modifier
                            .size(50.dp)
                            .border(
                                width = 2.dp,
                                color = Color.Black,
                                shape = CircleShape
                            ),
                            shape = CircleShape) {
                            Column(modifier = modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "2", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            }
                        }
                    }
                }
                // content
                Column(modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)) {
                    OutlinedTextField(
                        modifier = modifier.fillMaxWidth(),
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Name")})
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        value = "",
                        onValueChange = {},

                        label = { Text(text = "Description")})
                    Row(modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), horizontalArrangement = Arrangement.End) {
                        Text(text = "x", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        Text(text = stringResource(R.string.caracters_total),
                            modifier = modifier.alpha(0.6f),
                            fontSize = 12.sp)
                    }
                    OutlinedTextField(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp),
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Amount")},
                        trailingIcon = {
                            Icon(painter = painterResource(R.drawable.ic_baseline_attach_money_24),
                                contentDescription = "money_icon")})
                    UserSetting(
                        modifier = modifier,
                        settingTitle = stringResource(R.string.notification),
                        checked = false,
                        onCheckedChange = {},
                        titleDescription = stringResource(R.string.automatic_notify_members),
                        checkTrackColor = MaterialTheme.colors.primary
                    )
                }
                Column(modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom) {
                    Button(modifier = modifier
                        .fillMaxWidth(),
                        onClick = {
                        }) {
                        Text(text = stringResource(R.string.next))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NewTontineLightPreview(){
    NewTontinePreview()
}
