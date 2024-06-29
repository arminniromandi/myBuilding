package ir.arminniromandi.mybuilding.Fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.arminapp.myapp2.Room.DBHandler
import ir.arminniromandi.mybuilding.Calculate

import ir.arminniromandi.mybuilding.CheckDate
import ir.arminniromandi.mybuilding.Date
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.dataBase.DataModel.CostInMonth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun Add(
    navigate: NavHostController
) {


    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.Black)
    ) {


//        if (CheckDate(LocalContext.current).getAndCheck(Date()))
            View(navigate)
//        else
//            ShowDialog(navigate)

    }

}

@Composable
fun ShowDialog(navigate: NavHostController) {




        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Button(
                    onClick = {
                        navigate.navigate("Home")
                    },
                ) {
                    Text(text = "برگشت به خانه")
                }
            },
            title = {
                Text(text = "اخطار زمان اضافه کردن")
            },
            text = {
                Text(text = "این ماه فقط میتوانید یک بار هزینه هارا اضافه کنید")
            }
        )



}


var costWater = 0


@Composable
fun OnboardingScreen(ContinueClick: () -> Unit) {

    Box(
        Modifier
            .background(Color(0xFF000000))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {


            Text(
                color = Color.White,
                text = "لطفا هزینه آب را تعیین کنید",
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )


            var waterCost by remember {
                mutableStateOf("")
            }
            TextField(

                value = waterCost,
                onValueChange = { waterCost = it },
                textStyle = TextStyle(
                    textDirection = TextDirection.Rtl ,
                    color = Color.White
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 14.dp)
                    .fillMaxWidth(),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.LightGray
                ),


                label = {
                    Text(
                        "هزینه آب ساختمان",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            textDirection = TextDirection.Rtl
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )



            Button(
                modifier = Modifier
                    .padding(vertical = 16.dp , horizontal = 18.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(2.dp))
                ,

                onClick = {
                    ContinueClick()
                    costWater = waterCost.toInt()
                }
            ) {
                Text(
                    "ادامه",
                    fontSize = 22.sp,
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    )
                )
            }
        }
    }
}


@Composable
fun View(navigate: NavHostController) {


    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(Modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen() { shouldShowOnboarding = false }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "هزینه های پیش آمده را اضافه کنید", fontSize = 20.sp)
                BUnitTF(navigate = navigate)

            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun BUnitTF(navigate: NavHostController) {
    var unit by remember {
        mutableStateOf(3)
    }
    var data by remember { mutableStateOf(List(unit) { "" }) } // Initialize with 4 empty strings

    for (i in data.indices) {
        TextField(
            value = data[i].toString(),
            onValueChange = { newData ->
                data = data.toMutableList().also { it[i] = newData }

            },
            textStyle = TextStyle(
                textDirection = TextDirection.Rtl
            ),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .fillMaxWidth(),

            label = {
                Text(
                    "هزینه ${i + 1}",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        textDirection = TextDirection.Rtl
                    )
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),

        ) {
        OutlinedButton(
            onClick = {
                unit++
                data = data.toMutableList().also { it.add("") }

            },
            modifier = Modifier.fillMaxWidth(0.5f)


        ) {
            Text(text = "افزودن", style = TextStyle(fontSize = 20.sp))
        }

        OutlinedButton(
            onClick = {
                unit--
                data = data.toMutableList().also { it.removeLast() }

            },
            modifier = Modifier.fillMaxWidth()


        ) {
            Text(text = "کاهش", style = TextStyle(fontSize = 20.sp))
        }
    }


    val context = LocalContext.current
    Button(
        onClick = {
            var currentDate = Date().getDateForCalculate()
            var totalData = data.map {
                it.toInt()
            }.sum()


            Calculate(context).calculateAll(costWater, totalData)
            navigate.navigate(Screens.Home.screenName)


        },
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(text = "محاسبه", style = TextStyle(fontSize = 20.sp))
    }


}




