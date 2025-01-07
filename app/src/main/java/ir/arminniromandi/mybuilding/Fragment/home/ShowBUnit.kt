package ir.arminniromandi.mybuilding.Fragment.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.arminniromandi.mybuilding.Activity.ui.theme.back
import ir.arminniromandi.mybuilding.Activity.ui.theme.backDialog
import ir.arminniromandi.mybuilding.Activity.ui.theme.customButton
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
private val changedUnit = mutableIntStateOf(0)
private val changedUnit_Users = mutableIntStateOf(0)

@Composable
fun ShowBUnit() {
    val context = LocalContext.current
    val allUser = remember { mutableStateOf<Array<BUser>>(emptyArray()) }
    LaunchedEffect(key1 = Unit) {
        DBHandler.getDataBase(context).buildingDao().getAllUsers().collect { data ->
            allUser.value = data.toTypedArray()
        }
    }




    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(back)
            .padding(vertical = 16.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .drawBehind {
                    drawRoundRect(
                        primaryVariant,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                },
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "نمایش افراد واحد ساختمان",
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



        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            thickness = 0.5.dp,
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.padding(start = 12.dp),
                tint = Color.White
            )

            Text(
                text = "تعداد افراد",
                fontSize = 18.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = Color.White
                )
            )

            Text(
                text = "واحد",
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = Color.White
                )
            )

        }

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            items(allUser.value.size) {
                ItemshowBUnit(allUser.value[it]) { selectedItem ->
                    changedUnit.intValue = selectedItem.U_id
                    changedUnit_Users.intValue = selectedItem.U_Unit
                }
            }

        }
        if (dialog.value)
            ShowDialog(changedUnit.intValue , changedUnit_Users.intValue)


    }
}

@Composable
fun ItemshowBUnit(users: BUser, onClick: (BUser) -> Unit) {


    Row(
        Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(vertical = 4.dp)

            .drawBehind {
                drawRoundRect(
                    Color(0xFF212121),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            }
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Button(
            onClick = {
                onClick(users)
                dialog.value = true
            },
            colors = buttonColors(
                backgroundColor = customButton
            )
        ) {

            Text(
                text = "ویرایش",
                fontSize = 18.sp,
                color = Color.White,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakhr)
                    )
                )
            )
        }

        Text(
            text = users.U_Unit.toString(),
            fontSize = 24.sp,
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.yekanbakh)
                )
            ),
        )

        Text(
            text = users.U_id.toString(),
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
private fun ShowDialog(unit: Int, count: Int) {
    val context = LocalContext.current



    androidx.compose.ui.window.Dialog(
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
                text = "$unit : واحد",
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
                    onClick = { changedUnit_Users.intValue++ },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(Color.Green)
                ) {
                    Image(imageVector = Icons.Default.Add, contentDescription = "Add")
                }

                Text(
                    text = changedUnit_Users.intValue.toString(),
                    fontSize = 34.sp,
                    color = editTextColor_Text,

                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(editTextColor)

                        .padding(horizontal = 34.dp, vertical = 4.dp)


                )

                IconButton(
                    onClick = { changedUnit_Users.intValue-- },
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
                                unit,
                                changedUnit_Users.intValue
                            )

                        )

                    }


                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(

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


@Preview(name = "ShowBUnit")
@Composable
private fun PreviewShowBUnit() {
    ShowBUnit()
}