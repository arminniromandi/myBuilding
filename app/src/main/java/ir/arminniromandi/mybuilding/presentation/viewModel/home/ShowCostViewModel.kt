package ir.arminniromandi.mybuilding.presentation.viewModel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship.CostForUnitDetails
import ir.arminniromandi.mybuilding.data.repository.BuildingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ShowCostViewModel @Inject constructor(

    private val buildingRepository: BuildingRepository
) : ViewModel() {

    private val _showExposeDialog = MutableStateFlow(false)
    val showExposeDialog: StateFlow<Boolean> = _showExposeDialog.asStateFlow()

    private val _isOnAllCostShow = MutableStateFlow(false)
    val isOnAllCostShow: StateFlow<Boolean> = _isOnAllCostShow.asStateFlow()


    private val _mounths = MutableStateFlow<List<CostInMonth>>(emptyList())
    val mounths: StateFlow<List<CostInMonth>> = _mounths.asStateFlow()

    private val _costPerUnit = MutableStateFlow<List<CostForUnitDetails>>(emptyList())
    val costPerUnit: StateFlow<List<CostForUnitDetails>> = _costPerUnit.asStateFlow()


    //تابغ گرفتن تمامی ماه ها
    fun getMonths() {
        viewModelScope.launch {
            buildingRepository.getMouths().collect { data ->
                _mounths.value = data.reversed()
            }

        }
    }



    //تابع گرفتن شاؤز بر اساس id ماه
    fun getCostPerUnitWithMounth(mouthId: Int) {


        viewModelScope.launch {
//getCostWithMoths
            buildingRepository.getCostWithMonth(mouthId)
                .collect { fetchedCost ->
                    _costPerUnit.value = fetchedCost
                }
        }


    }


    fun changePaidState(data : CostForUnit ){
        viewModelScope.launch {
            buildingRepository
                .updateCost(data.copy(isPaid = !data.isPaid))
        }
    }



    //todo : im add to this
    //get Monthid and getForm db CostInMonth
    fun showDialog(){




    }



    fun closeDialogState(){
        _showExposeDialog.value = false
    }

    fun changeDialogState(state: Boolean) {
        _showExposeDialog.value = (state)
    }

    fun changeisOnAllCostShow(state: Boolean) {
        _isOnAllCostShow.value = (state)
    }


}