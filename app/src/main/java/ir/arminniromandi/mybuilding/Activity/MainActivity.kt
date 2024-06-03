package ir.arminniromandi.mybuilding.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.amozeshgam.persiandate.PersianDate
import ir.arminniromandi.mybuilding.Fragment.Add
import ir.arminniromandi.mybuilding.Fragment.BottomNavItem
import ir.arminniromandi.mybuilding.Fragment.Home
import ir.arminniromandi.mybuilding.Fragment.Screens
import ir.arminniromandi.mybuilding.Fragment.Settings
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.TextInBox

class MainActivity : ComponentActivity() {
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
        if (pref.getBoolean("showIntro", true))
            startActivity(Intent(this, IntroActivity::class.java))



        setContent {
            Column {
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

        var item = arrayOf(
            BottomNavItem(
                name = "Home",
                selectedIcon = Icons.Filled.Home,
                unSelectedIcon = Icons.Outlined.Home,
            ),
            BottomNavItem(
                name = "Add",
                selectedIcon = Icons.Filled.Add,
                unSelectedIcon = Icons.Outlined.Add,
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
                            selected = selectedItem.value == index,
                            colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Gray),
                            onClick = {
                                selectedItem.value = index
                                navController.navigate(item.name)
                            },
                            icon = {
                                if (index ==1){


                                    FloatingActionButton(
                                        modifier = Modifier.padding(bottom = 16.dp),
                                        onClick = { /*TODO*/ }){
                                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "Add")
                                    }
                                }else{

                                    Icon(
                                        imageVector = if (selectedItem.value == index) item.selectedIcon
                                        else item.unSelectedIcon,
                                        contentDescription = item.name,
                                        tint = Color.White
                                    )

                                }
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
                modifier = Modifier.padding(it)){
                composable(Screens.Home.screenName){ Home() }
                composable(Screens.Add.screenName){ Add() }
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