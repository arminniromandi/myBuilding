package ir.arminniromandi.mybuilding.presentation.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import ir.arminniromandi.mybuilding.presentation.navigation.navHost.IntroNavHost
import ir.arminniromandi.mybuilding.presentation.viewModel.intro.IntroViewModel

@Composable
fun IntroScreen() {
    val viewModel = hiltViewModel<IntroViewModel>()
    val navController = rememberNavController()

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
