package ir.arminniromandi.mybuilding.Fragment.home

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    var name :String,
    var text :String,
    var selectedIcon :ImageVector,
    var unSelectedIcon :ImageVector
)
