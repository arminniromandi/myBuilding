package ir.arminniromandi.mybuilding.data.local.preferences

import android.content.Context

class PreferencesManager(context : Context) {

     private  val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveState(){
        prefs.edit().apply {
            putBoolean("showIntro", false)
            apply()
        }
    }

    fun checkState() = prefs.getBoolean("showIntro", true)



}