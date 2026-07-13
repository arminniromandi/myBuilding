package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship

import androidx.room.Embedded
import androidx.room.Relation
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth

data class CostForUnitDetails(

    @Embedded
    val costForUnit: CostForUnit,

    @Relation(
        parentColumn = "C_UnitId",
        entityColumn = "U_Id"
    )
    val unit: BUser,

    @Relation(
        parentColumn = "C_MonthId",
        entityColumn = "CostInMonth_Id"
    )
    val month: CostInMonth
)
