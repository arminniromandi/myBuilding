package ir.arminniromandi.mybuilding.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ir.arminniromandi.mybuilding.Activity.ui.theme.AppTheme
import ir.arminniromandi.mybuilding.Activity.ui.theme.statusBarIntro
import ir.arminniromandi.mybuilding.presentation.ui.IntroScreen


/*
* Intro Activity
* first activity when the user install app
*
*  this activitu for :
*   --getting user and Building data and save in database
*
* */

@AndroidEntryPoint
class IntroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(statusBarIntro)
                }

                IntroScreen()
            }
        }
    }




}




