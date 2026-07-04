package ir.arminniromandi.mybuilding.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.arminniromandi.mybuilding.domain.useCase.CalculateMonthlyCostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCostViewModel @Inject constructor(
    private val calculateMonthlyCostsUseCase: CalculateMonthlyCostsUseCase
) : ViewModel() {
    private val _waterCostInput = MutableStateFlow("0")
    val waterCostInput: StateFlow<String> = _waterCostInput.asStateFlow()

    private val _showWaterCostInput = MutableStateFlow(true)
    val showWaterCostInput: StateFlow<Boolean> = _showWaterCostInput.asStateFlow()

    private val _costValues = MutableStateFlow(listOf("", ""))
    val costValues: StateFlow<List<String>> = _costValues.asStateFlow()

    private var initialWaterCostApplied = false

    fun setInitialWaterCost(waterCost: Int) {
        if (initialWaterCostApplied) return

        _waterCostInput.value = waterCost.toString()
        initialWaterCostApplied = true
    }

    fun onWaterCostChanged(value: String) {
        _waterCostInput.value = value.onlyDigits()
    }

    fun onContinueClicked(commitWaterCost: (Int) -> Unit) {
        commitWaterCost(_waterCostInput.value.toIntOrZero())
        _showWaterCostInput.value = false
    }

    fun onCostValueChanged(index: Int, value: String) {
        val currentValues = _costValues.value

        if (index !in currentValues.indices) return

        _costValues.value = currentValues.toMutableList().also {
            it[index] = value.onlyDigits()
        }
    }

    fun addCostField() {
        _costValues.value = _costValues.value + ""
    }

    fun removeCostField() {
        val currentValues = _costValues.value

        if (currentValues.isEmpty()) return

        _costValues.value = currentValues.dropLast(1)
    }

    fun calculateCosts(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val water = _waterCostInput.value.toIntOrZero()
            val allCost = _costValues.value.sumOf { it.toIntOrZero() }

            calculateMonthlyCostsUseCase(
                water = water,
                allCost = allCost
            )

            onSuccess()
        }
    }

    fun canContinue(): Boolean {
        return _waterCostInput.value.isNotBlank()
    }

    fun canRemoveCostField(): Boolean {
        return _costValues.value.isNotEmpty()
    }

    fun canCalculate(): Boolean {
        return _costValues.value.any { it.isNotBlank() }
    }

    private fun String.onlyDigits(): String {
        return filter { it.isDigit() }
    }

    private fun String.toIntOrZero(): Int {
        return toIntOrNull() ?: 0
    }
}