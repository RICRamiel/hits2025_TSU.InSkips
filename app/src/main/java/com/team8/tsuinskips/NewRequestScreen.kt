package com.team8.tsuinskips

import android.net.Uri
import android.util.Range
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.team8.tsuinskips.data.datasource.MissRequestType
import com.team8.tsuinskips.presentation.mappers.toRuString
import com.team8.tsuinskips.viewModel.NewRequestViewModel
import java.time.format.DateTimeFormatter

@Composable
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
fun NewRequestScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewRequestViewModel = viewModel()
) {
    var requestRange: Range<LocalDate>? by remember { mutableStateOf(null) }
    var selectedMissRequestType: MissRequestType? by remember { mutableStateOf(null) }
    val selectedImagesUris = viewModel.selectedImagesUris.collectAsState()

    var showDateRangePicker by remember { mutableStateOf(false) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.attachNewConfirmationFile(uri)
            }
        }
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { showDateRangePicker = true },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                requestRange?.let {
                    "${it.lower.formated()} - ${it.upper.formated()}"
                } ?: stringResource(R.string.pick_dates)
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
                    selectedRange = requestRange
                ) { startDate, endDate ->
                    requestRange = Range(startDate, endDate)
                },
            )
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            MissRequestType.entries.forEach {
                FilterChip(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    selected = (it == selectedMissRequestType),
                    onClick = { selectedMissRequestType = it },
                    label = { Text(it.toRuString()) },
                    colors = FilterChipDefaults.filterChipColors(
                        labelColor = Color(0xFF383638),
                        selectedContainerColor = Color(0xFF383638),
                        selectedLabelColor = Color.White
                    ),
                )
            }
        }

        val columnsCount = 3
        for (row in 0..(selectedImagesUris.value.size + 1) / columnsCount) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for (column in 0..<columnsCount) {
                    val index = row * columnsCount + column

                    if (index < selectedImagesUris.value.size) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .aspectRatio(1f),
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(selectedImagesUris.value[index]),
                                contentDescription = "Request image",
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                            IconButton(
                                onClick = {viewModel.detachConfirmationFile(index)},
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                            ) {
                                Icon(
                                    modifier = Modifier.background(
                                        color = Color.White,
                                        shape = CircleShape
                                    ),
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                    else if (index == selectedImagesUris.value.size) {
                        OutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .aspectRatio(1f),
                            onClick = { filePickerLauncher.launch("*/*") },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(stringResource(R.string.pick_file))
                        }
                    }
                    else{
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = {
                if (selectedMissRequestType?.let {
                        requestRange?.let { it1 ->
                            viewModel.createNewMissRequest(
                                from = it1.lower,
                                to = it1.upper,
                                type = it
                            )
                        }
                    } == true){
                    navController.navigateUp()
                }
                      },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(R.string.add))
        }
    }
}

fun LocalDate.formated(): String {
    val outputFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    return this.format(outputFormat)
}