package ir.arminniromandi.mybuilding.presentation.ui.IntroScreens

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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.backIntro
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.editTextColor
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.editTextColor_Text
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.transparent
import ir.arminniromandi.mybuilding.presentation.navigation.Screens.ScreensIntro
import ir.arminniromandi.mybuilding.presentation.viewModel.intro.IntroViewModel


@Composable
fun GetInfo(
    navController: NavController,
    unit: MutableIntState,
    viewModel: IntroViewModel
) {

    val showDialog by viewModel.showDateWarningDialog.collectAsState()


    val h_name = remember {
        mutableStateOf(viewModel.name.value ?: "")
    }
    if (showDialog)
        Dialog(navController, viewModel,h_name.value, unit.intValue)


    Box(
        Modifier
            .imePadding()
            .fillMaxWidth()
            .fillMaxHeight(),

        ) {


        Image(
            painter = painterResource(id = R.drawable.b2),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "building"

        )



        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
                .clip(RoundedCornerShape(topStart = 44.dp))
                .background(backIntro)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.End

        ) {

            Text(
                text = " ورود به برنامه",
                fontSize = 34.sp,
                color = Color.White,
                fontFamily = FontFamily(
                    Font(R.font.yekanbakh)
                ),
                modifier = Modifier
                    .padding(top = 16.dp, end = 34.dp, bottom = 12.dp)
            )

            TextField(
                value = h_name.value,
                onValueChange = { newData ->
                    h_name.value = newData
                },
                shape = RoundedCornerShape(50),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    textDirection = TextDirection.Rtl,
                    color = Color.White
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = editTextColor,
                    disabledIndicatorColor = transparent,
                    focusedIndicatorColor = transparent,
                    unfocusedIndicatorColor = transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp)
                    .padding(bottom = 12.dp),
                label = {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp)
                            .padding(bottom = 4.dp),
                        text = "نام",
                        color = editTextColor_Text,
                        fontFamily = FontFamily(Font(R.font.yekanbakh)),
                        fontSize = 20.sp,
                        style = TextStyle(
                            textDirection = TextDirection.Rtl
                        )
                    )
                },
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "تعداد  واحد",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh))
                )

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 34.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {

                IconButton(
                    onClick = { unit.intValue++ },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(Color.Green)
                ) {
                    Image(imageVector = Icons.Default.Add, contentDescription = "Add")
                }

                TextField(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth(0.3f),
                    value = unit.intValue.toString(),
                    shape = RoundedCornerShape(50),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = editTextColor,
                        disabledIndicatorColor = transparent,
                        focusedIndicatorColor = transparent,
                        unfocusedIndicatorColor = transparent

                    ),
                    textStyle = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    ),
                    onValueChange = {
                        unit.intValue = it.toInt()
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    )
                )


                IconButton(
                    onClick = {
                        if (unit.intValue >= 1)
                            unit.intValue--
                    },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(Color.Red)

                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_remove_24),
                        contentDescription = "Add",
                    )
                }
            }



            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 34.dp)
                        .padding(bottom = 12.dp),
                    onClick = {
                        unit.intValue = unit.intValue
                        viewModel.dialogState(true)

                    }) {
                    Text(
                        text = "ذخیره و ادامه",
                        fontFamily = FontFamily(Font(R.font.yekanbakh)),
                        fontSize = 20.sp,
                    )

                }

                Text(
                    text = "!! توجه",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh))
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "مدیر گرامی باید برای محاسبه هزینه آب  به تعداد واحد ساختمان نفرات هر واحد را اضافه کنید",
                    color = Color.LightGray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    modifier = Modifier.padding(horizontal = 24.dp),
                    fontFamily = FontFamily(Font(R.font.yekanbakh))
                )


            }


        }

    }
}


@Composable
private fun Dialog(
    navController: NavController,
    viewModel : IntroViewModel,
    h_name: String,
    unit: Int
) {

    AlertDialog(
        onDismissRequest = { viewModel.dialogState(false)},
        backgroundColor = Color.DarkGray,
        confirmButton = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {

                Button(
                    onClick = {
                        viewModel.insertBuildingUser()

                        navController.navigate(ScreensIntro.SaveData.screenName)
                    }) {
                    Text(
                        text = "بله", fontSize = 16.sp, fontFamily = FontFamily(
                            Font(R.font.yekanbakhr)
                        ),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))

                Button(

                    onClick = {
                        viewModel.dialogState(false)
                    }) {
                    Text(
                        text = "خیر", fontSize = 16.sp, fontFamily = FontFamily(
                            Font(R.font.yekanbakhr)
                        ),
                        color = Color.DarkGray
                    )


                }


            }
        },
        title = {
            Text(
                text = "آیا از صحت داده ها مطمعن هستید",
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                fontFamily = FontFamily(Font(R.font.yekanbakh)),

                )
        },
        text = {
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "نام مدیر : $h_name",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
                Text(
                    text = "تعداد واحد : $unit",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )

            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)


    )

}




