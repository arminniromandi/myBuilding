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

        val buildingId = 1

        val totalUnitCount =
            repository.getBData() ?: 0

        val buildingUnitCount =
            repository.getBUnitCount()

        if (users.isEmpty()) return
        if (totalUnitCount <= 0) return
        if (buildingUnitCount <= 0) return

        val waterCostPerUnit =
            water.toDouble() / totalUnitCount.toDouble()

        val sharedCostPerUnit =
            allCost.toDouble() / buildingUnitCount.toDouble()

        val unitCosts =
            mutableListOf<UnitMonthlyCost>()

        println(users)

        users.forEach { user ->

            println("worked: unit=${user.U_Number}, id=${user.U_id}")

            val waterCostForUnit =
                user.U_Unit * waterCostPerUnit

            val totalCostForUnit =
                waterCostForUnit + sharedCostPerUnit

            unitCosts.add(
                UnitMonthlyCost(
                    /*
                     * بسیار مهم:
                     * اینجا U_id باید ذخیره شود،
                     */
                    user.U_id,

                    totalCostForUnit.toInt()
                )
            )
        }

        repository.insertMonthWithCosts(
            MonthlyCostCommand(
                /*
                 * شناسه واقعی ساختمان
                 */
                buildingId = buildingId,

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