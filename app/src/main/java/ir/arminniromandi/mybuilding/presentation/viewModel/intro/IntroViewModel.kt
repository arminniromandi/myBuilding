package ir.arminniromandi.mybuilding.presentation.viewModel.intro

import BuildingRepository
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Building
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val buildingRepository : BuildingRepository
) : ViewModel() {




    private val _showDateWarningDialog = MutableStateFlow(false)
    val showDateWarningDialog: StateFlow<Boolean> = _showDateWarningDialog.asStateFlow()
    private val _name = MutableStateFlow("")
    private val _count = MutableStateFlow(0)

    val name= _name.asStateFlow()
    val count = _count.asStateFlow()

    val isAdded = mutableStateOf(false)



    fun dialogState(state : Boolean){

        _showDateWarningDialog.value = state


    }

    fun isUserAdded() {
        viewModelScope.launch(Dispatchers.IO) {
            val count = buildingRepository.countOfBuilding()
            withContext(Dispatchers.Main) {
                isAdded.value = count != 0
            }

        }
    }

    fun getOldData() {

        viewModelScope.launch(Dispatchers.IO) {

            val userName =   buildingRepository.getBHostName()
            val count = buildingRepository.getBUnitCount()

            withContext(Dispatchers.Main) {
                if (userName != null) {
                    _name.value = userName
                }
                _count.value = count
            }


        }

    }

    fun insertBuildingUser(){

        viewModelScope.launch {

                buildingRepository
                .insertData(Building(B_id = 0, name.value, count.value))

        }
    }


}