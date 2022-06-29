package com.example.moneypal.utils

import android.widget.ToggleButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.moneypal.R

@Composable
fun CustomAlertDialog(
    openDialog: MutableState<Boolean>,
    dialogHeight: Dp,
    dialogWidth: Dp,
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit
){
    if(openDialog.value){
        Dialog(onDismissRequest = onDismissRequest) {
            Box(modifier = modifier.size(dialogWidth,dialogHeight)){
                content
            }
        }
    }
}

@Composable
fun WelcomeTitle(modifier: Modifier){
    Text(text = stringResource(R.string.welcome),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h5,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    )
}

@Composable
fun UserSetting(
    modifier: Modifier,
    settingTitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    titleDescription: String,
    checkTrackColor: Color
){
    Surface(
        elevation = 4.dp,
        shape = MaterialTheme.shapes.small
    ) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()) {
                Text(text = settingTitle, fontWeight = FontWeight.Bold)
                Switch(
                    checked = checked,
                    onCheckedChange = onCheckedChange,
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = checkTrackColor
                    )
                )
            }
            Text(text = titleDescription)
        }

    }
}