package ir.arminniromandi.mybuilding.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.arminniromandi.mybuilding.dataBase.DBHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(private val application: Application) :AndroidViewModel(application){

    private val _data = MutableLiveData(1)
    val payed: LiveData<Int> = _data

    private val _name = MutableLiveData("مدیر")
    val name :LiveData<String> = _name



    fun getPayed(month: String , paid : Int) {
        viewModelScope.launch {
            val count = withContext(Dispatchers.IO) { // Perform database operation on IO thread
                val db = DBHandler.getDataBase(context = application.baseContext)
                db.buildingDao().getPaidCount(month , paid  )
            }
            _data.value = count
        }
    }

    fun getName() {
        viewModelScope.launch {
            val name = withContext(Dispatchers.IO) { // Perform database operation on IO thread
                val db = DBHandler.getDataBase(context = application.baseContext)
                db.buildingDao().getBHostName()
            }
            _name.value = name
        }
    }



}