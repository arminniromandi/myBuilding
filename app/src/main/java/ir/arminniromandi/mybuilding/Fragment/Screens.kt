package ir.arminniromandi.mybuilding.Fragment

sealed class Screens (val screenName : String){
    data object Home : Screens("Home")
    data object Add : Screens("Add")
    data object Note : Screens("Note")
    data object Cost : Screens("Cost")
    data object Edit : Screens("Edit")

}