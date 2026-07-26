package ir.arminniromandi.mybuilding.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.arminniromandi.mybuilding.presentation.Activity.IntroActivity
import ir.arminniromandi.mybuilding.presentation.navigation.navBar.BottomNavigationBar
import ir.arminniromandi.mybuilding.presentation.navigation.navHost.MainNavHost
import ir.arminniromandi.mybuilding.presentation.viewModel.home.MainViewModel


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.refreshInsertedState()
    }
    val isInserted by viewModel.isInsert.observeAsState()

    when (isInserted) {
        null -> {
            LoadingScreen()
        }

        true -> {
            MainScreenContent(
                viewModel = viewModel
            )
        }

        false -> {
            LaunchedEffect(Unit) {
                val intent = Intent(
                    context,
                    IntroActivity::class.java
                )

                context.startActivity(intent)

                (context as? Activity)?.finish()
            }

            LoadingScreen()
        }
    }
}


@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun MainScreenContent(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = navBackStackEntry
        ?.destination
        ?.route.toString()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = currentRoute
            )
        }
    ) { innerPadding ->
        MainNavHost(
            navController = navController,
            innerPadding = innerPadding,
            myViewModel = viewModel
        )
    }
}