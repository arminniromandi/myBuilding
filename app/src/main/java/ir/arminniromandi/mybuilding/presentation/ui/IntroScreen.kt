package ir.arminniromandi.mybuilding.presentation.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import ir.arminniromandi.mybuilding.presentation.navigation.navHost.IntroNavHost
import ir.arminniromandi.mybuilding.presentation.viewModel.IntroViewModel

@Composable
fun IntroScreen() {
    val viewModel = hiltViewModel<IntroViewModel>()
    val navController = rememberNavController()

    viewModel.isUserAdded()

    //if user leave app in half and dosent save any data
    //app will get some data when user leaved and return to app
    if (viewModel.isAdded.value) {
        viewModel.getOldData()
    }
    val unit = remember {
        mutableIntStateOf(viewModel.count.value ?: 0)
    }

    Scaffold { it ->
        IntroNavHost(
            navController = navController,
            viewModel,
            innerPadding = it,
            unit = unit
        )
    }
}
