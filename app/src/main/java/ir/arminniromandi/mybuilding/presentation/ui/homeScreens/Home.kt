package ir.arminniromandi.mybuilding.presentation.ui.homeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.outlined.AddHomeWork
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.presentation.customUi.TextInBox
import ir.arminniromandi.mybuilding.presentation.navigation.Screens.HomeScreens
import ir.arminniromandi.mybuilding.presentation.viewModel.home.MainViewModel





@Composable
fun Home(navigate: NavHostController, viewModel: MainViewModel) {
    val textColor = if (!isSystemInDarkTheme()) Color.Black else Color.White


    val isChargeAdded by
    viewModel.isChargeAdded.observeAsState()

    if (isChargeAdded == true) {
        viewModel.getPayed(true)
    }


    LaunchedEffect(Unit) {
        viewModel.loadInitialData()

    }

    val payedCount by
    viewModel.payed.observeAsState()



    val name by viewModel.name.observeAsState()

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .padding(top = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        //اندازه اوکی
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp)

        ) {

            MText(
                text = "سلام $name",
                size = 24,
                color = textColor
            )

        }



        Card(
            Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                MText(
                    text = viewModel.nowDate, size = 22, color = textColor.copy(0.9f)
                )

            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary // آبی ملایم‌تر
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.getPayed(true) }
        ) {

            Row(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountBalanceWallet,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(75.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    MText(text = "تعداد پرداخت های این ماه", color = Color.White, size = 18)

                    Text(
                        if (isChargeAdded == true) payedCount?.toString() ?: ""
                        else "لطفا شارژ جدیدی را اضافه کنید",
                        color = Color(0xEBF1EDED),
                        fontSize = if (isChargeAdded == true) 24.sp else 12.sp
                    )

                }

            }
        }


        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState())

        ) {

            TextInBox(
                "اضافه کردن شارژ", textColor, Icons.Outlined.AddHomeWork
            ) { navigate.navigate(HomeScreens.Add.screenName) }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))


            TextInBox(
                "نمایش تمامی شارژ ", textColor, Icons.Outlined.Today
            ) { navigate.navigate(HomeScreens.Cost.screenName) }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = DividerDefaults.color.copy(0.8f)
            )
            TextInBox(text = "نمایش تمامی افراد ", textColor, icon = Icons.Outlined.Groups) {
                navigate.navigate(HomeScreens.ShowBUnit.screenName)
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 24.dp),
                color = DividerDefaults.color.copy(0.8f)
            )


            TextInBox(
                "اضافه کردن یادداشت",
                textColor,
                Icons.Outlined.Description
            ) { navigate.navigate(HomeScreens.Note.screenName) }


        }

    }
}


@Composable
private fun MText(
    text: String,
    size: Int,
    modifier: Modifier = Modifier,
    color: Color
) {

    Text(
        text = text,
        color = color,
        modifier = modifier,
        style = TextStyle(
            fontFamily = FontFamily(
                Font(R.font.yekanbakh)
            ),
            fontSize = size.sp
        )
    )

}



