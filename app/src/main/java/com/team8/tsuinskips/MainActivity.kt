package com.team8.tsuinskips

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getString
import com.team8.tsuinskips.ui.theme.TSUInSkipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TSUInSkipsTheme {
                Box(
                    Modifier
                        .fillMaxSize(1f)
                        .paint(
                            painterResource(R.drawable.login_background),
                            contentScale = ContentScale.Crop
                        ), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = getString(baseContext, R.string.hello),
                        fontFamily = FontFamily(Font(R.font.istokweb_bold)),
                        fontSize = 36.sp,
                        lineHeight = 52.sp,
                        color = Color(getColor(baseContext, R.color.grey)),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(67.dp, 130.dp, 66.dp, 570.dp)
                    )
                    parametrButton(
                        func = {},
                        context = baseContext,
                        text = getString(baseContext, R.string.login),
                    )
                    parametrButton(
                        func = {},
                        context = baseContext,
                        text = getString(baseContext, R.string.register),
                        oY = 80.dp,
                    )

                }
            }
        }
    }
}

@Composable
fun parametrButton(
    func: () -> Unit,
    context: Context,
    paddingValuesButton: PaddingValues = PaddingValues(0.dp),
    text: String,
    oX: Dp = 0.dp,
    oY: Dp = 0.dp
) {
    Button(
        onClick = func,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonColors(
            Color(getColor(context, R.color.grey)),
            Color.White,
            Color.Unspecified,
            Color.Unspecified
        ),
        modifier = Modifier
            .padding(paddingValuesButton)
            .offset(oX, oY)
            .fillMaxWidth(0.7f)
            .height(61.dp)
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