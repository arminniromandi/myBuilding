package ir.arminniromandi.mybuilding.presentation.viewModel.home

import BuildingRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowBUnitViewModel @Inject constructor(
    private val buildingRepository: BuildingRepository
) : ViewModel() {

    val allUsers: StateFlow<List<BUser>> =
        buildingRepository.getAllUsers()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    private val _isDialogVisible = MutableStateFlow(false)
    val isDialogVisible: StateFlow<Boolean> = _isDialogVisible.asStateFlow()

    private val _selectedUnit = MutableStateFlow(0)
    val selectedUnit: StateFlow<Int> = _selectedUnit.asStateFlow()

    private val _selectedUnitUsers = MutableStateFlow(0)
    val selectedUnitUsers: StateFlow<Int> = _selectedUnitUsers.asStateFlow()

    fun openEditDialog(user: BUser) {
        _selectedUnit.value = user.U_id
        _selectedUnitUsers.value = user.U_Unit
        _isDialogVisible.value = true
    }

    fun closeDialog() {
        _isDialogVisible.value = false
    }

    fun increaseUnitUsers() {
        _selectedUnitUsers.value += 1
    }

    fun decreaseUnitUsers() {
        if (_selectedUnitUsers.value > 0) {
            _selectedUnitUsers.value -= 1
        }
    }

    fun updateSelectedUnitUsers() {
        viewModelScope.launch {
            buildingRepository.updateUsers(
                BUser(
                    U_id = _selectedUnit.value,
                    U_Unit = _selectedUnitUsers.value
                )
            )

            _isDialogVisible.value = false
        }
    }
}