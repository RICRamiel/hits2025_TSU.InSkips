package com.team8.tsuinskips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RequestsScreen(navController: NavHostController) {

    val cardList =
        (1..10).map { Card("болезнь", "Полушкин Василий Игоревич", "972303", "подтверждено","28.05.2005","29.07.2222") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .border(
                width = 1.dp,
                color = Color.Gray
            )
            .paint(
                painterResource(R.drawable.login_background)
            ),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(cardList) { card ->
            ListItem(card = card)
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp, 40.dp)
                .size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "",
                modifier = Modifier.size(48.dp)
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text = stringResource(R.string.last_requests),
            modifier = Modifier
                .padding(top = 56.dp)
                .padding(end = 20.dp)
                .background((colorResource(R.color.white))),
            color = Color.Black,
            fontSize = 20.sp
        )
    }

}


@Composable
fun ListItem(card: Card) {

    val borderColor = when (card.reason) {
        "болезнь" -> Color.Green
        "семья" -> Color.Yellow
        "командировка" -> Color.Blue
        else -> Color.Gray
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(6.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .background(colorResource(R.color.white))
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            colors = CardColors(Color.White, Color.Gray, Color.Gray, Color.Gray)
        ) {
            Text(
                text = card.SNP,
                modifier = Modifier
                    .padding(4.dp)
                    .background((colorResource(R.color.white))),
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = card.groupName,
                modifier = Modifier
                    .padding(8.dp)
                    .background((colorResource(R.color.white))),
                fontSize = 20.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background((colorResource(R.color.white))),
            ) {
                Box(
                    modifier = Modifier
                        .background(colorResource(R.color.white))
                        .fillMaxWidth(0.47f)
                        .border(
                            width = 2.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = card.status,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        fontSize = 20.sp
                    )
                }
                Spacer(
                    modifier = Modifier
                        .padding(8.dp)
                )
                Box(
                    modifier = Modifier
                        .background(colorResource(R.color.white))
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = card.reason,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        fontSize = 20.sp
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background((colorResource(R.color.white))),
            ) {
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .background(
                            Color.Gray,
                            RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                        )
                        .fillMaxWidth(0.5f)
                ) {
                    Text(
                        color = Color.White,
                        text = card.dateStart,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        fontSize = 14.sp
                    )
                }
                Spacer(
                    modifier = Modifier
                        .padding(0.5.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .background(
                            Color.Gray,
                            RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
                        )
                        .fillMaxWidth()
                ) {
                    Text(
                        color = Color.White,
                        text = card.dateEnd,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        fontSize = 14.sp
                    )
                }
            }

        }
    }
}

data class Card(
    val reason: String,
    val SNP: String,
    val groupName: String,
    val status: String,
    val dateStart: String,
    val dateEnd: String
)
