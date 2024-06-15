package ir.arminniromandi.mybuilding.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ir.arminniromandi.mybuilding.ui.theme.MyBuildingTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class CalculateActivity : ComponentActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyBuildingTheme {
                AddCalculate()
            }
        }
    }

}


@Composable
fun AddCalculate(


    ) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        View()

    }
}



var costWater = 0
@Composable
fun OnboardingScreen( ContinueClick: () -> Unit) {

    Box(
        Modifier
            .background(Color(0xFF131212))
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {


            Text(
                "لطفا هزینه آب را تعیین کنید",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )


            var waterCost by remember {
                mutableStateOf("")
            }
            TextField(
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Gray,
                    textColor = Color.White,
                    disabledTextColor = Color.Black
                ),
                value = waterCost,
                onValueChange = { waterCost = it },
                textStyle = TextStyle(
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .fillMaxWidth(),

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
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                onClick = {
                    ContinueClick()
                    costWater = waterCost.toInt()
                }
            ) {
                Text(
                    "ادامه",
                    fontSize = 20.sp
                )
            }
        }
    }
}


@Composable
fun View() {


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
                BUnitTF(  )

            }
        }
    }
}

@Composable
fun BUnitTF() {
    var unit by remember {
        mutableStateOf(3)
    }
    var data by remember { mutableStateOf(List(unit) { "" }) } // Initialize with 4 empty strings

    for (i in data.indices) {
        TextField(
            singleLine = true,
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





    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(text = "محاسبه", style = TextStyle(fontSize = 20.sp))
    }


}
