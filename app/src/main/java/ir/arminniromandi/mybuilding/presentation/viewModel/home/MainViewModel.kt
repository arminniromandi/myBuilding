package ir.arminniromandi.mybuilding.presentation.viewModel.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arminniromandi.mybuilding.data.local.preferences.PreferencesManager
import ir.arminniromandi.mybuilding.data.repository.BuildingRepository
import ir.arminniromandi.mybuilding.utils.date.Date
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsManager: PreferencesManager,
    private val buildingRepository: BuildingRepository,
    private val date: Date
) : ViewModel() {

    val nowDate: String = date.getDate()

    /*
     * مقدار اولیه ندارد؛ بنابراین قبل از بررسی دیتابیس
     * true یا false اشتباه به UI ارسال نمی‌شود.
     */
    private val _isInsert = MutableLiveData<Boolean>()
    val isInsert: LiveData<Boolean> = _isInsert

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _isChargeAdded = MutableLiveData<Boolean>()
    val isChargeAdded: LiveData<Boolean> = _isChargeAdded

    private val _data = MutableLiveData(0)
    val payed: LiveData<Int> = _data

    init {
        loadInitialData()
    }

     fun loadInitialData() {
        viewModelScope.launch {


            // مرحله اول: بررسی وجود اطلاعات ساختمان
            val buildingUnitCount = buildingRepository.getBUnitCount()
            val isBuildingInserted = buildingUnitCount > 0


            // فقط بعد از تمام شدن بررسی، نتیجه به UI ارسال می‌شود
            _isInsert.value = isBuildingInserted

            /*
             * فقط وقتی ساختمان ثبت شده است،
             * وضعیت شارژ و نام مدیر بررسی می‌شود.
             */
            if (isBuildingInserted) {
                Log.i("IntroTest" , "loadIntD")

                loadChargeStatus()
                loadManagerName()
            } else {

                _isChargeAdded.value = false
                _name.value = "مدیر"
            }
        }
    }

    private suspend fun loadChargeStatus() {
        val currentMonth = date.getDateForCalculate()

        val chargeCount = buildingRepository.isLastMonthExist(
            currentMonth
        )

        _isChargeAdded.value = chargeCount != 0
    }

    private suspend fun loadManagerName() {
        val managerName = buildingRepository.getBHostName()

        println(managerName)
        _name.value = managerName ?: "مدیر"
    }

    fun refreshInsertedState() {
        loadInitialData()
    }

    fun getPayed(paid: Boolean) {
        viewModelScope.launch {
            val currentMonth = date.getDateForCalculate()

            _data.value = buildingRepository.getPaidCount(
                currentMonth,
                paid
            )
        }
    }
}