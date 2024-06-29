package ir.arminniromandi.mybuilding.Fragment

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.arminapp.myapp2.Room.DBHandler
import ir.arminniromandi.mybuilding.Activity.ui.theme.back
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.dataBase.DataModel.CostForUnit
import ir.arminniromandi.mybuilding.dataBase.DataModel.CostInMonth


@Composable
fun ShowCost() {
    val context = LocalContext.current

    var show = remember {
        mutableStateOf(false)
    }
    var mouths = remember { mutableStateOf<Array<CostInMonth>>(emptyArray()) }

    LaunchedEffect(key1 = Unit) {
        DBHandler.getDataBase(context).buildingDao().getMouths().collect { fetchedNotes ->
            mouths.value = fetchedNotes.toTypedArray()
        }
    }









    var index = remember {
        mutableStateOf("")
    }

    val state = LazyGridState()

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Black)

    ) {
        if (show.value) {
            ShowCostByMouth()
        }else {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(16.dp),
                state = state,
                reverseLayout = true
            ) {
                items(mouths.value.size) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .clickable(true) {
                                index.value = mouths.value[it].CostInMounthAndYear
                                show.value = true
                                Log.i("test", index.value)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mouths.value[it].CostInMounthAndYear,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.yekanbakh))
                            ),
                            fontSize = 24.sp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                        // TODO: Add here a cost on mouth
                        Text(
                            text = mouths.value[it].C_MonthId.toString(),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.yekanbakh))
                            ),
                            fontSize = 24.sp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )

                    }

                }

            }
        }

    }


   
}


@Composable
fun ShowCostByMouth() {
    Column(
        modifier = Modifier.fillMaxWidth()
            .fillMaxHeight()
            .background(back)
            .padding(vertical = 24.dp , horizontal = 24.dp)
    ){
        Row(
            Modifier

                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "پرداخت",
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = Color.White
                )
            )

            Text(
                text = "هزینه",
                fontSize = 20.sp,
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
    }



}


@Preview(name = "ShowCost")
@Composable
private fun PreviewShowCost() {
    ShowCostByMouth()
}