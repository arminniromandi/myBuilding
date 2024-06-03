package ir.arminniromandi.mybuilding.Activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.arminniromandi.mybuilding.ui.theme.MyBuildingTheme

class CalculateActivity : ComponentActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = intent.getIntegerArrayListExtra("data")

        setContent {
            MyBuildingTheme {
               Greeting(intent!!)
            }
        }
    }





    @Composable
    fun Greeting(data:ArrayList<Int>) {

        Column(
            modifier = Modifier
                .padding(12.dp)
                .scrollable(
                    ScrollableState { 1f },
                    orientation = Orientation.Vertical
                )
        ) {


            for (i in data.indices) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "${data[i]}", fontSize = 24.sp)
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .height(18.dp)
                            .width(1.dp),
                    )
                    Text(text = "واحد ${i + 1} ", fontSize = 28.sp)

                }
            }
        }

    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun GreetingPreview() {
        MyBuildingTheme {




        }
    }
}