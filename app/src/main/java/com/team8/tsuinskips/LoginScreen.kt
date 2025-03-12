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
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
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
import com.team8.tsuinskips.viewModel.LoginViewModel

@Composable
fun LoginScreen(navController: NavHostController, vm: LoginViewModel = viewModel()) {
    val email = remember { mutableStateOf("") }
    val passwd = remember { mutableStateOf("") }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
                text = stringResource(R.string.enter),
                fontFamily = FontFamily(Font(R.font.istokweb_bold)),
                fontSize = 36.sp,
                lineHeight = 52.sp,
                color = colorResource(R.color.grey),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 32.dp)
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { newText -> email.value = newText },
                placeholder = { Text(stringResource(R.string.email)) },
                maxLines = 1,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(vertical = 24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            OutlinedTextField(
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
                func = { vm.login(UserLogin(email.value, passwd.value)) },
                text = stringResource(R.string.login)
            )
//            Row(
//                modifier = Modifier.fillMaxWidth(0.9f),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                SmallButton(func = {}, text = stringResource(R.string.register))
//
//            }
        }
    }
}