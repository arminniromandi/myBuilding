package ir.arminniromandi.mybuilding.presentation.ui

import android.content.Context
import android.content.Intent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.arminniromandi.mybuilding.presentation.Activity.IntroActivity
import ir.arminniromandi.mybuilding.presentation.navigation.navBar.BottomNavigationBar
import ir.arminniromandi.mybuilding.presentation.navigation.navHost.MainNavHost
import ir.arminniromandi.mybuilding.presentation.viewModel.MainViewModel


@Composable
fun MainScreen(context: Context) {
    val viewModel = hiltViewModel<MainViewModel>()


    viewModel.checkIsInserted {
        context.startActivity(Intent(context, IntroActivity::class.java))
    }

    // Navigation controller
    val navController = rememberNavController()
    // Current navigation destination (updates automatically on route changes)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.toString()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController , currentRoute)
        }
    ) {

        MainNavHost(
            navController = navController,
            innerPadding = it,
            myViewModel = viewModel
        )
    }



}