package ir.arminniromandi.mybuilding.presentation.navigation.Screens

sealed class HomeScreens (val screenName : String){
    data object Home : HomeScreens("Home")
    data object Add : HomeScreens("Add")
    data object Note : HomeScreens("Note")
    data object Cost : HomeScreens("Cost")
    data object ShowBUnit : HomeScreens("ShowBUnit")



}
