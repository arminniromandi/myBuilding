package ir.arminniromandi.mybuilding.Fragment

sealed class Screens (val screenName : String){
    data object Home : Screens("Home")
    data object Add : Screens("Add")
    data object Settings : Screens("Settings")
    data object Note : Screens("Note")

}