package ir.arminniromandi.mybuilding.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.arminniromandi.mybuilding.dataBase.DBHandler
import ir.arminniromandi.mybuilding.utils.date.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(private val application: Application) : AndroidViewModel(application){

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
        isChargeAdded()
    }




    fun isInserted() {
        viewModelScope.launch {
            val count = withContext(Dispatchers.IO) { // Perform database operation on IO thread
                val db = DBHandler.getDataBase(context = application.baseContext)
                db.buildingDao().getBData()
            }
            _isInsert.value = count == 0


        }
    }
    fun isChargeAdded(){
        val today = Date().getDateForCalculate()
        viewModelScope.launch {
            val isExist = withContext(Dispatchers.IO) { // Perform database operation on IO thread
                val db = DBHandler.getDataBase(context = application.baseContext)
                db.buildingDao().isLastMonthExist(today)
            }
             _isChargeAdded.value = isExist != 0
            Log.i("test" , _isChargeAdded.value.toString())



        }
    }

    fun getPayed(month: String , paid : Int) {
        viewModelScope.launch {
            val count = withContext(Dispatchers.IO) {
                val db = DBHandler.getDataBase(context = application.baseContext)
                db.buildingDao().getPaidCount(month , paid  )
            }
            _data.value = count
        }

    }

    fun getName() {
        viewModelScope.launch {
            val name = withContext(Dispatchers.IO) {
                val db = DBHandler.getDataBase(context = application.baseContext)
                db.buildingDao().getBHostName()
            }
            _name.value = name
        }
    }



}