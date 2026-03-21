package com.team8.tsuinskips

import android.util.Range
import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.team8.tsuinskips.data.datasource.Status
import com.team8.tsuinskips.data.datasource.MissRequestType
import com.team8.tsuinskips.ui_elements.FilterBottomSheet
import com.team8.tsuinskips.viewModel.RequestsViewModel
import kotlinx.coroutines.coroutineScope
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import java.util.UUID

@Composable
fun RequestsScreen(
    navController: NavHostController,
    vm: RequestsViewModel = viewModel()
) {
    val navigationDrawerItems = listOf("Мои пропуски", "Список пропусков", "Добавить пропуск")
    val selectedItem = remember { mutableStateOf(navigationDrawerItems[0]) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val profile = vm.profile.collectAsState()
    val reqList = vm.requests.collectAsState()
    val scope = rememberCoroutineScope()
    vm.getUserProfile()

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
                    IconButton(
                        modifier = Modifier.padding(10.dp, 20.dp),
                        onClick = { scope.launch { drawerState.close() } },
                        content = { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Меню") })
                    Text(
                        text = vm.getSNP(),
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
                        onClick = { vm.logout() },
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
            Row(
                modifier = Modifier
            ) {
                IconButton(onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier.padding(20.dp, 40.dp),
                    content = { Icon(Icons.Filled.Menu, "Меню") })
            }
            if (selectedItem.value == "Мои пропуски") {
                vm.getRequestList()

                val cardList = reqList.value.requests.map {
                    RequestCard(
                        it.id,
                        typeToString(it.missRequestType),
                        "${it.creator.surname} ${it.creator.name} ${it.creator.patronymic}",
                        it.creator.groupName ?: "",
                        statusToString(it.status),
                        it.startDate.toString(),
                        it.endDate.toString()
                    )
                }
                LastRequests(cardList, vm)
            }
            if (selectedItem.value == "Список пропусков") {
                ListSkips(vm)
            }
            if (selectedItem.value == "Добавить пропуск") {
                NewRequestScreen(navController = navController)
            }
        })
}

@Composable
fun ListSkips(vm: RequestsViewModel) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(1) }
    val endMonth = remember { currentMonth.plusMonths(1) }
    val daysOfWeek = remember { daysOfWeek() }
    val sheetState = remember { mutableStateOf(false) }
    val rememberGroup = remember { mutableStateOf<String?>(null) }
    val rememberSubGroup = remember { mutableStateOf<List<String>?>(null) }
    val rememberSurname = remember { mutableStateOf<String?>(null) }
    if (sheetState.value) {
        ModalBottomSheet(sheetState, vm, rememberGroup, rememberSurname, rememberSubGroup.value)
    }
    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )
    LazyColumn(modifier = Modifier.padding(top = 70.dp)) {
        item {
            Box {
                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = stringResource(R.string.skips_list),
                        modifier = Modifier
                            .padding(end = 20.dp)
                            .background((colorResource(R.color.white))),
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd
                ) {
                    IconButton(onClick = { sheetState.value = true },
                        modifier = Modifier
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 56.dp)
                            .size(24.dp),
                        content = { Icon(painterResource(R.drawable.filter), "Фильтр") })
                }
            }
        }
        item {

            val bottomSheetState = remember { mutableStateOf(false) }
            var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
            val reqList = vm.filteredRequests.collectAsState()
            val temp = reqList.value
            HorizontalCalendar(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp),
                state = state,
                dayContent = { day ->
                    var color = Color.Gray
                    var fontWeight = FontWeight.Normal

                    for (i in 0..<temp.requests.size) {
                        val dayStart = temp.requests[i].startDate
                        val dayEnd = temp.requests[i].endDate
                        if (day.date in dayStart..dayEnd) {
                            color = Color.Black
                            fontWeight = FontWeight.Bold
                        }
                    }
                    Day(
                        day,
                        color,
                        rememberGroup,
                        rememberSurname,
                        rememberSubGroup.value,
                        fontWeight,
                        bottomSheetState,
                        selectedDate,
                        vm,
                        isSelected = selectedDate == day.date
                    ) { day ->
                        selectedDate = if (selectedDate == day.date) null else day.date
                    }
                },
                monthHeader = { month ->
                    val months = month.yearMonth
                    val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                    MonthHeader(daysOfWeek = daysOfWeek, months = months)
                }
            )
            if (bottomSheetState.value) {
                val cardList = reqList.value.requests.map {
                    RequestCard(
                        it.id,
                        typeToString(it.missRequestType),
                        "${it.creator.surname} ${it.creator.name} ${it.creator.patronymic}",
                        it.creator.groupName ?: "",
                        statusToString(it.status),
                        it.startDate.toString(),
                        it.endDate.toString()
                    )
                }
                BottomRequest(cardList, vm)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheet(
    sheetState: MutableState<Boolean>,
    vm: RequestsViewModel,
    groupNameRemember: MutableState<String?>,
    surnameRemember: MutableState<String?>,
    subgroupNameRemember: List<String>?
) {
    val state = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = {
            sheetState.value = false
        },
        sheetState = state
    ) {
        FilterBottomSheet(
            modifier = Modifier.padding(horizontal = 16.dp),
            onAnyChange = { surname: String?, group: String?, subgroup: String?, range: Range<LocalDate>? ->
                vm.getFilteredRequestList(
                    if (group.isNullOrEmpty()) null else group,
                    if (subgroup.isNullOrEmpty()) null else listOf(subgroup),
                    if (surname.isNullOrEmpty()) null else surname,
                    range?.lower,
                    range?.upper
                );groupNameRemember.value = group;surnameRemember.value =
                surname;subgroupNameRemember?.plus(subgroup)
            }
        )
    }
}

@Composable
fun Day(
    day: CalendarDay,
    color: Color,
    groupNameRemember: MutableState<String?>,
    surnameRemember: MutableState<String?>,
    subgroupNameRemember: List<String>?,
    fontWeight: FontWeight,
    bottomSheetState: MutableState<Boolean>,
    selectedDate: LocalDate?,
    vm: RequestsViewModel,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit
) {

    if (isSelected) {
        vm.getFilteredRequestList(
            groupNameRemember.value,
            subgroupNameRemember,
            surnameRemember.value,
            selectedDate,
            selectedDate
        )
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = {
                    onClick(day); bottomSheetState.value = true;
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            fontWeight = fontWeight,
            color = color
        )
    }
}

@Composable
fun BottomRequest(cardList: List<RequestCard>, vm: RequestsViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp, color = Color.Gray
            )
            .paint(
                painterResource(R.drawable.login_background)
            ),
    ) {
        cardList.forEach { card ->
            ListItem(card = card, vm)
        }
    }
}

@Composable
fun MonthHeader(daysOfWeek: List<DayOfWeek>, months: YearMonth) {
    Column {
        Text(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp),
            text = months.month.toString(),
            fontSize = 20.sp,
            textAlign = TextAlign.Left
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                )
            }
        }
    }
}

@Composable
fun LastRequests(cardList: List<RequestCard>, vm: RequestsViewModel) {
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
            ListItem(card = card, vm)
        }
    }
}


@Composable
fun ListItem(card: RequestCard, vm: RequestsViewModel) {

    val id = remember { mutableStateOf("") }
    val sheetState = remember { mutableStateOf(false) }
    if (id.value != "") {
        ProlongBottomSheet(sheetState, vm, card.dateEnd, id)
    }

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
                .clickable { id.value = card.id;sheetState.value = true }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProlongBottomSheet(
    sheetState: MutableState<Boolean>,
    vm: RequestsViewModel,
    newDate: String,
    id: MutableState<String>
) {
    var date by remember { mutableStateOf(LocalDate.now()) }
    val newDate = remember { mutableStateOf(newDate) }

    if (sheetState.value) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = date.toEpochDay() * 86400000
        )
        newDate.value = date.toString()
        DatePickerDialog(onDismissRequest = { sheetState.value = false }, confirmButton = {
            Button(onClick = {
                datePickerState.selectedDateMillis?.let { millis ->
                    newDate.value = LocalDate.ofEpochDay(millis / 86400000).toString()
                }
                sheetState.value = false
                vm.prolong(id.value,newDate.value)
            }) {
                Text("OK")
            }
        }) {
            DatePicker(state = datePickerState)
        }
    }
}

fun typeToString(reason: MissRequestType): String {
    if (reason == MissRequestType.FAMILY) {
        return "семья"
    }
    if (reason == MissRequestType.SICK) {
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
    val id: String,
    val reason: String,
    val SNP: String,
    val groupName: String,
    val status: String,
    val dateStart: String,
    val dateEnd: String
)
