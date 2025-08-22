package ir.arminniromandi.mybuilding.Activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ir.arminniromandi.mybuilding.Activity.ui.theme.AppTheme
import ir.arminniromandi.mybuilding.navigation.Screens.HomeScreens
import ir.arminniromandi.mybuilding.model.BottomNavItem
import ir.arminniromandi.mybuilding.model.NavBarItem
import ir.arminniromandi.mybuilding.navigation.navHost.MainNavHost
import ir.arminniromandi.mybuilding.utils.date.Date
import ir.arminniromandi.mybuilding.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    private lateinit var pref: SharedPreferences
    private val myViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = getSharedPreferences("pref", MODE_PRIVATE)
        if (pref.getBoolean("showIntro", true)) {
            myViewModel.isInserted()
            if (myViewModel.isInsert.value == false)
                startActivity(Intent(this, IntroActivity::class.java))
        }



        setContent {
            AppTheme(darkTheme = false) {

                val systemUiController = rememberSystemUiController()

                SideEffect {
                    systemUiController.setSystemBarsColor(Color.Black)
                }

                Column(
                    Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    Body()
                }
            }
        }

    }

    @Composable
    fun Body() {
        val navController = rememberNavController()


        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val item = arrayOf(

            BottomNavItem(
                name = "Note",
                text = "یادداشت",
                selectedIcon = Icons.AutoMirrored.Filled.EventNote,
                unSelectedIcon = Icons.AutoMirrored.Outlined.EventNote,
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
                NavigationBar(containerColor = Color(0xFFF9FAFB)) {
                    item.forEachIndexed { index, item ->
                        if (index == 1) {

                            Box(
                                Modifier
                                    .clickable {
                                        navController.navigate(HomeScreens.Add.screenName) {
                                            popUpTo(navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        }
                                    }
                                    .padding(8.dp)
                                    .padding(bottom = 8.dp)
                                    .clip(shape = RoundedCornerShape(50))
                                    .background(MaterialTheme.colorScheme.primary)
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
                            NavBarItem(
                                item = item,
                                currentRoute = currentRoute,
                                navController = navController
                            )
                        }
                    }
                }


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
        pref.edit().apply {
            putBoolean("showIntro", false)
            apply()
        }
    }


}

