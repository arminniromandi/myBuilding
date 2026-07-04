package ir.arminniromandi.mybuilding.presentation.navigation.navHost

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ir.arminniromandi.mybuilding.presentation.navigation.Screens.HomeScreens
import ir.arminniromandi.mybuilding.presentation.ui.homeScreens.AddCostScreen
import ir.arminniromandi.mybuilding.presentation.ui.homeScreens.Home
import ir.arminniromandi.mybuilding.presentation.ui.homeScreens.Note
import ir.arminniromandi.mybuilding.presentation.ui.homeScreens.ShowBUnit
import ir.arminniromandi.mybuilding.presentation.ui.homeScreens.ShowCost
import ir.arminniromandi.mybuilding.presentation.viewModel.MainViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    myViewModel: MainViewModel
) {

    NavHost(
        navController = navController,
        startDestination = HomeScreens.Home.screenName,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(
                    durationMillis = 600
                ),
            )
        },

        modifier = Modifier.padding(innerPadding)
    ) {
        composable(HomeScreens.Home.screenName) { Home(navController, myViewModel) }
        composable(HomeScreens.Note.screenName) { Note() }
        composable(HomeScreens.Add.screenName) { AddCostScreen(navController , myViewModel) }
        composable(HomeScreens.ShowBUnit.screenName) { ShowBUnit() }
        composable(HomeScreens.Cost.screenName) { ShowCost(navController) }
    }
}