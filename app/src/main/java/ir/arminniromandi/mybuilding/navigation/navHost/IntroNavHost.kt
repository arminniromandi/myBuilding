package ir.arminniromandi.mybuilding.navigation.navHost

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.arminniromandi.mybuilding.Screens.IntroFragment.GetInfo
import ir.arminniromandi.mybuilding.Screens.IntroFragment.Intro
import ir.arminniromandi.mybuilding.Screens.IntroFragment.SaveData
import ir.arminniromandi.mybuilding.navigation.Screens.ScreensIntro

@Composable
fun IntroNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    unit: MutableIntState
) {
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

        modifier = Modifier.padding(innerPadding)
    ) {
        composable(ScreensIntro.Intro.screenName) { Intro(navController) }
        composable(ScreensIntro.SaveData.screenName) { SaveData(  unit) }
        composable(ScreensIntro.GetBValue.screenName) { GetInfo(navController , unit) }
    }


}