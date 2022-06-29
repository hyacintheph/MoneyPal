package com.example.moneypal.ui.account

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.moneypal.R
import com.example.moneypal.UserAccountActivity
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.viewmodels.UserAccountViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.request.ImageRequest
import com.example.moneypal.MainActivity
import com.example.moneypal.WelcomeUserActivity
import com.example.moneypal.models.Status
import com.example.moneypal.utils.WelcomeTitle
import com.example.moneypal.utils.convertUriToBitmap
import com.example.moneypal.utils.findActivity
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.RuntimeException

@Composable
fun WelcomeUser(modifier: Modifier = Modifier,
                viewModel: UserAccountViewModel = hiltViewModel()){
    var userName by rememberSaveable() {
        mutableStateOf("")
    }
    val activity = LocalContext.current.findActivity() as WelcomeUserActivity

    var bitmap by rememberSaveable() { mutableStateOf<Bitmap?>(null) }
    var imageUri by rememberSaveable() { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        bitmap = convertUriToBitmap(imageUri, activity)
    }
    val coroutineScope = rememberCoroutineScope()
    val isProfileUpdated: Boolean by viewModel.isProfileUpdated.observeAsState(false)
    MoneyPalTheme {
        Scaffold {
            Column(modifier = modifier
                .padding(16.dp) ){
                WelcomeTitle(modifier = modifier)

                Text(text = stringResource(R.string.fill_user_infos),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center)
                    // image to select from gallery
                    if(bitmap != null){
                        Image(
                            contentDescription = stringResource(id = R.string.image_placeholder),
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp)
                                .size(100.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colors.primary,
                                    shape = MaterialTheme.shapes.small
                                )
                                .clip(MaterialTheme.shapes.small),
                            bitmap = bitmap!!.asImageBitmap()
                        )
                    }else{
                        Image(
                            contentDescription = stringResource(id = R.string.image_placeholder),
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp)
                                .size(100.dp)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colors.primary,
                                    shape = MaterialTheme.shapes.small
                                )
                                .clip(MaterialTheme.shapes.small),
                            painter = painterResource(id = R.drawable.placeholder_image)
                        )
                    }

                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.select_image)),
                    onClick = {
                        launcher.launch("image/*")},
                    modifier = modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                )
                OutlinedTextField(
                    value = userName,
                    onValueChange = {userName = it},
                    label = {
                        Text(text = stringResource(id = R.string.your_name_placeholder))
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                // update image and name
                Button(onClick = {
                    // upload image to firestorage
                    if(userName.isNotBlank() && imageUri != null){
                        // coroutine scope
                        coroutineScope.launch {
                            bitmap?.let {
                                viewModel.uploadImage(it, userName, false)
                            }
                        }
                    }else{
                        Toast.makeText(activity, activity.getString(R.string.enter_update_data),
                        Toast.LENGTH_LONG).show()
                    }

                },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)) {
                    Text(text = stringResource(id = R.string.confirm))
                }
                // loading composable
                if(!isProfileUpdated){
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 24.dp, bottom = 24.dp)) {
                        Text(text = stringResource(R.string.updating_profile))
                        LinearProgressIndicator(modifier = modifier.padding(top = 8.dp))
                    }
                }else{
                    try {
                        bitmap = null
                        activity.startActivity(Intent(activity, MainActivity::class.java))
                    }catch (e: RuntimeException){
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomePreview(modifier: Modifier = Modifier){
    MoneyPalTheme {
        Scaffold {
            Column(modifier = modifier
                .padding(16.dp) ){

                WelcomeTitle(modifier = modifier)

                Text(text = stringResource(R.string.fill_user_infos),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    textAlign = TextAlign.Center)
                // image to select from gallery
                Image(
                    painter = painterResource(id = R.drawable.placeholder_image),
                    contentDescription = stringResource(id = R.string.image_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 16.dp)
                        .size(100.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .clip(MaterialTheme.shapes.small)
                )

                ClickableText(
                    text = AnnotatedString(stringResource(id = R.string.select_image)),
                    onClick = {},
                    modifier = modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.primary
                    )
                )
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(text = stringResource(id = R.string.your_name_placeholder))
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                // update image and name
                Button(onClick = {},
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)) {
                    Text(text = stringResource(id = R.string.confirm))
                }

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun WelcomeUserLightPreview(){
    WelcomePreview()
}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun WelcomeUserDarkPreview(){
    WelcomePreview()
}