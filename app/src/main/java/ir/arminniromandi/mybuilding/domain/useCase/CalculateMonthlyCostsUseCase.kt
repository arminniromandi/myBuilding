package ir.arminniromandi.mybuilding.domain.useCase

import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth
import ir.arminniromandi.mybuilding.data.repository.BuildingRepository
import ir.arminniromandi.mybuilding.utils.date.Date
import javax.inject.Inject
import kotlin.math.roundToInt

class CalculateMonthlyCostsUseCase @Inject constructor(
    private val repository: BuildingRepository,
    private val dateProvider: Date
) {

    suspend operator fun invoke(
        water: Int,
        allCost: Int
    ) {
        val users = repository.getUserUnit()
        val buildingUnitCount = repository.getBData() ?: 0
        val buildingTotalUnitCount = repository.getBUnitCount()

        if (users.isEmpty()) return
        if (buildingUnitCount <= 0) return
        if (buildingTotalUnitCount <= 0) return

        val date = dateProvider.getDateForCalculate()

        val waterCostPerUnit = water.toDouble() / buildingUnitCount.toDouble()
        val sharedCostPerUnit = allCost.toDouble() / buildingTotalUnitCount.toDouble()
        val totalMonthCost = water + allCost

        repository.insertMonth(
            CostInMonth(
                C_MonthId = 0,
                CostInMounthAndYear = Date().getDateForCalculate(),
                costWater = water,
                cost = allCost,
                sumOfAll = totalMonthCost
            )
        )

        users.forEachIndexed { index, user ->
            val unitNumber = index + 1
            val waterCostForUnit = user.U_Unit * waterCostPerUnit
            val totalCostForUnit = waterCostForUnit + sharedCostPerUnit

            repository.insertCost(
                CostForUnit(
                    B_id = 0,
                    C_Cost = totalCostForUnit.roundToInt().toString(),
                    C_Unit = unitNumber,
                    isPaid = false,
                    C_MonthAndYear = date
                )
            )
        }
    }
}