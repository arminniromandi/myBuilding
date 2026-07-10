package ir.arminniromandi.mybuilding.presentation.navigation.navHost

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
import ir.arminniromandi.mybuilding.presentation.ui.IntroScreens.GetInfo
import ir.arminniromandi.mybuilding.presentation.ui.IntroScreens.Intro
import ir.arminniromandi.mybuilding.presentation.ui.IntroScreens.SaveData
import ir.arminniromandi.mybuilding.presentation.navigation.Screens.ScreensIntro
import ir.arminniromandi.mybuilding.presentation.viewModel.intro.IntroViewModel

@Composable
fun IntroNavHost(
    navController: NavHostController,
    viewModel : IntroViewModel,
    innerPadding: PaddingValues,
    unit: MutableIntState
) {


    NavHost(
        navController = navController,
        startDestination = if (viewModel.isAdded.value) ScreensIntro.GetBValue.screenName else ScreensIntro.Intro.screenName,
        enterTransition = {
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
        composable(ScreensIntro.SaveData.screenName) { SaveData(unit) }
        composable(ScreensIntro.GetBValue.screenName) { GetInfo(navController , unit , viewModel) }
    }


}