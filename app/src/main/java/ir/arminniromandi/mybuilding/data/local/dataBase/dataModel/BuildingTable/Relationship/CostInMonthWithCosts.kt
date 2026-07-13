package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship

import androidx.room.Embedded
import androidx.room.Relation
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth

data class CostInMonthWithCosts(

    @Embedded
    val month: CostInMonth,

    @Relation(
        parentColumn = "CostInMonth_Id",
        entityColumn = "C_MonthId"
    )
    val costs: List<CostForUnit>
)
