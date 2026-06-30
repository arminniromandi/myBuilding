package ir.arminniromandi.mybuilding.presentation.ui.IntroFragment

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.arminniromandi.mybuilding.Activity.MainActivity
import ir.arminniromandi.mybuilding.Activity.ui.theme.backDialog
import ir.arminniromandi.mybuilding.Activity.ui.theme.backIntro
import ir.arminniromandi.mybuilding.Activity.ui.theme.customButton
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor_Text
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.presentation.customUi.DialogText
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private val showDialog = mutableStateOf(false)

@Composable
fun SaveData(
    unit: MutableState<Int>
) {
    val context = LocalContext.current

    val isValidData = remember { mutableStateOf(false) }

    var data by remember { mutableStateOf(List(unit.value) { "" }) } // Initialize with 4 empty strings

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .background(backIntro),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showDialog.value)
            ShowDialog(showDialog)



        for (i in data.indices) {
            TextField(
                singleLine = true,
                value = data[i],
                onValueChange = { newData ->
                    data = data.toMutableList().also { it[i] = newData }

                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = editTextColor
                ),
                textStyle = TextStyle(
                    textDirection = TextDirection.Rtl,
                    color = Color.White
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),
                isError = if (data[i].isNullOrBlank()) true else false,

                label = {
                    Text(
                        "واحد ${i + 1}",
                        color = editTextColor_Text,
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




        Button(
            onClick = {
                val allUnit = data.map {
                    if (it.isNullOrBlank()) null else it.toInt()
                 }

                  allUnit.forEach { intData ->
                    if (intData == null) {
                        showDialog.value = true
                    }
                      if (!(intData == null))
                          isValidData.value = true

                }

                if (isValidData.value){

                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = DBHandler.getDataBase(context).buildingDao()
                        allUnit.forEach {
                            dao.addUsers(BUser(U_Unit = it!!))
                        }
                    }


                    context.startActivity(Intent(context, MainActivity::class.java))
                }




            },
            colors = ButtonDefaults.buttonColors(
                containerColor = customButton
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "ادامه",
                color = Color.White,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakhr))
                )
            )
        }

    }


}

@Composable
private fun ShowDialog(state: MutableState<Boolean>) {

    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        onDismissRequest = { state.value = false },
        buttons = {
            Button(
                modifier = Modifier.padding(start = 12.dp , bottom = 12.dp),
                onClick = { state.value = false },
                colors = ButtonDefaults.buttonColors(containerColor = customButton)
            ) {
                Text(
                    text = "اوکی",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.yekanbakhr))
                    )

                )

            }
        },
        backgroundColor = backDialog,
        title = {
            DialogText(text = "مشکل در ورود داده ها", size = 20 , color = Color.White )

        },
        text = {
            DialogText(text = "لطفا تمامی واحد ها را به درستی وارد کنید" , size = 18)
        }
    )

}