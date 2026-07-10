package ir.arminniromandi.mybuilding.presentation.ui.homeScreens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.checkBox
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.customButton
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.primary
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.presentation.customUi.DialogText
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth
import ir.arminniromandi.mybuilding.presentation.viewModel.home.ShowCostViewModel
import ir.arminniromandi.mybuilding.utils.date.Conststanse.IntFormat


private val index = mutableIntStateOf(0)

@Composable
fun ShowCost(
    navigate: NavHostController,
    viewModel: ShowCostViewModel = hiltViewModel<ShowCostViewModel>()
) {





    val costPerUnit by viewModel.costPerUnit.collectAsState()
    val mouths by viewModel.mounths.collectAsState()
    val color = if (isSystemInDarkTheme()) Color.White else Color.Black

    val showDialog by viewModel.showExposeDialog.collectAsState()
    val isOnAllCostShow by viewModel.isOnAllCostShow.collectAsState()




    LaunchedEffect(Unit) {
        viewModel.getMonths()
    }




    var clickedMouth by remember {
        mutableStateOf("")
    }


    //fixme : در صورتی که نمایش درست نبوده
        LaunchedEffect( Unit, clickedMouth) {
            viewModel.getCostPerUnitWithMounth(clickedMouth)
        }






    if (showDialog) {
        Dialog(costInMonth = mouths[index.intValue] , viewModel)
    }


    val state = LazyGridState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(68.dp)
                    .clip(RoundedCornerShape(12.dp))

                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {

                        if (isOnAllCostShow) {
                            viewModel.changeDialogState(true)
                        } else {

                            /*CoroutineScope(Dispatchers.IO).launch {
                                val db = DBHandler
                                    .getDataBase(context)
                                    .buildingDao()
                                db.deleteCost()
                                db.deleteMounth()
                            }*/

                        }

                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = if (!isOnAllCostShow) "تمامی هزینه ها" else "برای نمایش ریز هزینه کلیک کنید",
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
                    if (!isOnAllCostShow) {
                        navigate.navigate("home")
                    } else {
                        viewModel.changeisOnAllCostShow(false)
                    }

                }
            ) {
                Icon(
                    imageVector = if (!isOnAllCostShow) Icons.Outlined.Home else Icons.Default.ArrowForward,
                    modifier = Modifier.size(45.dp),
                    contentDescription = "",
                    tint = color
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
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally


        ) {
            if (isOnAllCostShow) {
                ShowCostByMouth(costPerUnit, viewModel,color)
            } else {

                if (mouths.isNotEmpty())
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(12.dp),
                        state = state,
                        reverseLayout = false
                    ) {
                        items(mouths.size) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(primary)
                                    .clickable(true) {
                                        index.intValue = it
                                        clickedMouth = mouths[it].CostInMounthAndYear
                                        viewModel.changeisOnAllCostShow(true)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column {
                                    Text(
                                        text = mouths[it].CostInMounthAndYear,
                                        style = TextStyle(
                                            color = Color.White,
                                            fontFamily = FontFamily(Font(R.font.yekanbakh))
                                        ),
                                        fontSize = 24.sp,
                                        modifier = Modifier.padding(top = 12.dp, bottom = 8.dp)
                                    )


                                    Text(
                                        text = IntFormat.format(mouths[it].sumOfAll),
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
                else
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            "هیچ شارژی محاسبه نکرده اید برای محاسبه جدید روی + کلیک کنید",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
            }

        }
    }


}


@Composable
private fun ShowCostByMouth(cost: List<CostForUnit>, viewModel : ShowCostViewModel,color: Color) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

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
                    color = color
                )
            )

            Text(
                text = "هزینه",
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = color
                )
            )

            Text(
                text = "واحد",
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = color
                )
            )

        }



        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()


        ) {

            itemsIndexed(cost) { index, item ->
                CostItems(item, index + 1 , viewModel)
            }

        }


    }


}

@Composable
private fun CostItems(data: CostForUnit, index: Int ,viewModel: ShowCostViewModel) {


    val cost = IntFormat.format(data.C_Cost.toInt())

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
                viewModel.changePaidState(data)
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
    costInMonth: CostInMonth,
    viewModel: ShowCostViewModel
) {


    AlertDialog(
        onDismissRequest = { viewModel.changeDialogState(false) },
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
                    onClick = { viewModel.changeDialogState(false) }) {
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

                DialogText("هزینه آب :  ${IntFormat.format(costInMonth.costWater)}")
                DialogText("هزینه این ماه :  ${IntFormat.format(costInMonth.cost)}")
                DialogText(" هزینه کل :  ${IntFormat.format(costInMonth.sumOfAll)}")
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



