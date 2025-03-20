package com.team8.tsuinskips

import android.net.Uri
import android.util.Range
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.LinkAnnotation
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
import com.team8.tsuinskips.presentation.models.AttachFileItem
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
    val selectedImages = viewModel.selectedImages.collectAsState()

    var showDateRangePicker by remember { mutableStateOf(false) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                viewModel.attachNewConfirmationFile(AttachFileItem(uri.lastPathSegment.toString(), uri))
            }
        }
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(painterResource(R.drawable.login_background), contentScale = ContentScale.Crop)
            .padding(16.dp)
            .padding(top = 64.dp)
            .verticalScroll(scrollState)
            .then(modifier)
    ) {
//        IconButton(
//            onClick = { navController.popBackStack() },
//            modifier = Modifier
//                .paint(
//                    painterResource(R.drawable.arrow_back), contentScale = ContentScale.Fit
//                )
//                .size(33.dp)
//        ){}

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            onClick = { showDateRangePicker = true },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                requestRange?.let {
                    "${it.lower.formated()}  -  ${it.upper.formated()}"
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
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            MissRequestType.entries.forEach {
                FilterChip(
                    modifier = Modifier
                        //.padding(horizontal = 4.dp)
                        .weight(1f)
                        .fillMaxWidth(0.31f)
                        .fillMaxWidth(),
                    selected = (it == selectedMissRequestType),
                    onClick = { selectedMissRequestType = it },
                    label = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(it.toRuString())
                        }
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        labelColor = Color(0xFF383638),
                        selectedContainerColor = Color(0xFF383638),
                        selectedLabelColor = Color.White
                    ),
                )
            }
        }

        selectedImages.value.forEachIndexed{ index, file ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(file.uri),
                        contentDescription = "Request image",
                        modifier = Modifier
                            .fillMaxSize(0.3f)
                            .clip(RoundedCornerShape(8.dp))
                            .aspectRatio(1.3f),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Row {
                            Text(
                                stringResource(R.string.filename)
                            )
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color.Black,
                                modifier = Modifier
                                    .clickable { viewModel.detachConfirmationFile(index) }
                            )
                        }
                        BasicTextField(
                            value = file.name,
                            onValueChange = { newName ->
                                viewModel.changeConfirmationFileName(
                                    index, newName
                                )
                            },
                            //label = { stringResource(R.string.filename) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                        )
                    }
                }
            }
        }
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { filePickerLauncher.launch("*/*") },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(stringResource(R.string.pick_file))
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