package ir.arminniromandi.mybuilding.viewModel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import kotlinx.coroutines.launch

class IntroViewModel(
    context: Context
) : ViewModel() {

    val db = DBHandler.getDataBase(context)


    private val _name = MutableLiveData("")
    private val _count = MutableLiveData(0)

    val name: LiveData<String> = _name
    val count: LiveData<Int> = _count

    val isAdded = mutableStateOf(false)


    init {
        isUserAdded()
        getOldData()
    }


    fun isUserAdded() {
        viewModelScope.launch {
            val count = db.buildingDao().countOfBuilding()
            isAdded.value = count != 0
        }
    }

    fun getOldData() {

        viewModelScope.launch {
            val userName = db.buildingDao().getBHostName()
            val count = db.buildingDao().getBUnitCount()

            _name.value = userName
            _count.value = count

        }

    }


}