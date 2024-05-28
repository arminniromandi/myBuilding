package ir.arminniromandi.mybuilding.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPagerIndicator
import ir.arminapp.myapp2.Room.DBHandler
import ir.arminapp.myapp2.Room.DataModel.Building
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.ui.theme.MyBuildingTheme
import kotlinx.coroutines.launch

class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyBuildingTheme {
                View()
            }
        }
    }

    @Composable
    fun View(modifier: Modifier = Modifier) {

        var showIntro by remember {
            mutableStateOf(true)
        }
        if (showIntro) {
            Intro {
                showIntro = false
            }
        } else {

            Column(
                modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 18.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color(0xFFFEA700))
                    .height(600.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                var bHostName by remember {
                    mutableStateOf("")
                }
                var bUnit by remember {
                    mutableStateOf("")
                }


                TextField(
                    value = bHostName,
                    onValueChange = { bHostName = it },
                    textStyle = TextStyle(
                        textDirection = TextDirection.Rtl
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    singleLine = true,
                    label = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            text = "نام مدیر ساختمان (خودتان)",
                            style = TextStyle(
                                color = Color.Black,
                                textDirection = TextDirection.Rtl
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                TextField(
                    value = bUnit,
                    onValueChange = { bUnit = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        textDirection = TextDirection.Rtl
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    label = { Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        text = "نام مدیر ساختمان (خودتان)",
                        style = TextStyle(
                            textDirection = TextDirection.Rtl
                        )
                    )},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.height(18.dp))
                Button(onClick = {
                    DBHandler.getDataBase(this@IntroActivity)
                        .buildingDao()
                        .insertData(Building(B_id = 0 , bHostName , bUnit.toInt()))

                    startActivity(Intent(this@IntroActivity, MainActivity::class.java))

                }) {
                    Text(
                        text = "ذخیره و ادامه",
                        fontFamily = FontFamily(Font(R.font.yekanbakh))
                    )


                }



            }
        }

    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun Intro(continueClick: () -> Unit) {

        val pages = listOf(
            R.drawable.page1,
            R.drawable.page2
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            val pagerState = rememberPagerState(pageCount = { 2 })
            val coroutineScope = rememberCoroutineScope()
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                state = pagerState
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    painter = painterResource(id = pages[it]),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
            }





            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val prevButtonVisible = remember {
                    derivedStateOf {
                        pagerState.currentPage > 0
                    }
                }

                val nextButtonVisible = remember {
                    derivedStateOf {
                        pagerState.currentPage < 1 // total pages are 5
                    }
                }



                TextButton(
                    enabled = prevButtonVisible.value,
                    onClick = {
                        val prevPageIndex = pagerState.currentPage - 1
                        coroutineScope.launch { pagerState.animateScrollToPage(prevPageIndex) }
                    },
                ) {
                    Text(text = if (prevButtonVisible.value) "قبلی" else "")
                }

                HorizontalPagerIndicator(
                    pageCount = 2,
                    pagerState = pagerState,
                )


                TextButton(
                    onClick = {
                        val nextPageIndex = pagerState.currentPage + 1
                        coroutineScope.launch { pagerState.animateScrollToPage(nextPageIndex) }
                        if (!nextButtonVisible.value)
                            continueClick.invoke()
                    },
                ) {
                    Text(text = if (nextButtonVisible.value) "بعدی" else "بزن بریم")
                }
            }


        }


    }


}

