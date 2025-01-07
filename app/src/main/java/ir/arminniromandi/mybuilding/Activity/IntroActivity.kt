package ir.arminniromandi.mybuilding.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.arminniromandi.mybuilding.Activity.ui.theme.MyBuildingTheme
import ir.arminniromandi.mybuilding.Activity.ui.theme.statusBarIntro
import ir.arminniromandi.mybuilding.Fragment.IntroFragment.GetInfo
import ir.arminniromandi.mybuilding.Fragment.IntroFragment.Intro
import ir.arminniromandi.mybuilding.Fragment.IntroFragment.SaveData
import ir.arminniromandi.mybuilding.Fragment.ScreensIntro

class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyBuildingTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(statusBarIntro)
                }

                Fragment()
            }
        }
    }


    @Composable
    fun Fragment() {
        val navController = rememberNavController()
        val unit = remember {
            mutableIntStateOf(0)
        }
        Scaffold { it ->


            NavHost(
                navController = navController,
                startDestination = ScreensIntro.Intro.screenName,
                enterTransition = { // When entering Screen2
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(
                            durationMillis = 400
                        ),
                    )
                },

                modifier = Modifier.padding(it)
            ) {
                composable(ScreensIntro.Intro.screenName) { Intro(navController) }
                composable(ScreensIntro.SaveData.screenName) { SaveData(  unit) }
                composable(ScreensIntro.GetBValue.screenName) { GetInfo(navController , unit) }
            }


        }
    }




}




