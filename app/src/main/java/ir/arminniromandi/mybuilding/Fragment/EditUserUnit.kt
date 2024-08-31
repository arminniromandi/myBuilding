package ir.arminniromandi.mybuilding.Fragment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.Activity.ui.theme.back
import ir.arminniromandi.mybuilding.Activity.ui.theme.backDialog
import ir.arminniromandi.mybuilding.Activity.ui.theme.cyanLight
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor_Text
import ir.arminniromandi.mybuilding.Activity.ui.theme.primaryVariant
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.dataBase.DBHandler
import ir.arminniromandi.mybuilding.dataBase.dataModel.BUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val dialog = mutableStateOf(false)
private val unit = mutableStateOf("")

@Composable
fun EditUserUnit(
    navigate: NavHostController
) {




    if (dialog.value) {
        if (unit.value != "") {
            ShowDialog(navigate)
        }
    }



    Box(
        Modifier
            .fillMaxSize()
            .background(back)
            .blur(if (dialog.value) 12.dp else 0.dp)
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.End
        ) {
            View(navigate)
        }


    }
}


@Composable
private fun View( navigate: NavHostController) {
    Text(
        text = "کدام واحد را ویرایش میکنید",
        modifier = Modifier.padding(bottom = 16.dp),
        color = Color.White,
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.yekanbakh))
    )


    TextField(
        value = unit.value,
        onValueChange = {
            unit.value = it
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = editTextColor
        ),
        singleLine = true,
        textStyle = TextStyle(
            textDirection = TextDirection.Rtl,
            color = Color.White,
            fontSize = 20.sp
        ),
        modifier = Modifier
            .fillMaxWidth(),
        label = {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                text = "واحد",
                style = TextStyle(
                    textDirection = TextDirection.Rtl,
                    color = editTextColor_Text,
                    fontSize = 12.sp
                )
            )
        },


        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )

    )

    Spacer(modifier = Modifier.height(16.dp))


    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Button(
            onClick = { navigate.navigate(Screens.Home.screenName) },
            modifier = Modifier.fillMaxWidth(0.5f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            ),
            border = BorderStroke(1.dp, primaryVariant)
        ) {

            Text(
                text = "لغو",
                color = Color.Black,
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(R.font.yekanbakhsb)
                )

            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

        Button(
            onClick = { dialog.value = true },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryVariant
            )
        ) {
            Text(
                text = "بعدی",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily(
                    Font(R.font.yekanbakhsb)
                )
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        Text(
            text = "تغییرات بر روی تعداد افراد واحد تاثیر میگذارد",
            fontSize = 18.sp,
            color = cyanLight,
            textAlign = TextAlign.End,
            fontFamily = FontFamily(
                Font(R.font.yekanbakhr)

            )
        )
    }


}


@Composable
private fun ShowDialog(navigate: NavHostController) {
    val context = LocalContext.current

    val changedUnit = remember {
        mutableIntStateOf(0)
    }

    Dialog(
        onDismissRequest = { dialog.value = false }
    ) {
        Column(
            Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(backDialog),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "واحد ${unit.value}",
                fontSize = 24.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.yekanbakh))

            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { changedUnit.intValue++ },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(Color.Green)
                ) {
                    Image(imageVector = Icons.Default.Add, contentDescription = "Add")
                }

                Text(
                    text = changedUnit.intValue.toString(),
                    fontSize = 34.sp,
                    color = editTextColor_Text,

                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(editTextColor)

                        .padding(horizontal = 34.dp, vertical = 4.dp)


                )

                IconButton(
                    onClick = { changedUnit.intValue-- },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(Color.Red)

                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_remove_24),
                        contentDescription = "Add",
                    )
                }


            }


            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    dialog.value = false

                    CoroutineScope(Dispatchers.IO).launch {

                        DBHandler.getDataBase(context).buildingDao().updateUsers(
                            BUser(
                                unit.value.toInt(),
                                changedUnit.intValue
                            )

                        )

                    }
                    navigate.navigate(Screens.Home.screenName)


                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryVariant
                )
            ) {
                Text(
                    text = "ویرایش",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakhsb)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}


