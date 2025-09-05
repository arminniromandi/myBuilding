package ir.arminniromandi.mybuilding.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.arminniromandi.mybuilding.Activity.ui.theme.AppTheme
import ir.arminniromandi.mybuilding.data.local.preferences.PreferencesManager
import ir.arminniromandi.mybuilding.navigation.navBar.BottomNavigationBar
import ir.arminniromandi.mybuilding.navigation.navHost.MainNavHost
import ir.arminniromandi.mybuilding.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    private lateinit var myViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myViewModel = MainViewModel(application , PreferencesManager(this))

        myViewModel.checkIsInserted{
            startActivity(Intent(this, IntroActivity::class.java))
        }

        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Black)
                }

                Body()

            }
        }

    }

    @Composable
    fun Body() {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController , currentRoute.toString())
            }
        ) { it ->

            MainNavHost(
                navController = navController,
                innerPadding = it,
                myViewModel = myViewModel
            )
        }

    }


    override fun onPause() {
        super.onPause()
        myViewModel.changeState()
    }


}

