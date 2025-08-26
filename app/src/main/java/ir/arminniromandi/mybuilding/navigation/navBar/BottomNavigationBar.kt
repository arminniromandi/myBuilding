package ir.arminniromandi.mybuilding.navigation.navBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.model.NavBarItem
import ir.arminniromandi.mybuilding.navigation.Screens.HomeScreens
import ir.arminniromandi.mybuilding.utils.date.Conststanse.BottomNavItem

@Composable
fun BottomNavigationBar(navController: NavHostController , currentRoute: String){

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        BottomNavItem.forEachIndexed { index, item ->
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