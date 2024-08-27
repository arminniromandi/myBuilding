package ir.arminniromandi.mybuilding.IntroFragment

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Button
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
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.Activity.MainActivity
import ir.arminniromandi.mybuilding.Activity.ui.theme.backIntro
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor_Text
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.dataBase.DBHandler
import ir.arminniromandi.mybuilding.dataBase.dataModel.BUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SaveData(
    navHostController: NavHostController,
    unit: MutableState<Int>
) {
    val context = LocalContext.current

    var data by remember { mutableStateOf(List(unit.value) { "" }) } // Initialize with 4 empty strings

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(backIntro)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        for (i in data.indices) {
            TextField(
                singleLine = true,
                value = data[i],
                onValueChange = { newData ->
                    data = data.toMutableList().also { it[i] = newData }

                },
                colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                    backgroundColor = editTextColor
                ),
                textStyle = TextStyle(
                    textDirection = TextDirection.Rtl ,
                    color = Color.White
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth(),

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
                var allUnit = data.map { it.toInt() }
                CoroutineScope(Dispatchers.IO).launch {
                    var dao = DBHandler.getDataBase(context).buildingDao()
                    allUnit.forEach {
                        dao.addUsers(BUser(U_Unit = it))
                    }
                }


                context.startActivity(Intent(context, MainActivity::class.java))

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = "ادامه",
                style = TextStyle(fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.yekanbakh)))
            )
        }
    }


}
