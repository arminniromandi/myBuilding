package ir.arminniromandi.mybuilding.Activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.arminniromandi.mybuilding.InteroFragment.GetBValue
import ir.arminniromandi.mybuilding.InteroFragment.Intro
import ir.arminniromandi.mybuilding.InteroFragment.SaveData
import ir.arminniromandi.mybuilding.InteroFragment.ScreensIntro
import ir.arminniromandi.mybuilding.ui.theme.MyBuildingTheme

class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            MyBuildingTheme {
                Fragment()
            }
        }
    }


    @Composable
    fun Fragment() {
        val navController = rememberNavController()
        var unit = remember {
            mutableStateOf(0)
        }
        Scaffold {


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
                composable(ScreensIntro.SaveData.screenName) { SaveData(navController , unit) }
                composable(ScreensIntro.GetBValue.screenName) { GetBValue(navController , unit) }
            }


        }
    }




}




