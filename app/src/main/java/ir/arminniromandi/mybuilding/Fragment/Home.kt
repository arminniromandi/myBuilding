package ir.arminniromandi.mybuilding.Fragment

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.Activity.ui.theme.back
import ir.arminniromandi.mybuilding.Activity.ui.theme.cyan1
import ir.arminniromandi.mybuilding.Activity.ui.theme.cyan2
import ir.arminniromandi.mybuilding.Date
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.TextInBox


@SuppressLint("SuspiciousIndentation")
@Composable
fun Home(navigate: NavHostController) {

    val verticalGradient = listOf(cyan1, cyan2)
    Column(
        Modifier
            .background(back)
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp, bottom = 8.dp)

        ) {

            Text(text = "سلام مدیر", size = 24)

        }
        Box(
            Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 24.dp)
                .drawBehind {
                    drawRoundRect(
                        Brush.verticalGradient(
                            verticalGradient
                        ),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                }
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center,

            ) {

            Text(

                text = Date().getDate(), size = 22
            )
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
                .padding(horizontal = 24.dp)

                .drawBehind {

                    drawRoundRect(
                        Brush.verticalGradient(
                            verticalGradient
                        ),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                },
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "2", size = 25)
            Text(text = "تعداد پرداخت شده", size = 22)
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            thickness = 0.5.dp
        )



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState())
                .drawBehind {
                    drawRoundRect(
                        color = Color.Gray,
                        cornerRadius = CornerRadius(14.dp.toPx())
                    )
                }
                .padding(horizontal = 8.dp)


        ) {
            TextInBox(
                "اضافه کردن هزینه جدید", Icons.Outlined.Add
            ) { navigate.navigate(Screens.Add.screenName) }
            TextInBox(
                "نمایش هزینه های ثبت شده", Icons.Outlined.DateRange
            ) { navigate.navigate(Screens.Cost.screenName) }
            TextInBox(text = "ویرایش افراد واحد", icon = Icons.Outlined.Edit) {
                navigate.navigate(Screens.Edit.screenName)
            }
            TextInBox(
                "اضافه کردن مشکل",
                Icons.Filled.AddCircle
            ) { navigate.navigate(Screens.Note.screenName) }

        }

    }
}

@Composable
fun Text(text: String, size: Int) {

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



