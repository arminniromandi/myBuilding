package ir.arminniromandi.mybuilding.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.TextInBox

class MainActivity : ComponentActivity() {
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        if (pref.getBoolean("showIntro", true))
            startActivity(Intent(this, IntroActivity::class.java))



        setContent {
            View()
        }

    }


    @Preview(showSystemUi = true)
    @Composable
    private fun View() {


        Column(
            Modifier
                .background(Color(0xFF141414))
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()

            ) {

//                Icon(
//                    painter = painterResource(id = R.drawable.settings),
//                    tint = Color.White,
//                    contentDescription = ""
//                )

                text(text = "سلام مدیر", size = 24)

            }
            Box(
                Modifier
                    .padding(top = 16.dp)
                    .drawBehind {
                        drawRoundRect(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFFD1F300),
                                    Color(0xFF01D123)
                                )
                            ),
                            cornerRadius = CornerRadius(14.dp.toPx())
                        )
                    }
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {

                text(text = "چهارشنبه 12 شهریور", size = 22)
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                thickness = 0.5.dp
            )
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(100.dp)
                    .fillMaxWidth()

                    .drawBehind {
                        drawRoundRect(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xFFD1F300),
                                    Color(0xFF01D123)
                                )
                            ),
                            cornerRadius = CornerRadius(14.dp.toPx())
                        )
                    },
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                text(text = "2", size = 25)
                text(text = "تعداد پرداخت شده", size = 22)
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                thickness = 0.5.dp
            )

            val scrollState = rememberScrollState()
            var offset by remember { mutableStateOf(0f) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 8.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color.Gray,
                            cornerRadius = CornerRadius(14.dp.toPx())
                        )
                    }
                    .padding(horizontal = 8.dp)
                    .scrollable(
                        orientation = Orientation.Vertical,
                        state = rememberScrollableState { delta ->
                            offset += delta
                            delta
                        }
                    )

            ) {
                TextInBox("اضافه کردن هزینه" , Icons.Outlined.AccountBox)
                TextInBox("اضافه کردن واحد" , Icons.Outlined.Build)
                TextInBox("اضافه کردن مشکل" , Icons.Filled.AddCircle)

            }


        }


    }

    @Composable
    fun text(text: String, size: Int) {

        Text(
            text = text,
            Modifier,
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.yekanbakh)
                ),
                fontSize = size.sp
            )
        )

    }


    override fun onPause() {
        super.onPause()
        pref.edit().apply {
            putBoolean("showIntro", false)
            apply()
        }
    }


    @Preview(showSystemUi = true)
    @Composable
    private fun hello() {
        View()
    }
}