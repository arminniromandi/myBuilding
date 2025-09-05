package ir.arminniromandi.mybuilding.viewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import kotlinx.coroutines.launch

class IntroViewModel(
     context : Context
):ViewModel() {

    val db = DBHandler.getDataBase(context)

    val isAdded = mutableStateOf(false)




    fun isUserAdded(){
        viewModelScope.launch {
            val count = db.buildingDao().countOfBuilding()

            isAdded.value = count != 0
        }
    }

    fun getOldData(){

    }




}