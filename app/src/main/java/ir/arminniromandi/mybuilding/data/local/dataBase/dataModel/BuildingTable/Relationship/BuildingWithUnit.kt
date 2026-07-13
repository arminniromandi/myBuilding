package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Relationship

import androidx.room.Embedded
import androidx.room.Relation
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Building

data class BuildingWithUnits(

    @Embedded
    val building: Building,

    @Relation(
        parentColumn = "B_Id",
        entityColumn = "U_BuildingId"
    )
    val units: List<BUser>
)
