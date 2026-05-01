package ir.arminniromandi.mybuilding.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.preferences.PreferencesManager
import ir.arminniromandi.mybuilding.utils.date.CheckDate
import ir.arminniromandi.mybuilding.utils.date.Date
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(
    application: Context,
    val prefsManager: PreferencesManager
) : ViewModel() {

    private val db = DBHandler.getDataBase(application)

    private val _name = MutableLiveData("مدیر")
    private val _data = MutableLiveData(1)
    private val _isInsert = MutableLiveData(false)
    private val _isChargeAdded = MutableLiveData(false)

    val isChargeAdded: LiveData<Boolean> = _isChargeAdded
    val payed: LiveData<Int> = _data
    val name: LiveData<String> = _name
    val isInsert: LiveData<Boolean> = _isInsert

    init {
        getName()
        isInserted()
        isChargeAdded()
    }

    private val _showDateWarningDialog = MutableStateFlow(false)
    val showDateWarningDialog: StateFlow<Boolean> = _showDateWarningDialog.asStateFlow()

    private val _waterCost = MutableStateFlow(0) // Default water cost
    val waterCost: StateFlow<Int> = _waterCost.asStateFlow()

    // You'll need to implement the actual storage/logic for this
    private var isChargeAlreadyAddedThisMonth = false


     fun checkDateForWarning(context: Context) {
        viewModelScope.launch {
            delay(100)
            _showDateWarningDialog.value = !CheckDate(context).getAndCheck(Date())
        }
    }



    fun updateWaterCost(cost: Int) {
        _waterCost.value = cost
        // Potentially save this to SharedPreferences or a database if needed persistently
    }

    fun markChargeAsAdded() {
        // Here, you would implement the logic to persist that a charge has been added
        // For example, update SharedPreferences or a database flag
        isChargeAlreadyAddedThisMonth = true
        // You might also want to update _showDateWarningDialog if the warning
        // should disappear immediately after adding (though current logic shows it on screen entry)
        Log.i("ViewModel", "Charge marked as added for the month.")
    }







    fun checkIsInserted(onAction: () -> Unit) {
        if (prefsManager.checkState())
            if (isInsert.value == false)
                onAction()

    }

    fun changeState() {
        prefsManager.saveState()
    }


    fun isInserted() {
        viewModelScope.launch {
            val count = withContext(Dispatchers.IO) {
                db.buildingDao().getBData()
            }
            _isInsert.value = count == 0

        }
    }

    fun isChargeAdded() {
        val today = Date().getDateForCalculate()
        viewModelScope.launch {
            val isExist = withContext(Dispatchers.IO) { // Perform database operation on IO thread

                db.buildingDao().isLastMonthExist(today)
            }
            _isChargeAdded.value = isExist != 0


        }
    }

    fun getPayed(month: String, paid: Int) {
        viewModelScope.launch {
            val count = withContext(Dispatchers.IO) {

                db.buildingDao().getPaidCount(month, paid)
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