package com.team8.tsuinskips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.team8.tsuinskips.ui.theme.TSUInSkipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSUInSkipsTheme {
                Box(
                    Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(R.drawable.login_background),
                            contentScale = ContentScale.Crop
                        )
                ) {

                }
            }
        }
    }
}

@Composable
fun HelloScreen() {
    Text(
        text = R.string.hello.toString(),

    )

}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true, widthDp = 100, heightDp = 100)
@Composable
fun HelloScreenPreview() {
    Box(
        Modifier
            .fillMaxSize()
            .paint(
                painterResource(R.drawable.login_background), contentScale = ContentScale.Crop
            )
    ) {
        HelloScreen()
    }
}