package ir.arminniromandi.mybuilding.Fragment

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.amozeshgam.persiandate.PersianDate
import ir.arminniromandi.mybuilding.Activity.CalculateActivity
import ir.arminniromandi.mybuilding.Date
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.TextInBox


@SuppressLint("SuspiciousIndentation")
@Composable
fun Home(navigate: NavHostController) {
    val context = LocalContext.current
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

            Icon(
                imageVector = Icons.Outlined.AccountBox,
                tint = Color.White,
                contentDescription = ""
            )

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

            text(text = Date().getDate(), size = 22)
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
            TextInBox(
                "اضافه کردن هزینه جدید", Icons.Outlined.Add
            ) {

                context.startActivity(Intent(context, CalculateActivity::class.java))
            }
            TextInBox("تغییر تعداد افراد واحد ", Icons.Outlined.Build) {

            }
            TextInBox("اضافه کردن مشکل", Icons.Filled.AddCircle) {}

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



