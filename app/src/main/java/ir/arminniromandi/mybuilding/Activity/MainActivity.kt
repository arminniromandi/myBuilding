package ir.arminniromandi.mybuilding.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ir.arminniromandi.mybuilding.Fragment.Add
import ir.arminniromandi.mybuilding.Fragment.BottomNavItem
import ir.arminniromandi.mybuilding.Fragment.Home
import ir.arminniromandi.mybuilding.Fragment.Note
import ir.arminniromandi.mybuilding.Fragment.Screens
import ir.arminniromandi.mybuilding.Fragment.Settings
import ir.arminniromandi.mybuilding.R

class MainActivity : ComponentActivity() {
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        if (pref.getBoolean("showIntro", true))
            startActivity(Intent(this, IntroActivity::class.java))



        setContent {
            Column(
                Modifier.background(Color.Black)
            ) {
                BottomNav()
            }

        }

    }

    @Composable
    fun BottomNav() {
        var navController = rememberNavController()

        var selectedItem = rememberSaveable {
            mutableStateOf(0)
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        var item = arrayOf(
            BottomNavItem(
                name = "Home",
                selectedIcon = Icons.Filled.Home,
                unSelectedIcon = Icons.Outlined.Home,
            ),
            BottomNavItem(
                name = "Note",
                selectedIcon = Icons.Filled.Info,
                unSelectedIcon = Icons.Outlined.Info,
            ),


            BottomNavItem(
                name = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unSelectedIcon = Icons.Outlined.Settings,
            )


        )

        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = Color.Black , ) {
                    item.forEachIndexed {index , item->
                        NavigationBarItem(
                            selected = currentRoute == item.name,

                            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Gray),
                            onClick = {
                                navController.navigate(item.name) {
                                    popUpTo(navController.graph.startDestinationId)
                                    launchSingleTop = true
                                }
                            },
                            icon = {

                                    Icon(
                                        imageVector = if (selectedItem.value == index) item.selectedIcon
                                        else item.unSelectedIcon,
                                        contentDescription = item.name,
                                        tint = Color.White
                                    )

                            },
                            label = {
                                    Text(
                                        text = item.name,
                                        style = TextStyle(
                                            color = Color.White,
                                            fontFamily = FontFamily(Font(R.font.yekanbakh))
                                        )
                                    )
                            },
                            alwaysShowLabel = false
                        )
                    }
                }


            }
        ) {

            NavHost(
                navController = navController,
                startDestination = Screens.Home.screenName,
                enterTransition = { // When entering Screen2
                    slideInHorizontally(initialOffsetX = { it } ,
                        animationSpec = tween(
                            durationMillis = 400
                        ),
                        ) // Slide in from the left
                },

                modifier = Modifier.padding(it)){
                composable(Screens.Home.screenName){ Home(navController) }
                composable(Screens.Note.screenName){ Note() }
                composable(Screens.Add.screenName){ Add(navController) }
                composable(Screens.Settings.screenName){ Settings() }
                }
        }

    }





    override fun onPause() {
        super.onPause()
        pref.edit().apply {
            putBoolean("showIntro", false)
            apply()
        }
    }


}

