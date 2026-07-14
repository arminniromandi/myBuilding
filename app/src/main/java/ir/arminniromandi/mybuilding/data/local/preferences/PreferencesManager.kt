package ir.arminniromandi.mybuilding.data.local.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferencesManager @Inject constructor (@ApplicationContext context : Context) {

     private  val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun changeIsAddState(state: Boolean){
        prefs.edit().apply {
            putBoolean("showIntro", state)
            apply()
        }
    }





    fun checkState() = prefs.getBoolean("showIntro", true)



}