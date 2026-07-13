package ir.arminniromandi.mybuilding.presentation.ui.homeScreens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.primary
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.primaryVariant
import ir.arminniromandi.mybuilding.presentation.customUi.DialogText
import ir.arminniromandi.mybuilding.presentation.navigation.Screens.HomeScreens
import ir.arminniromandi.mybuilding.presentation.viewModel.home.AddCostViewModel
import ir.arminniromandi.mybuilding.utils.ThousandSeparatorTransformation

private val yekanBakhFontFamily = FontFamily(Font(R.font.yekanbakh))

@Composable
fun AddCostScreen(
    navController: NavHostController,
    addCostViewModel: AddCostViewModel = hiltViewModel()
) {
    val showDateWarningDialog by addCostViewModel.showDateWarningDialog.collectAsState()
    val mainWaterCost by addCostViewModel.waterCost.collectAsState()

    LaunchedEffect(Unit) {
        addCostViewModel.checkDateForWarning()
    }

    LaunchedEffect(mainWaterCost) {
        addCostViewModel.setInitialWaterCost(mainWaterCost)
    }

    if (!showDateWarningDialog) {
        DateWarningDialog(
            onNavigateHome = {
                navController.navigate(HomeScreens.Home.screenName) {
                    popUpTo(HomeScreens.Home.screenName) { inclusive = true }
                }
            }
        )
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        AddCostContent(
            navController = navController,
            addCostViewModel = addCostViewModel
        )
    }
}

@Composable
private fun AddCostContent(
    navController: NavHostController,
    addCostViewModel: AddCostViewModel
) {
    val showWaterCostInput by addCostViewModel.showWaterCostInput.collectAsState()

    Surface(Modifier.fillMaxSize()) {
        if (showWaterCostInput) {
            WaterCostInputScreen(
                viewModel = addCostViewModel,
                onContinueClicked = {
                    addCostViewModel.onContinueClicked { waterCost ->
                        addCostViewModel.updateWaterCost(waterCost)
                    }
                }
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "هزینه های پیش آمده را اضافه کنید",
                    fontSize = 20.sp,
                    fontFamily = yekanBakhFontFamily,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                DynamicCostInputFields(
                    viewModel = addCostViewModel,
                    onCostsCalculated = {
                        addCostViewModel.calculateCosts {
                            navController.navigate(HomeScreens.Home.screenName) {
                                popUpTo(HomeScreens.Home.screenName) { inclusive = true }
                            }
                        }


                        navController.navigate(HomeScreens.Home.screenName) {
                            popUpTo(HomeScreens.Home.screenName) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun DateWarningDialog(
    onNavigateHome: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    onClick = onNavigateHome,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = primaryVariant
                    )
                ) {
                    Text(
                        text = "برگشت به خانه",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        fontFamily = yekanBakhFontFamily
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
        title = {
            Text(
                text = "اخطار زمان اضافه کردن",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                fontFamily = yekanBakhFontFamily
            )
        },
        text = {
            DialogText(
                text = "این ماه فقط میتوانید یک بار هزینه هارا اضافه کنید",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@Composable
fun WaterCostInputScreen(
    viewModel: AddCostViewModel,
    onContinueClicked: () -> Unit
) {
    val waterCostInput by viewModel.waterCostInput.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = "لطفا هزینه آب را وارد کنید",
                fontSize = 24.sp,
                fontFamily = yekanBakhFontFamily,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            TextField(
                value = waterCostInput,
                onValueChange = viewModel::onWaterCostChanged,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 14.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    textDirection = TextDirection.Rtl,
                    color = MaterialTheme.colorScheme.onBackground
                ),
                label = {
                    Text(
                        "هزینه آب ساختمان",
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = TextStyle(textDirection = TextDirection.Rtl)
                    )
                },
                visualTransformation = ThousandSeparatorTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            Button(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 18.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary
                ),
                onClick = onContinueClicked,
                enabled = viewModel.canContinue()
            ) {
                Text(
                    "ادامه",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 22.sp,
                    fontFamily = yekanBakhFontFamily
                )
            }
        }
    }
}

@Composable
fun DynamicCostInputFields(
    viewModel: AddCostViewModel,
    onCostsCalculated: () -> Unit
) {
    val costValues by viewModel.costValues.collectAsState()

    Column {
        costValues.forEachIndexed { index, value ->
            TextField(
                value = value,
                onValueChange = { newValue ->
                    viewModel.onCostValueChanged(index, newValue)
                },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End
                ),
                visualTransformation = ThousandSeparatorTransformation(),
                label = {
                    Text(
                        "هزینه ${index + 1}",
                        style = TextStyle(
                            textDirection = TextDirection.Rtl,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            onClick = viewModel::addCostField,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "افزودن",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = yekanBakhFontFamily
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        OutlinedButton(
            onClick = viewModel::removeCostField,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = viewModel.canRemoveCostField()
        ) {
            Text(
                text = "کاهش",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = yekanBakhFontFamily
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Button(
        onClick = onCostsCalculated,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = primary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        shape = RoundedCornerShape(8.dp),
        enabled = viewModel.canCalculate()
    ) {
        Text(
            text = "محاسبه",
            style = TextStyle(
                fontFamily = yekanBakhFontFamily,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}