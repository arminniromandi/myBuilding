package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship

import androidx.room.Embedded
import androidx.room.Relation
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInYear

data class CostInYearWithMonths(

    @Embedded
    val year: CostInYear,

    @Relation(
        parentColumn = "C_YearId",
        entityColumn = "M_YearId"
    )
    val months: List<CostInMonth>
)