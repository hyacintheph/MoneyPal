package com.example.moneypal.ui.home.tontine

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneypal.R
import com.example.moneypal.models.Tontine
import com.example.moneypal.ui.theme.MoneyPalTheme

@Composable
fun TontineCard(modifier: Modifier = Modifier, tontine: Tontine = Tontine()){
    MoneyPalTheme {
        Surface(color = Color.Black.copy(alpha = 0.8f), shape = MaterialTheme.shapes.small) {
            Column(modifier = modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = tontine.name, color = Color.White,
                    fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Row() {
                    Icon(painter = painterResource(R.drawable.ic_baseline_group_24), 
                        contentDescription = "Icon group", tint = Color.White)
                    Text(text = "${tontine.users.size} Mbers",
                        modifier = modifier.padding(start = 8.dp),
                        color = Color.White)
                    Text(text = "${stringResource(id = R.string.session)} 1/40",
                        modifier.padding(start = 24.dp),
                        color = Color.White,
                        fontSize = 14.sp)
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = stringResource(id = R.string.contribution),
                            color = Color.White, fontSize = 14.sp)
                        Row() {
                            LinearProgressIndicator(progress = 0.1f,
                                color = Color.White, modifier = modifier
                                    .height(20.dp).width(100.dp))
                            Text(text = "1/20", color = Color.White,
                                modifier = modifier.padding(start = 8.dp))
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = stringResource(id = R.string.to_pay),
                            color = Color.White, fontSize = 14.sp)
                        Text(text = "${tontine.totalAmount}", color = Color.White,
                            fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier.fillMaxWidth()) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = stringResource(id = R.string.deadline),
                            color = Color.White, fontSize = 14.sp)
                        Text(text = "12 June", color = Color.White)
                    }
                    Button(onClick = { /*TODO*/ },
                        modifier = modifier.width(150.dp),
                        shape = MaterialTheme.shapes.small.copy(bottomEnd = CornerSize(88))) {
                        Text(text = stringResource(id = R.string.pay))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TontineCardPreview(){
    TontineCard()
}