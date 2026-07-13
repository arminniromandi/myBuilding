package ir.arminniromandi.mybuilding.domain.useCase

import ir.arminniromandi.mybuilding.data.repository.BuildingRepository
import ir.arminniromandi.mybuilding.domain.model.MonthlyCostCommand
import ir.arminniromandi.mybuilding.domain.model.UnitMonthlyCost
import ir.arminniromandi.mybuilding.utils.date.Date
import javax.inject.Inject

class CalculateMonthlyCostsUseCase @Inject constructor(
    private val repository: BuildingRepository,
    private val dateProvider: Date
) {

    suspend operator fun invoke(
        water: Int,
        allCost: Int
    ) {

        val users = repository
            .getUserUnit()
            .sortedBy { it.U_Number }
        val buildingUnitCount = repository.getBData() ?: 0
        val buildingTotalUnitCount = repository.getBUnitCount()

        if (users.isEmpty()) return
        if (buildingUnitCount <= 0) return
        if (buildingTotalUnitCount <= 0) return


        val waterCostPerUnit = water.toDouble() / buildingUnitCount.toDouble()
        val sharedCostPerUnit = allCost.toDouble() / buildingTotalUnitCount.toDouble()




        val unitCosts = mutableListOf<UnitMonthlyCost>()

        users.forEachIndexed { index, user ->
            val waterCostForUnit = user.U_Unit * waterCostPerUnit
            val totalCostForUnit = waterCostForUnit + sharedCostPerUnit

            unitCosts.add(
                index +1,
                UnitMonthlyCost(
                    user.U_Number,
                    totalCostForUnit.toInt()
                )
            )
        }


        repository.insertMonthWithCosts(
            MonthlyCostCommand(
                buildingId = 0,
                year = dateProvider.year,
                monthNumber = dateProvider.month,
                monthDisplayName = dateProvider.strMonth,
                waterCost = water,
                otherCost = allCost,
                totalCost = water + allCost,
                unitCosts = unitCosts
            )
        )





    }
}