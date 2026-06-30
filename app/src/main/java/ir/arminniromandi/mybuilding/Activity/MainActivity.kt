package ir.arminniromandi.mybuilding.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import ir.arminniromandi.mybuilding.Activity.ui.theme.AppTheme
import ir.arminniromandi.mybuilding.presentation.ui.MainScreen

/*
* main activity
* if user set and saved data in IntroActivity
* this activity become first
* or
* else user dosent save any data .user navigate to IntroActivity
*
*  the activity for
*       --adding cost per mounth
*       --edit data (per unit person)
*       --show data about Building like(unitCount , charge(Cost) , )
*       --showing how many person payed Cost in this mounth
* */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            AppTheme {
                //setting status bar color to black
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Black)
                }
                MainScreen(this)

            }
        }

    }


}

