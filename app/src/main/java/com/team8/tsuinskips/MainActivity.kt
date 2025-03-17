package com.team8.tsuinskips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.team8.tsuinskips.ui.theme.TSUInSkipsTheme
import com.team8.tsuinskips.viewModel.NewRequestViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSUInSkipsTheme{
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNav(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun AppNav(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = "home"
    ) {
        composable("home") { StartScreen(modifier, navController) }
        composable("login") { LoginScreen(modifier, navController) }
        composable("register"){ SignUpScreen(modifier, navController)}
        composable("request"){ RequestsScreen(navController)}
        composable("new_request"){ NewRequestScreen(modifier, navController)}
    }
}

@Composable
fun ParametrButton(
    func: () -> Unit,
    paddingValuesButton: PaddingValues = PaddingValues(0.dp),
    text: String,
    oX: Dp = 0.dp,
    oY: Dp = 0.dp,
    fraction: Float = 0.7f,
    modifier: Modifier = Modifier
        .padding(paddingValuesButton)
        .offset(oX, oY)
        .fillMaxWidth(fraction)
        .height(61.dp)
) {
    Button(
        onClick = func, shape = RoundedCornerShape(16.dp), colors = ButtonColors(
            colorResource(R.color.grey), Color.White, Color.Unspecified, Color.Unspecified
        ), modifier = modifier

    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.istokweb_regular)),
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            maxLines = 1
        )
    }
}

@Composable
fun SmallButton(
    func: () -> Unit,
    paddingValuesButton: PaddingValues = PaddingValues(0.dp),
    text: String,
) {
    Button(
        onClick = func, shape = RoundedCornerShape(16.dp), colors = ButtonColors(
            colorResource(R.color.grey), Color.White, Color.Unspecified, Color.Unspecified
        ), modifier = Modifier
            .padding(paddingValuesButton)
            .height(61.dp)
            .fillMaxWidth(0.9f)
    ) {
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.istokweb_regular)),
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterVertically),
            maxLines = 1
        )
    }
}