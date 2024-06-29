package ir.arminniromandi.mybuilding.Fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ir.arminapp.myapp2.Room.DBHandler
import ir.arminniromandi.mybuilding.Activity.ui.theme.back
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor
import ir.arminniromandi.mybuilding.Activity.ui.theme.editTextColor_Text
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.dataBase.DataModel.BUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EditUser(
    navigate: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(back)
    ) {


        ShowForEdit()
        Button(onClick = {
            navigate.navigate("Home")
        },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
            ) {

            Text(
                text = "ذخیره و بازگشت",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.yekanbakh)),
                    fontSize = 24.sp
                )
            )
        }
    }


}

@Composable
fun ShowForEdit() {

    val context = LocalContext.current

    CoroutineScope(Dispatchers.IO)


    val unit by remember {
        mutableStateOf(3)
    }

    var data by remember { mutableStateOf(List(unit) { "" }) } // Initialize with 4 empty strings






    for (i in data.indices) {
        TextField(
            value = data[i],
            onValueChange = { newData ->
                data = data.toMutableList().also { it[i] = newData }
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = DBHandler.getDataBase(context).buildingDao()
                    if (newData.isNotEmpty())
                        dao.updateUsers(BUser(i + 1, data[i].toInt()))
                }
            },

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = editTextColor
            ),
            singleLine = true,
            textStyle = TextStyle(
                textDirection = TextDirection.Rtl,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            label = {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp),
                    text = "واحد ${i + 1}",
                    style = TextStyle(
                        textDirection = TextDirection.Rtl,
                        color = editTextColor_Text,
                    )
                )
            },

            shape = RoundedCornerShape(50),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )

    }

}

