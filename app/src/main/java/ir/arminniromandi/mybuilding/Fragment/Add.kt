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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.Activity.ui.theme.back
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor_Text
import ir.arminniromandi.mybuilding.Activity.ui.theme.primaryVariant
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.accessbility.DialogText
import ir.arminniromandi.mybuilding.utils.Calculate
import ir.arminniromandi.mybuilding.utils.date.CheckDate
import ir.arminniromandi.mybuilding.utils.date.Date

var showDialog = mutableStateOf(false)
@Composable
fun Add(
    navigate: NavHostController
) {

    if (showDialog.value)
        ShowDialog(navigate = navigate)

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(back)
    ) {

        if (CheckDate(context = LocalContext.current).getAndCheck(Date())) {
            View(navigate = navigate)
        } else {
          showDialog.value = true
        }

    }

}

@Composable
private fun ShowDialog(navigate: NavHostController) {

    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Button(
                    onClick = {
                        navigate.navigate("Home")
                    },
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = primaryVariant
                    )
                ) {
                    Text(
                        text = "برگشت به خانه" ,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        fontFamily = FontFamily(Font(R.font.yekanbakh)),
                    )
                }
            }
        },
        title = {
            Text(text = "اخطار زمان اضافه کردن",
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                fontFamily = FontFamily(Font(R.font.yekanbakh))
                )
        },
        text = {
            DialogText(text = "این ماه فقط میتوانید یک بار هزینه هارا اضافه کنید" , color = Color.DarkGray)
        }
    )


}



var costWater = 0


@Composable
fun OnboardingScreen(continueClick: () -> Unit) {

    Box(
        Modifier
            .background(back)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {


            Text(
                color = Color.White,
                text = "لطفا هزینه آب را وارد کنید",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.yekanbakh)),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )


            var waterCost by remember {
                mutableStateOf("")
            }
            TextField(

                value = waterCost,
                onValueChange = { waterCost = it },

                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 14.dp)
                    .fillMaxWidth(),

                textStyle = TextStyle(
                    textDirection = TextDirection.Rtl,
                    color = Color.White
                ),


                label = {
                    Text(
                        "هزینه آب ساختمان",
                        modifier = Modifier.fillMaxWidth(),
                        color = editTextColor_Text,
                        style = TextStyle(textDirection = TextDirection.Rtl)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )



            Button(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 18.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(2.dp)),

                onClick = {
                    continueClick()
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
private fun View(navigate: NavHostController) {


    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(Modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen { shouldShowOnboarding = false }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(back),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "هزینه های پیش آمده را اضافه کنید",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh)),
                    color = Color.White
                )
                BUnitTF(navigate = navigate)

            }
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun BUnitTF(navigate: NavHostController) {
    var unit by remember {
        mutableIntStateOf(2)
    }

    var data by remember { mutableStateOf(List(unit) { "" }) }


    for (i in data.indices) {
        TextField(
            value = data[i],
            onValueChange = { newData ->
                data = data.toMutableList().also { it[i] = newData }
            },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = editTextColor
            ),


            label = {
                Text(
                    "هزینه ${i + 1}",
                    style = TextStyle(
                        textDirection = TextDirection.Rtl,
                        color = editTextColor_Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp)
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
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(8.dp)


        ) {
            Text(text = "افزودن", style = TextStyle(fontSize = 20.sp))
        }

        OutlinedButton(
            onClick = {
                unit--
                data = data.toMutableList().also { it.removeLast() }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)


        ) {
            Text(text = "کاهش", style = TextStyle(fontSize = 20.sp))
        }
    }


    val context = LocalContext.current
    Button(
        onClick = {
            //.map
            val totalData = data.sumOf {
                if (it.isEmpty()) {
                    0
                } else
                    it.toInt()
            }
            //sum


            Calculate(context).calculateAll(costWater, totalData)
            navigate.navigate(Screens.Home.screenName)


        },
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(text = "محاسبه", style = TextStyle(fontSize = 20.sp))
    }


}







