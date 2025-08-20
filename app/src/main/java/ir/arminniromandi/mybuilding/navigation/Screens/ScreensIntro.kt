package ir.arminniromandi.mybuilding.navigation.Screens

sealed class ScreensIntro (val screenName : String){
    data object Intro : ScreensIntro("Intro")

    data object GetBValue : ScreensIntro("GetBValue")
    data object SaveData : ScreensIntro("SaveData")



}