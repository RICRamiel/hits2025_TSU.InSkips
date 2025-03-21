package com.team8.tsuinskips

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.team8.tsuinskips.common.application.InSkipsApplication
import com.team8.tsuinskips.data.RetrofitApi
import com.team8.tsuinskips.domain.Token
import com.team8.tsuinskips.viewModel.StartViewModel

@Composable
fun StartScreen(
    modifier: Modifier = Modifier, navController: NavController, vm: StartViewModel = viewModel()
) {
    val logIn = vm.isLogged.collectAsState()

    vm.checkMemory()
    Box(
        modifier
            .fillMaxSize(1f)
            .paint(
                painterResource(R.drawable.login_background), contentScale = ContentScale.Crop
            ), contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier
                .fillMaxHeight(0.7f)
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(R.string.hello),
                fontFamily = FontFamily(Font(R.font.istokweb_bold)),
                fontSize = 36.sp,
                lineHeight = 52.sp,
                color = colorResource(R.color.grey),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 67.dp, end = 66.dp, bottom = 160.dp)
            )
            ParametrButton(
                func = {
                    navController.navigate("login")
                    InSkipsApplication.getApp().appSharedPref.edit {
                        putString("token", "")
                        putString("email", "")
                        putString("passwd", "")
                        apply()
                    }
                    RetrofitApi.updateToken(Token(""))
                },
                text = stringResource(R.string.login),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.7f)
                    .height(61.dp)

            )
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            ParametrButton(
                func = {
                    navController.navigate("register")
                    InSkipsApplication.getApp().appSharedPref.edit {
                        putString("token", "")
                        putString("email", "")
                        putString("passwd", "")
                        apply()
                    }
                    RetrofitApi.updateToken(Token(""))
                },
                text = stringResource(R.string.enregister),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.7f)
                    .height(61.dp)
            )
            Log.i("logInSTATUS", logIn.value.toString())
            if (logIn.value) {
                navController.navigate("request")
            }
        }
    }
}