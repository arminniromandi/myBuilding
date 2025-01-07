package ir.arminniromandi.mybuilding.Fragment.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import ir.arminniromandi.mybuilding.Fragment.HomeScreens
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.customUi.TextInBox
import ir.arminniromandi.mybuilding.utils.date.Const
import ir.arminniromandi.mybuilding.utils.date.Date
import ir.arminniromandi.mybuilding.viewModel.MainViewModel


@SuppressLint("SuspiciousIndentation")
@Composable
fun Home(navigate: NavHostController, viewModel: MainViewModel) {

    val payedCount by
    viewModel.payed.observeAsState()
    viewModel.getPayed(Date().getDateForCalculate(), Const.PAID)

    val name by viewModel.name.observeAsState()
    viewModel.getName()

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

            Text(text = "سلام $name", size = 24, Modifier)

        }
        Box(
            Modifier
                .padding(top = 12.dp)
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
                .height(85.dp),
            contentAlignment = Alignment.Center,

            ) {

            Text(
                text = Date().getDate(), size = 22, Modifier
            )
        }


        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .height(85.dp)
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

            Text(text = payedCount.toString(), size = 25,
                modifier = Modifier.clickable {
                    viewModel.getPayed(Date().getDateForCalculate(), Const.PAID)
                })
            Text(text = "تعداد پرداخت شده", size = 22, Modifier)
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            color = Color.LightGray,
            thickness = 0.5.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 12.dp)
                .verticalScroll(rememberScrollState())
                .drawBehind {
                    drawRoundRect(
                        color = Color(0xAD292929),
                        cornerRadius = CornerRadius(7.dp.toPx(), 7.dp.toPx())
                    )
                }
                .padding(horizontal = 8.dp)


        ) {




            TextInBox(
                "نمایش تمامی شارژ ", Icons.Outlined.DateRange
            ) { navigate.navigate(HomeScreens.Cost.screenName) }

            TextInBox(text = "نمایش تمامی افراد ", icon = Icons.Default.Face) {
                navigate.navigate(HomeScreens.ShowBUnit.screenName)
            }

            TextInBox(
                "اضافه کردن یادداشت",
                Icons.Filled.Info
            ) { navigate.navigate(HomeScreens.Note.screenName) }




        }

    }
}

@Composable
private fun Text(text: String, size: Int, modifier: Modifier) {

    Text(
        text = text,
        modifier = modifier,
        color = Color.White,
        style = TextStyle(
            fontFamily = FontFamily(
                Font(R.font.yekanbakh)
            ),
            fontSize = size.sp
        )
    )

}



