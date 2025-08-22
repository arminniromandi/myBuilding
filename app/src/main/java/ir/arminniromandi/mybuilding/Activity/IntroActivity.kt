package ir.arminniromandi.mybuilding.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.arminniromandi.mybuilding.Activity.ui.theme.AppTheme
import ir.arminniromandi.mybuilding.Activity.ui.theme.statusBarIntro
import ir.arminniromandi.mybuilding.navigation.navHost.IntroNavHost

class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(statusBarIntro)
                }

                Body()
            }
        }
    }


    @Composable
    fun Body() {
        val navController = rememberNavController()
        val unit = remember {
            mutableIntStateOf(0)
        }
        Scaffold { it ->


            IntroNavHost(
                navController = navController,
                innerPadding = it,
                unit = unit
            )


        }
    }




}




