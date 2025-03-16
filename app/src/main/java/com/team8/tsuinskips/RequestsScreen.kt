package com.team8.tsuinskips

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.team8.tsuinskips.data.datasource.Status
import com.team8.tsuinskips.data.datasource.Type
import com.team8.tsuinskips.viewModel.RequestsViewModel
import kotlinx.coroutines.launch

@Composable
fun RequestsScreen(navController: NavHostController, vm: RequestsViewModel = viewModel()) {
    val navigationDrawerItems = listOf("Мои пропуски", "Список пропусков")
    val selectedItem = remember { mutableStateOf(navigationDrawerItems[0]) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val profile = vm.profile.collectAsState()
    val reqList = vm.requests.collectAsState()
    val scope = rememberCoroutineScope()
    vm.getUserProfile()
//    val cardList = (1..10).map {
//        RequestCard(
//            "болезнь",
//            "Полушкин Василий Игоревич",
//            "972303",
//            "подтверждено",
//            "28.05.2005",
//            "29.07.2222"
//        )
//    }


    ModalNavigationDrawer(drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.DarkGray,
                drawerContentColor = Color.LightGray,
                windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp, right = 0.dp, left = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(colorResource(R.color.greyMenu))
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.25f)
                ) {
                    IconButton(onClick = { scope.launch { drawerState.close() } },
                        modifier = Modifier.padding(10.dp, 20.dp),
                        content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Меню") })
                    Text(
                        text = "${profile.value.surname} ${profile.value.name} ${profile.value.patronymic} ",
                        fontFamily = FontFamily(Font(R.font.istokweb_regular)),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp, bottom = 10.dp)
                    )
                    Text(
                        text = profile.value.email,
                        fontFamily = FontFamily(Font(R.font.istokweb_regular)),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        fontSize = 20.sp,
                        lineHeight = 29.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(top = 60.dp)
                            .padding(start = 16.dp)
                    )
                    Button(
                        onClick = { TODO("ADD vm.logout(), so logoutUseCase to ViewModel") },
                        modifier = Modifier.align(Alignment.BottomEnd),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent, contentColor = Color.Transparent
                        )
                    ) {
                        Text(
                            text = "Выйти",
                            fontFamily = FontFamily(Font(R.font.istokweb_regular)),
                            color = colorResource(R.color.redText),
                            textAlign = TextAlign.Left,
                            fontSize = 20.sp,
                            lineHeight = 29.sp
                        )
                    }
                }
                navigationDrawerItems.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item, fontSize = 22.sp) },
                        selected = selectedItem.value == item,
                        onClick = {
                            scope.launch { drawerState.close() }
                            selectedItem.value = item
                            println(item)
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Transparent,
                            unselectedContainerColor = Color.Transparent,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                }
            }
        },
        content = {
            Row {
                IconButton(onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier.padding(20.dp, 40.dp),
                    content = { Icon(Icons.Filled.Menu, "Меню") })
            }
            if (selectedItem.value == "Мои пропуски") {
                vm.getRequestList()

                val cardList = reqList.value.requests.map {
                    RequestCard(
                        typeToString(it.type),
                        "${it.creator.surname} ${it.creator.name} ${it.creator.patronymic}",
                        it.creator.groupName,
                        statusToString(it.status),
                        it.startDate,
                        it.endDate
                    )
                }
                LastRequests(cardList)
            }
        })
}

@Composable
fun LastRequests(cardList: List<RequestCard>) {
    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .border(
                width = 1.dp, color = Color.Gray
            )
            .paint(
                painterResource(R.drawable.login_background)
            ), contentPadding = PaddingValues(4.dp)
    ) {
        items(cardList) { card ->
            ListItem(card = card)
        }
    }
}


@Composable
fun ListItem(card: RequestCard) {

    val borderColor = when (card.reason) {
        "болезнь" -> colorResource(R.color.greenIll)
        "семья" -> colorResource(R.color.orangeFamily)
        "командировка" -> colorResource(R.color.blueTrip)
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
                    width = 2.dp, color = borderColor, shape = RoundedCornerShape(16.dp)
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
                color = Color.Black,
                maxLines = 1
            )
            Text(
                text = card.groupName,
                modifier = Modifier
                    .padding(8.dp)
                    .background((colorResource(R.color.white))),
                fontSize = 20.sp,
                maxLines = 1
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
                            width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = card.status,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                }
                Spacer(
                    modifier = Modifier.padding(8.dp)
                )
                Box(
                    modifier = Modifier
                        .background(colorResource(R.color.white))
                        .fillMaxWidth()
                        .border(
                            width = 2.dp, color = borderColor, shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = card.reason,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        fontSize = 16.sp,
                        maxLines = 1
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
                            Color.Gray, RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)
                        )
                        .fillMaxWidth(0.5f)
                ) {
                    Text(
                        color = Color.White,
                        text = card.dateStart,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Center),
                        fontSize = 14.sp,
                        maxLines = 1
                    )
                }
                Spacer(
                    modifier = Modifier.padding(0.5.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .background(
                            Color.Gray, RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)
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

fun typeToString(reason: Type): String {
    if (reason == Type.FAMILY) {
        return "семья"
    }
    if (reason == Type.SICK) {
        return "болезнь"
    }
    return "командировка"
}

fun statusToString(reason: Status): String {
    if (reason == Status.IN_QUEUE) {
        return "в очереди"
    }
    if (reason == Status.APPROVED) {
        return "подтверждено"
    }
    return "отклонено"
}

data class RequestCard(
    val reason: String,
    val SNP: String,
    val groupName: String,
    val status: String,
    val dateStart: String,
    val dateEnd: String
)
