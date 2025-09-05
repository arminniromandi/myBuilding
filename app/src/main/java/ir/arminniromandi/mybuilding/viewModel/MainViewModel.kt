package ir.arminniromandi.mybuilding.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.preferences.PreferencesManager
import ir.arminniromandi.mybuilding.utils.date.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(
    private val application: Context,
    val prefsManager : PreferencesManager
    ) : ViewModel(){

    private val db = DBHandler.getDataBase(application)

    private val _name = MutableLiveData("مدیر")
    private val _data = MutableLiveData(1)
    private val _isInsert = MutableLiveData(false)
    private val _isChargeAdded = MutableLiveData(false)

     val isChargeAdded :LiveData<Boolean> = _isChargeAdded
    val payed: LiveData<Int> = _data
    val name :LiveData<String> = _name
    val isInsert :LiveData<Boolean> = _isInsert

    init {
        getName()
        isInserted()
        isChargeAdded()
    }

    fun checkIsInserted(onAction : ()->Unit){
        if (!prefsManager.checkState())
            if (isInsert.value == false)
                onAction()

    }

    fun changeState(){
        prefsManager.saveState()
    }






    fun isInserted() {
        viewModelScope.launch {
            val count =  withContext(Dispatchers.IO) {
                db.buildingDao().getBData()
            }


             _isInsert.value = count == 0
            Log.i(
                "test",
                "count" +(count).toString()
            )
            Log.i(
                "test",
                "is Insert = " +isInsert.value.toString()
            )
        }
    }
    fun isChargeAdded(){
        val today = Date().getDateForCalculate()
        viewModelScope.launch {
            val isExist = withContext(Dispatchers.IO) { // Perform database operation on IO thread

                db.buildingDao().isLastMonthExist(today)
            }
             _isChargeAdded.value = isExist != 0



        }
    }

    fun getPayed(month: String , paid : Int) {
        viewModelScope.launch {
            val count = withContext(Dispatchers.IO) {

                db.buildingDao().getPaidCount(month , paid  )
            }
            _data.value = count
        }

    }

    fun getName() {
        viewModelScope.launch {
            val name = withContext(Dispatchers.IO) {

                db.buildingDao().getBHostName()
            }
            _name.value = name
        }
    }



}