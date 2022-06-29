package com.example.moneypal.utils

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.moneypal.R
import com.example.moneypal.models.UserData
import com.example.moneypal.ui.theme.MoneyPalTheme
import com.example.moneypal.viewmodels.UserViewModel

@Composable
fun UserCard(
    modifier: Modifier = Modifier,
    userData: UserData = UserData(),
    type: String = "",
    userViewModel: UserViewModel = hiltViewModel()
) {
    MoneyPalTheme {
        Row(modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current).data(userData.avatar)
                            .placeholder(R.drawable.ic_baseline_image_24)
                            .error(R.drawable.ic_baseline_error_24)
                            .fallback(R.drawable.ic_baseline_broken_image_24)
                            .build()
                    ),
                    contentDescription = stringResource(id = R.string.image_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .border(
                            width = 1.dp, color = MaterialTheme.colors.primary,
                            shape = CircleShape
                        )
                        .size(60.dp)
                        .clip(CircleShape)

                )
                Column(modifier = modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp),
                    verticalArrangement = Arrangement.Center) {
                    Text(text = userData.name!! )
                    Row() {
                        Icon(modifier = modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.placeholder_image),
                            contentDescription = "type")
                        Text(text = userData.phoneNumber!!)
                    }
                }
            }
            when(type){
                "delete" -> IconButton(onClick = {userViewModel.deleteSelectedUser(userData)}) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "delete")
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserCardPreview() {
    UserCard()
}