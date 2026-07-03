package ir.arminniromandi.mybuilding.presentation.ui.IntroFragment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.HorizontalPagerIndicator
import ir.arminniromandi.mybuilding.presentation.navigation.Screens.ScreensIntro
import ir.arminniromandi.mybuilding.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Intro(navController: NavHostController) {

    val pages = listOf(
        R.drawable.intro1,
        R.drawable.intro2,
        R.drawable.intro3
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        val pagerState = rememberPagerState(pageCount = { 3 })
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
                    pagerState.currentPage < 2
                }
            }



            TextButton(
                enabled = prevButtonVisible.value,
                onClick = {
                    val prevPageIndex = pagerState.currentPage - 1
                    coroutineScope.launch { pagerState.animateScrollToPage(prevPageIndex) }
                },
            ) {
                Text(text = if (prevButtonVisible.value) "قبلی" else "",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh)),
                    color = Color.White
                )
            }

            HorizontalPagerIndicator(
                pageCount = 3,
                activeColor = Color.White,
                inactiveColor = Color.LightGray,
                pagerState = pagerState,
            )


            TextButton(
                onClick = {
                    val nextPageIndex = pagerState.currentPage + 1
                    coroutineScope.launch { pagerState.animateScrollToPage(nextPageIndex) }
                    if (!nextButtonVisible.value)
                        navController.navigate(ScreensIntro.GetBValue.screenName)
                },
            ) {
                Text(text = if (nextButtonVisible.value) "بعدی" else "بزن بریم",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh)),
                    color = Color.White
                    )
            }
        }


    }


}