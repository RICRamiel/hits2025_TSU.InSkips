package com.team8.tsuinskips

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.team8.tsuinskips.ui.theme.TSUInSkipsTheme

@Composable
fun StartScreen(navController: NavController) {
    TSUInSkipsTheme {
        Box(
            Modifier
                .fillMaxSize(1f)
                .paint(
                    painterResource(R.drawable.login_background), contentScale = ContentScale.Crop
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.hello),
                fontFamily = FontFamily(Font(R.font.istokweb_bold)),
                fontSize = 36.sp,
                lineHeight = 52.sp,
                color = colorResource(R.color.grey),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(67.dp, 130.dp, 66.dp, 570.dp)
            )
            ParametrButton(
                func = { navController.navigate("login") },
                text = stringResource(R.string.login),
            )
            ParametrButton(
                func = { navController.navigate("register") },
                text = stringResource(R.string.enregister),
                oY = 80.dp,
            )

        }
    }
}