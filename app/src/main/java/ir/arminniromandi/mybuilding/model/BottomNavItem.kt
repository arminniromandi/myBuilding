package ir.arminniromandi.mybuilding.model

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ir.arminniromandi.mybuilding.R

data class BottomNavItem(
    var name: String,
    var text: String,
    var selectedIcon: ImageVector,
    var unSelectedIcon: ImageVector
)

@Composable
fun RowScope.NavBarItem(
    item: BottomNavItem,
    currentRoute: String?,
    navController: NavHostController
) {
    val routeState = currentRoute == item.name
    NavigationBarItem(
        selected = routeState,

        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = {
            if (!routeState)
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
                tint = MaterialTheme.colorScheme.secondary
            )

        },
        label = {
            Text(
                text = item.text,
                modifier = Modifier.padding(top = 2.dp),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.yekanbakh))
                )
            )
        }
    )
}