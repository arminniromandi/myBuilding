package ir.arminniromandi.mybuilding.domain.useCase

import ir.arminniromandi.mybuilding.data.repository.BuildingRepository
import ir.arminniromandi.mybuilding.utils.date.Date
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CheckNeedCreateNewMonthUseCase @Inject constructor(
    val buildingRepository: BuildingRepository,

    ) {
    suspend operator fun invoke(date: Date): Boolean {
        val lastMonth = buildingRepository.getLastMonth()

        return lastMonth?.CostInMounthAndYear != date.getDateForCalculate()
    }
}