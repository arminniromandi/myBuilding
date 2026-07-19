package ir.arminniromandi.mybuilding.domain.model

data class MonthlyCostCommand(
    val buildingId: Int = 1,
    val year: Int,
    val monthNumber: Int,
    val monthDisplayName: String,
    val waterCost: Int,
    val otherCost: Int,
    val totalCost: Int,
    val unitCosts: List<UnitMonthlyCost>
)
