package com.team8.tsuinskips.ui_elements

import android.util.Range
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.team8.tsuinskips.R
import com.team8.tsuinskips.formated
import java.time.LocalDate

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    onAnyChange: (surname: String?, group: String?, subgroup: String?, range: Range<LocalDate>?) -> Unit
) {
    var searchRange: Range<LocalDate>? by remember { mutableStateOf(null) }
    var searchSurname: String by remember { mutableStateOf("") }
    var searchGroupName: String by remember { mutableStateOf("") }
    var searchSubgroupName: String by remember { mutableStateOf("") }
    var showDateRangePicker by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    fun onAnyChange() {
        onAnyChange(
            if (searchSurname == "") null else searchSurname,
            if (searchGroupName == "") null else searchGroupName,
            if (searchSubgroupName == "") null else searchSubgroupName,
            searchRange
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .then(modifier)
    ) {
        OutlinedTextField(
            value = searchSurname,
            onValueChange = { searchSurname = it; onAnyChange() },
            label = { Text(stringResource(R.string.surname)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (searchSurname.isNotEmpty()) {
                    IconButton(onClick = { searchSurname = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear text"
                        )
                    }
                }
            }
        )
        OutlinedTextField(
            value = searchGroupName,
            onValueChange = { searchGroupName = it; onAnyChange() },
            label = { Text(stringResource(R.string.group_name)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (searchGroupName.isNotEmpty()) {
                    IconButton(onClick = { searchGroupName = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear text"
                        )
                    }
                }
            }
        )
        OutlinedTextField(
            value = searchSubgroupName,
            onValueChange = { searchSubgroupName = it; onAnyChange() },
            label = { Text(stringResource(R.string.subroup_name)) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (searchSubgroupName.isNotEmpty()) {
                    IconButton(onClick = { searchSubgroupName = "" }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear text"
                        )
                    }
                }
            }
        )

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            onClick = { showDateRangePicker = true },
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 7.dp),
                text = searchRange?.let {
                    "${it.lower.formated()}  -  ${it.upper.formated()}"
                } ?: stringResource(R.string.pick_dates),
                fontSize = 16.sp
            )
        }
        if (showDateRangePicker) {
            CalendarDialog(
                state = rememberSheetState(
                    visible = true,
                    onCloseRequest = {
                        showDateRangePicker = false
                    }),
                config = CalendarConfig(
                    yearSelection = true,
                    monthSelection = true,
                ),
                selection = CalendarSelection.Period(
                    selectedRange = searchRange
                ) { startDate, endDate ->
                    searchRange = Range(startDate, endDate)
                    onAnyChange()
                },
            )
        }
    }
}