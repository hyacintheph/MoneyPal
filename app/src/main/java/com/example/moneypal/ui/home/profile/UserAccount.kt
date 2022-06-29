package com.example.moneypal.ui.home.profile

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.moneypal.LoginActivity
import com.example.moneypal.MainActivity
import com.example.moneypal.R
import com.example.moneypal.ui.theme.CustomGray
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.utils.*
import com.example.moneypal.viewmodels.UserAccountViewModel
import com.example.moneypal.viewmodels.UserViewModel
import com.google.firebase.storage.FirebaseStorage

@Composable
fun UserAccount(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: UserViewModel = hiltViewModel(),
    userAccountViewModel: UserAccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current.applicationContext
    val activity: MainActivity = LocalContext.current as MainActivity
    var imageUri by rememberSaveable() { mutableStateOf<Uri?>(null) }
    var userName by rememberSaveable() { mutableStateOf("")}
    var phoneNumber by rememberSaveable() { mutableStateOf("")}
    val isSuccessfullyUpdated by userAccountViewModel.isProfileUpdated.observeAsState(false)
    // find launch to intercept viewmodel state on composable
    var isLaunched by remember() {
        mutableStateOf(false)
    }

    // select photo from gallery
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        if (imageUri != null){
            userAccountViewModel.uploadImage(bitmap = convertUriToBitmap(imageUri, context)!!,
                isUpdated = true)
            isLaunched = true
        }
    }
    var notificationState by rememberSaveable() { mutableStateOf(false)}
    var darkMode by rememberSaveable() { mutableStateOf(false)}
    val authUser = viewModel.authUser.observeAsState()
    MoneyPalTheme() {
        Surface(modifier = modifier.fillMaxSize(),
            color = if(isSystemInDarkTheme()) MaterialTheme.colors.surface else CustomGray) {
            // load user data
            LaunchedEffect(authUser){
                viewModel.getAuthUser()
                authUser.value!!.let {
                    phoneNumber = it.phoneNumber!!
                    userName = it.name!!
                    notificationState = it.notification
                    darkMode = it.darkMode
                    // get uri for image
                    val reference = FirebaseStorage.getInstance()
                        .reference.child("users/${it.avatar}")
                    reference.downloadUrl.addOnSuccessListener { uri ->
                        imageUri = uri
                    }.addOnFailureListener{
                        //userUri = null
                    }

                }
            }
            if(isSuccessfullyUpdated && isLaunched){
                Toast.makeText(activity, stringResource(R.string.successfully_update_profile), Toast.LENGTH_LONG).show()
            }
            Column(modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())) {
                Text(
                    text = stringResource(R.string.profile),
                    modifier = modifier.padding(top = 16.dp, bottom = 24.dp),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                    )

                //rememberAsyncImagePainter(imageUri)
                Box(modifier = modifier
                    .width(100.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Image(
                        painter = rememberAsyncImagePainter(
                                    model = ImageRequest.Builder(LocalContext.current).data(imageUri)
                                        .placeholder(R.drawable.ic_baseline_image_24)
                                        .error(R.drawable.ic_baseline_error_24)
                                        .fallback(R.drawable.ic_baseline_broken_image_24)
                                        .build()
                                ),
                        contentDescription = stringResource(id = R.string.image_placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .border(
                                width = 2.dp, color = MaterialTheme.colors.primary,
                                shape = CircleShape
                            )
                            .size(100.dp)
                            .clip(CircleShape)

                    )
                    IconButton(onClick = {
                                         launcher.launch("image/*")
                    },
                        modifier  = modifier
                            .padding(top = 55.dp, start = 60.dp)
                            .clip(CircleShape)
                            ) {
                        Icon(imageVector = Icons.Filled.Edit,
                            modifier = modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colors.primary)
                                .padding(4.dp),
                            tint = Color.White,
                            contentDescription = "IconUpdateImageProfile")
                    }
                }
                if(!isSuccessfullyUpdated && isLaunched){
                    LinearProgressIndicator(modifier = modifier.width(100.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp))
                }
                // user info
                Column(modifier = modifier.padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(text = "Personal info",
                            style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
                        ClickableText(
                            text = AnnotatedString(text = stringResource(R.string.edit)),
                            onClick = { SingleRouteNavigation(navController,
                                "${NavRoutes.EditAccount}")},
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.primary
                            )
                        )
                    }
                    Surface(
                        elevation = 4.dp,
                        shape = MaterialTheme.shapes.small,
                        modifier = modifier.padding(top = 24.dp)) {
                        Column(modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            Row(modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = stringResource(R.string.your_name_placeholder))
                                Text(text = userName, fontWeight = FontWeight.Bold)
                            }
                            Row(modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = stringResource(R.string.phone_placeholder))
                                Text(text = phoneNumber, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    // notification setting
                    UserSetting(
                        modifier = modifier,
                        settingTitle = stringResource(R.string.notification),
                        checked = notificationState,
                        onCheckedChange = {
                            notificationState = it
                            if(notificationState){
                                viewModel.setNotification()
                            }
                        },
                        titleDescription = stringResource(R.string.notify_me),
                        checkTrackColor = MaterialTheme.colors.primary
                    )
                    // dark setting
                    UserSetting(
                        modifier = modifier,
                        settingTitle = stringResource(R.string.dark_mode),
                        checked = darkMode,
                        onCheckedChange = {
                            darkMode = it
                        },
                        titleDescription = stringResource(R.string.change_mode),
                        checkTrackColor = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun UserAccountPreview(
    modifier: Modifier = Modifier
) {
    MoneyPalTheme() {
        Surface(modifier = modifier.fillMaxSize(),
            color = if(isSystemInDarkTheme()) MaterialTheme.colors.surface else CustomGray) {

            Column(modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())) {
                Text(
                    text = stringResource(R.string.profile),
                    modifier = modifier.padding(top = 16.dp, bottom = 24.dp),
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )

                //rememberAsyncImagePainter(imageUri)
                Box(modifier = modifier
                    .width(100.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Image(
                        painter = painterResource(R.drawable.placeholder_image),
                        contentDescription = stringResource(id = R.string.image_placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .border(
                                width = 2.dp, color = MaterialTheme.colors.primary,
                                shape = CircleShape
                            )
                            .size(100.dp)
                            .clip(CircleShape))
                    IconButton(onClick = {},
                        modifier  = modifier
                            .padding(top = 55.dp, start = 60.dp)
                            .clip(CircleShape)) {
                        Icon(imageVector = Icons.Filled.Edit,
                            modifier = modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colors.primary)
                                .padding(4.dp),
                            tint = Color.White,
                            contentDescription = "IconUpdateImageProfile")
                    }
                }
                // user info
                Column(modifier = modifier.padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(text = "Personal info",
                            style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
                        ClickableText(
                            text = AnnotatedString(text = stringResource(R.string.edit)),
                            onClick = {},
                            style = TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colors.primary
                            )
                        )
                    }
                    Surface(
                        elevation = 4.dp,
                        shape = MaterialTheme.shapes.small,
                        modifier = modifier.padding(top = 24.dp)) {
                        Column(modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(24.dp)) {
                            Row(modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = stringResource(R.string.your_name_placeholder))
                                Text(text = "", fontWeight = FontWeight.Bold)
                            }
                            Row(modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = stringResource(R.string.phone_placeholder))
                                Text(text = "", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    // notification setting
                    UserSetting(
                        modifier = modifier,
                        settingTitle = stringResource(R.string.notification),
                        checked = false,
                        onCheckedChange = {},
                        titleDescription = stringResource(R.string.notify_me),
                        checkTrackColor = MaterialTheme.colors.primary
                    )
                    // dark setting
                    UserSetting(
                        modifier = modifier,
                        settingTitle = stringResource(R.string.dark_mode),
                        checked = false,
                        onCheckedChange = {},
                        titleDescription = stringResource(R.string.change_mode),
                        checkTrackColor = Color.Black
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UserAccountLightPreview(){
    UserAccountPreview()
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun UserAccountDarkPreview(){
    UserAccountPreview()
}