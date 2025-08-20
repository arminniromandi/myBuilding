package ir.arminniromandi.mybuilding.Screens.home


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.Activity.ui.theme.back
import ir.arminniromandi.mybuilding.Activity.ui.theme.checkBox
import ir.arminniromandi.mybuilding.Activity.ui.theme.customButton
import ir.arminniromandi.mybuilding.Activity.ui.theme.primary
import ir.arminniromandi.mybuilding.Activity.ui.theme.primaryVariant
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.customUi.DialogText
import ir.arminniromandi.mybuilding.dataBase.DBHandler
import ir.arminniromandi.mybuilding.dataBase.dataModel.CostForUnit
import ir.arminniromandi.mybuilding.dataBase.dataModel.CostInMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat


private val dialog = mutableStateOf(false)
private val index = mutableIntStateOf(0)
private val show = mutableStateOf(false)
private val format = DecimalFormat("0,000")

@Composable
fun ShowCost(navigate: NavHostController) {
    val context = LocalContext.current


    val cost = remember { mutableStateOf<Array<CostForUnit>>(emptyArray()) }
    val mouths = remember { mutableStateOf<Array<CostInMonth>>(emptyArray()) }





    LaunchedEffect(key1 = Unit) {
        cost.value = emptyArray()
        DBHandler.getDataBase(context).buildingDao().getMouths().collect { data ->
            mouths.value = data.toTypedArray()
        }
    }
    val clickedMouth = remember {
        mutableStateOf("")
    }


    if (show.value) {
        LaunchedEffect(key1 = Unit) {
            DBHandler.getDataBase(context).buildingDao().getCostWithMonth(clickedMouth.value)
                .collect { fetchedCost ->
                    cost.value = fetchedCost.toTypedArray()
                }
        }
    }





    if (dialog.value) {
        Dialog(costInMonth = mouths.value[index.intValue])
    }


    val state = LazyGridState()

    Column(
        modifier = Modifier

            .fillMaxWidth()
            .fillMaxHeight()
            .background(back)
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .background(back)
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            Box(modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(68.dp)
                .drawBehind {
                    drawRoundRect(
                        primaryVariant,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
                .clickable {

                    if (show.value) {
                        dialog.value = true
                    } else {

                        CoroutineScope(Dispatchers.IO).launch {
                            val db = DBHandler
                                .getDataBase(context)
                                .buildingDao()

                            db.deleteCost()
                            db.deleteMounth()

                        }

                    }

                },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = if (!show.value) "تمامی هزینه ها" else "برای نمایش ریز هزینه کلیک کنید",
                    style = TextStyle(
                        fontFamily = FontFamily(
                            Font(R.font.yekanbakh)
                        ),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            }

            IconButton(
                onClick = {
                    if (!show.value) {
                        navigate.navigate("home")
                    } else {
                        show.value = false
                    }

                }
            ) {
                Icon(
                    imageVector = if (!show.value) Icons.Outlined.Home else Icons.Default.ArrowForward,
                    modifier = Modifier.size(45.dp),
                    contentDescription = "",
                    tint = Color.White
                )
            }

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            thickness = 0.5.dp
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()

        ) {
            if (show.value) {
                ShowCostByMouth(cost.value)
            } else {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .padding(12.dp),
                    state = state,
                    reverseLayout = false
                ) {
                    items(mouths.value.size) {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(primary)
                                .clickable(true) {
                                    index.intValue = it
                                    clickedMouth.value = mouths.value[it].CostInMounthAndYear
                                    show.value = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column {
                                Text(
                                    text = mouths.value[it].CostInMounthAndYear,
                                    style = TextStyle(
                                        color = Color.White,
                                        fontFamily = FontFamily(Font(R.font.yekanbakh))
                                    ),
                                    fontSize = 24.sp,
                                    modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                                )


                                Text(
                                    text = format.format(mouths.value[it].sumOfAll),
                                    style = TextStyle(
                                        fontFamily = FontFamily(Font(R.font.yekanbakh)),
                                        color = Color.White
                                    ),
                                    fontSize = 24.sp,
                                    modifier = Modifier
                                        .padding(bottom = 12.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            }

                        }

                    }

                }
            }

        }
    }


}


@Composable
private fun ShowCostByMouth(cost: Array<CostForUnit>) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(back)
            .padding(vertical = 24.dp, horizontal = 24.dp)
    ) {


        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "پرداخت",
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = Color.White
                )
            )

            Text(
                text = "هزینه",
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = Color.White
                )
            )

            Text(
                text = "واحد",
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = Color.White
                )
            )

        }



        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()


        ) {

            itemsIndexed(cost) { index, item ->
                CostItems(item, index + 1)
            }

        }


    }


}

@Composable
private fun CostItems(data: CostForUnit, index: Int) {
    val context = LocalContext.current

    val cost = format.format(data.C_Cost.toInt())

    Row(
        Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .drawBehind {
                drawRoundRect(
                    primary,
                    cornerRadius = CornerRadius(12.dp.toPx())
                )
            }
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = data.isPaid,
            onCheckedChange = {
                data.isPaid = it
                CoroutineScope(Dispatchers.IO).launch {
                    DBHandler.getDataBase(context).buildingDao()
                        .updateCost(data.copy(isPaid = it))
                }
            },
            colors = CheckboxDefaults.colors(
                checkedColor = checkBox,
                uncheckedColor = Color.White,
                checkmarkColor = Color.White

            ),
            modifier = Modifier.size(width = 26.dp, height = 24.dp)

        )


        Text(
            text = cost.toString(),
            fontSize = 24.sp,
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.yekanbakh)
                )
            )
        )

        Text(
            text = index.toString(),
            color = Color.White,
            fontSize = 24.sp,
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.yekanbakh)
                )
            )
        )


    }


}


@Composable
private fun Dialog(
    costInMonth: CostInMonth
) {
    val format = DecimalFormat("0,000")


    AlertDialog(
        onDismissRequest = { dialog.value = false },
        backgroundColor = Color.DarkGray,
        confirmButton = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = customButton
                    ),
                    onClick = { dialog.value = false }) {
                    Text(
                        text = "اوکی", fontSize = 20.sp, fontFamily = FontFamily(
                            Font(R.font.yekanbakhr)
                        ),
                        color = Color.White
                    )
                }
            }
        },
        title = {
            Text(
                text = "ریز هزینه های این ماه",
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                fontFamily = FontFamily(Font(R.font.yekanbakh)),

                )
        },
        text = {
            Column(horizontalAlignment = Alignment.End) {

                DialogText("هزینه آب :  ${format.format(costInMonth.costWater)}")
                DialogText("هزینه این ماه :  ${format.format(costInMonth.cost)}")
                DialogText(" هزینه کل :  ${format.format(costInMonth.sumOfAll)}")
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)


    )


}



