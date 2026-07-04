package ir.arminniromandi.mybuilding.presentation.viewModel

import BuildingRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arminniromandi.mybuilding.data.local.preferences.PreferencesManager
import ir.arminniromandi.mybuilding.data.repository.NoteRepository
import ir.arminniromandi.mybuilding.domain.useCase.CheckNeedCreateNewMonthUseCase
import ir.arminniromandi.mybuilding.utils.date.Date
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefsManager: PreferencesManager,
    private val buildingRepository: BuildingRepository,
    private val checkNeedCreateNewMonthUseCase: CheckNeedCreateNewMonthUseCase
) : ViewModel() {

    // todo: should have better
    private val _name = MutableLiveData("مدیر")
    private val _data = MutableLiveData(1)
    private val _isInsert = MutableLiveData(false)
    private val _isChargeAdded = MutableLiveData(false)

    val isChargeAdded: LiveData<Boolean> = _isChargeAdded
    val payed: LiveData<Int> = _data
    val name: LiveData<String> = _name
    val isInsert: LiveData<Boolean> = _isInsert


    //load data when MainViewModel initilized
    init {
        getName()
        isInserted()
        isChargeAdded()
    }




    // You'll need to implement the actual storage/logic for this
    //shared viewModel
    private var isChargeAlreadyAddedThisMonth = false







    fun markChargeAsAdded() {
        // Here, you would implement the logic to persist that a charge has been added
        // For example, update SharedPreferences or a database flag
        isChargeAlreadyAddedThisMonth = true
        // You might also want to update _showDateWarningDialog if the warning
        // should disappear immediately after adding (though current logic shows it on screen entry)
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
            val count  = buildingRepository.getBData()

            _isInsert.value = count == 0

        }
    }

    fun isChargeAdded() {
        val today = Date().getDateForCalculate()
        viewModelScope.launch {
            val isExist = buildingRepository.isLastMonthExist(today)

            _isChargeAdded.value = isExist != 0


        }
    }

    fun getPayed(month: String, paid: Int) {
        viewModelScope.launch {
            val count =
                buildingRepository.getPaidCount(month, paid)

            _data.value = count
        }

    }

    fun getName() {
        viewModelScope.launch {
            val name = buildingRepository.getBHostName()
            _name.value = name ?: "مدیر"
        }
    }


}