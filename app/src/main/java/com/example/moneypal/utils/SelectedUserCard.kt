package com.example.moneypal.utils

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.moneypal.R

@Composable
fun SelectedUserCard(modifier: Modifier = Modifier,
                     imageUri: Uri? = null,
                     userName: String = ""){
    Column(modifier = modifier.size(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = modifier
            .width(70.dp)
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
                    .size(50.dp)
                    .clip(CircleShape)

            )
            IconButton(onClick = {},
                modifier  = modifier
                    .padding(top = 25.dp, start = 35.dp)
                    .size(20.dp)
                    .clip(CircleShape)
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_baseline_cancel_24),
                    modifier = modifier
                        .clip(CircleShape)
                        .background(Color.Red)
                        .padding(4.dp),
                    tint = Color.White,
                    contentDescription = "IconUpdateImageProfile")
            }
        }
        Text(text = if(userName.length > 7) userName.substring(0..4)+".." else userName,
            color = Color.Black.copy(alpha = 0.7f),
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(horizontal = 4.dp).width(70.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedUserCardPreview(modifier: Modifier = Modifier){
    SelectedUserCard()
}