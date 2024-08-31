package ir.arminniromandi.mybuilding.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.arminniromandi.mybuilding.Activity.ui.theme.MyBuildingTheme
import ir.arminniromandi.mybuilding.Fragment.Add
import ir.arminniromandi.mybuilding.Fragment.BottomNavItem
import ir.arminniromandi.mybuilding.Fragment.EditUserUnit
import ir.arminniromandi.mybuilding.Fragment.Home
import ir.arminniromandi.mybuilding.Fragment.Note
import ir.arminniromandi.mybuilding.Fragment.Screens
import ir.arminniromandi.mybuilding.Fragment.ShowBUnit
import ir.arminniromandi.mybuilding.Fragment.ShowCost
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    private lateinit var pref: SharedPreferences
    private val myViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        if (pref.getBoolean("showIntro", true))
            startActivity(Intent(this, IntroActivity::class.java))

        println("hell")

        setContent {
            MyBuildingTheme {

                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Black)
                }



                Column(
                    Modifier.background(Color.Black)
                ) {
                    BottomNav()
                }
            }
        }

    }

    @Composable
    fun BottomNav() {
        val navController = rememberNavController()


        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val item = arrayOf(

            BottomNavItem(
                name = "Note",
                text = "یادداشت",
                selectedIcon = Icons.Filled.Info,
                unSelectedIcon = Icons.Outlined.Info,
            ),
            BottomNavItem(
                name = "Add",
                text = "شارژ",
                selectedIcon = Icons.Filled.AddCircle,
                unSelectedIcon = Icons.Outlined.AddCircle,
            ),
            BottomNavItem(
                name = "Home",
                text = "داشبورد",
                selectedIcon = Icons.Filled.Home,
                unSelectedIcon = Icons.Outlined.Home,
            )
        )


        Scaffold(
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF252525)) {
                    item.forEachIndexed { index, item ->
                        if (index == 1) {

                            Box(
                                Modifier
                                    .clickable {
                                        navController.navigate(Screens.Add.screenName) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
                                    .padding(8.dp)
                                    .padding(bottom = 8.dp)
                                    .clip(shape = RoundedCornerShape(50))
                                    .background(Color.Blue)
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .size(28.dp),
                                    imageVector = Icons.Default.Add,
                                    tint = Color.White,
                                    contentDescription = ""
                                )
                            }
                        } else {
                            NavigationBarItem(
                                selected = currentRoute == item.name,

                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color(
                                        0xFF1A237E
                                    )
                                ),
                                onClick = {
                                    navController.navigate(item.name) {
                                        popUpTo(navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    }
                                },
                                icon = {

                                    Icon(
                                        imageVector = if (currentRoute == item.name) item.selectedIcon
                                        else item.unSelectedIcon,
                                        contentDescription = item.name,
                                        tint = Color(0xFFBBDEFB)
                                    )

                                },
                                label = {
                                    Text(
                                        text = item.text,
                                        modifier = Modifier.padding(top = 8.dp),
                                        style = TextStyle(
                                            color = Color.White,
                                            fontSize = 15.sp,
                                            fontFamily = FontFamily(Font(R.font.yekanbakh))
                                        )
                                    )
                                }
                            )
                        }
                    }
                }


            }
        ) { it ->

            NavHost(
                navController = navController,
                startDestination = Screens.Home.screenName,
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(
                            durationMillis = 400
                        ),
                    )
                },

                modifier = Modifier.padding(it)
            ) {
                composable(Screens.Home.screenName) { Home(navController, myViewModel) }
                composable(Screens.Note.screenName) { Note() }
                composable(Screens.Add.screenName) { Add(navController ) }
                composable(Screens.ShowBUnit.screenName) { ShowBUnit() }
                composable(Screens.Edit.screenName) { EditUserUnit(navController) }
                composable(Screens.Cost.screenName) { ShowCost(navController) }
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

