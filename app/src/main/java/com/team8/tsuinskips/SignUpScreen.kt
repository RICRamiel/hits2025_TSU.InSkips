package com.team8.tsuinskips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.team8.tsuinskips.domain.UserLogin
import com.team8.tsuinskips.domain.UserRegister
import com.team8.tsuinskips.domain.useCase.RegisterUseCase
import com.team8.tsuinskips.viewModel.RegisterViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavHostController,vm: RegisterViewModel = viewModel()) {
    val email = remember { mutableStateOf("") }
    val passwd = remember { mutableStateOf("") }
    val SNP = remember { mutableStateOf("") }
    val birthdateStr = remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    Box(
        Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.login_background), contentScale = ContentScale.Crop
            ), contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(29.dp, 38.dp)
                .paint(
                    painterResource(R.drawable.arrow_back), contentScale = ContentScale.Fit
                )
                .size(33.dp)
        ) {}
        Column {
            Text(
                text = stringResource(R.string.register),
                fontFamily = FontFamily(Font(R.font.istokweb_bold)),
                fontSize = 36.sp,
                lineHeight = 52.sp,
                color = colorResource(R.color.grey),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                value = email.value,
                onValueChange = { newText -> email.value = newText },
                placeholder = { Text(stringResource(R.string.email)) },
                maxLines = 1,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                value = SNP.value,
                onValueChange = { newText -> SNP.value = newText },
                placeholder = { Text(stringResource(R.string.SNP)) },
                maxLines = 1,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            OutlinedTextField(
                value = birthdateStr.value,
                onValueChange = { newText -> birthdateStr.value },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                maxLines = 1,
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDatePicker = true
                        },
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .paint(
                                painterResource(R.drawable.calendar),
                                contentScale = ContentScale.Fit
                            )
                            .size(22.dp)
                    ) {}
                },
                placeholder = { Text(stringResource(R.string.birth_date)) },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 16.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                value = passwd.value,
                onValueChange = { newText -> passwd.value = newText },
                placeholder = { Text(stringResource(R.string.password)) },
                maxLines = 1,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 135.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            SmallButton(
                func = { vm.register(UserRegister(SNP.value.split("\\s".toRegex())[1],email.value,SNP.value.split("\\s".toRegex())[0],SNP.value.split("\\s".toRegex())[2],passwd.value))},
                text = stringResource(R.string.register)
            )
        }
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = birthdate.toEpochDay() * 86400000
            )
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    Button(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            birthdate = LocalDate.ofEpochDay(millis / 86400000)
                        }
                        birthdateStr.value =
                            "Дата рождения: ${birthdate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}"
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
