package ir.arminniromandi.mybuilding.utils.date

import android.icu.text.DecimalFormat
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Home
import ir.arminniromandi.mybuilding.presentation.model.BottomNavItem

object Conststanse {
    const val PAID = 1
    const val NOT_PAID = 0
    val IntFormat = DecimalFormat("0,000")

    val BottomNavItem = arrayOf(

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


}