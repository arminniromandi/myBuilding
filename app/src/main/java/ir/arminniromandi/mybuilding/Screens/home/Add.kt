import android.util.Log
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.Activity.ui.theme.primary
import ir.arminniromandi.mybuilding.Activity.ui.theme.primaryVariant
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.customUi.DialogText
import ir.arminniromandi.mybuilding.navigation.Screens.HomeScreens
import ir.arminniromandi.mybuilding.utils.Calculate
import ir.arminniromandi.mybuilding.utils.ThousandSeparatorTransformation
import ir.arminniromandi.mybuilding.viewModel.MainViewModel

@Composable
fun AddCostScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current

    viewModel.checkDateForWarning(context)

    val showDateWarningDialog by viewModel.showDateWarningDialog.collectAsState()

    Log.i("testUi" , showDateWarningDialog.toString())

    if (showDateWarningDialog) {
        DateWarningDialog(
            onNavigateHome = {
                navController.navigate(HomeScreens.Home.screenName) {
                    // Optional: Configure navigation to clear back stack or be single top
                    popUpTo(HomeScreens.Home.screenName) { inclusive = true }
                }
            }
        )
    }

    Column(
        Modifier
            .fillMaxSize() // Prefer fillMaxSize over fillMaxHeight and fillMaxWidth separately
            .background(MaterialTheme.colorScheme.background)
    ) {
        AddCostContent(navController = navController, viewModel = viewModel)
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
                horizontalArrangement = Arrangement.Start // Or Arrangement.End depending on desired layout
            ) {
                Button(
                    onClick = onNavigateHome,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = primaryVariant // Consider using MaterialTheme.colorScheme
                    )
                ) {
                    Text(
                        text = "برگشت به خانه",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        fontFamily = FontFamily(Font(R.font.yekanbakh)),
                    )
                }
            }
        },
        backgroundColor = MaterialTheme.colorScheme.surface,
        title = {
            Text(
                text = "اخطار زمان اضافه کردن",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface, // Use themed color
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                fontFamily = FontFamily(Font(R.font.yekanbakh))
            )
        },
        text = {
            DialogText( // Assuming DialogText is a custom Composable
                text = "این ماه فقط میتوانید یک بار هزینه هارا اضافه کنید",
                color = MaterialTheme.colorScheme.onSurfaceVariant // Consider MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

@Composable
fun WaterCostInputScreen(
    viewModel: MainViewModel,
    onContinueClicked: () -> Unit
) {
    // Get water cost from ViewModel and provide a way to update it
    var waterCostInput by rememberSaveable { mutableStateOf(viewModel.waterCost.value.toString()) }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.End // For RTL text alignment
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = "لطفا هزینه آب را وارد کنید",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.yekanbakh)),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            TextField(
                value = waterCostInput,
                onValueChange = {
                    waterCostInput = it.filter { char -> char.isDigit() }
                }, // Allow only digits
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
                        style = TextStyle(textDirection = TextDirection.Rtl) // Label also RTL
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
                    .clip(RoundedCornerShape(8.dp)), // Slightly more rounded
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    viewModel.updateWaterCost(waterCostInput.toIntOrNull() ?: 0)
                    onContinueClicked()
                },
                enabled = waterCostInput.isNotBlank() // Disable button if input is empty
            ) {
                Text(
                    "ادامه",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh))
                )
            }
        }
    }
}

@Composable
private fun AddCostContent(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    var showWaterCostInput by rememberSaveable { mutableStateOf(true) }
    val waterCost by viewModel.waterCost.collectAsState()

    Surface(Modifier.fillMaxSize()) { // Ensure Surface takes full size
        if (showWaterCostInput) {
            WaterCostInputScreen(viewModel = viewModel) {
                showWaterCostInput = false
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp), // Add some padding around the content
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "هزینه های پیش آمده را اضافه کنید",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh)),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                DynamicCostInputFields(
                    waterCost = waterCost, // Pass the collected water cost
                    onCostsCalculated = {
                        viewModel.markChargeAsAdded() // ViewModel handles this logic
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
fun DynamicCostInputFields(
    waterCost: Int,
    onCostsCalculated: () -> Unit
) {
    val context = LocalContext.current
    var numberOfCostFields by rememberSaveable { mutableIntStateOf(2) }
    var costValues by remember { mutableStateOf(List(numberOfCostFields) { "" }) }

    // Use LazyColumn for potentially long lists of TextFields for performance
    // For a small fixed number, Column is fine.
    Column {
        for (i in costValues.indices) {
            TextField(
                value = costValues[i],
                onValueChange = { newValue ->
                    costValues = costValues.toMutableList().also {
                        // Allow only digits, default to "0" if empty after filtering
                        val filteredValue = newValue.filter { char -> char.isDigit() }
                        it[i] = filteredValue
                    }
                },
                modifier = Modifier
                    .padding(vertical = 8.dp) // Removed horizontal for fillMaxWidth
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.End // Align text to end for numbers
                ),
                visualTransformation = ThousandSeparatorTransformation(),
                label = {
                    Text(
                        "هزینه ${i + 1}",
                        style = TextStyle(
                            textDirection = TextDirection.Rtl,
                            color = MaterialTheme.colorScheme.onSurfaceVariant // Use a less prominent color for label
                        ),
                        modifier = Modifier.fillMaxWidth() // Label takes full width for RTL text
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
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Add space between buttons
    ) {
        OutlinedButton(
            onClick = {
                numberOfCostFields++
                costValues = costValues.toMutableList().also { it.add("") }
            },
            modifier = Modifier
                .weight(1f) // Distribute width equally or as needed
                .padding(vertical = 8.dp), // Consistent padding
            colors = ButtonDefaults.outlinedButtonColors( // Use outlinedButtonColors
                backgroundColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "افزودن",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh))
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        OutlinedButton(
            onClick = {
                if (numberOfCostFields > 0) { // Prevent removing below zero fields
                    numberOfCostFields--
                    costValues = costValues.toMutableList().also { it.removeLast() }
                }
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), // Use themed outline
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.outlinedButtonColors( // Use outlinedButtonColors
                backgroundColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = numberOfCostFields > 0 // Disable if no fields to remove
        ) {
            Text(
                text = "کاهش", style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh))
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }

    Button(
        onClick = {
            val totalOtherCosts = costValues.sumOf { it.toIntOrNull() ?: 0 }
            Calculate(context).calculateAll(waterCost, totalOtherCosts)
            onCostsCalculated()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = primary // Or MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), // Add vertical padding
        shape = RoundedCornerShape(8.dp),
        enabled = costValues.any { it.isNotBlank() } // Enable only if at least one cost is entered
    ) {
        Text(
            text = "محاسبه",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.yekanbakh)),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary // Ensure contrast
            )
        )
    }
}